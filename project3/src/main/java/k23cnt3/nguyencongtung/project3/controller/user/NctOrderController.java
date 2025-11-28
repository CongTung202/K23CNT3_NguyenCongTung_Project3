package k23cnt3.nguyencongtung.project3.controller.user;

import k23cnt3.nguyencongtung.project3.entity.NctCartItem;
import k23cnt3.nguyencongtung.project3.entity.NctOrder;
import k23cnt3.nguyencongtung.project3.entity.NctProduct;
import k23cnt3.nguyencongtung.project3.entity.NctUser;
import k23cnt3.nguyencongtung.project3.service.NctCartService;
import k23cnt3.nguyencongtung.project3.service.NctOrderService;
import k23cnt3.nguyencongtung.project3.service.NctProductService;
import k23cnt3.nguyencongtung.project3.service.NctUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class NctOrderController {
    private final NctCartService nctCartService;
    private final NctUserService nctUserService;
    private final NctOrderService nctOrderService;
    private final NctProductService nctProductService;

    @Autowired
    public NctOrderController(NctCartService nctCartService, NctUserService nctUserService, NctOrderService nctOrderService, NctProductService nctProductService) {
        this.nctCartService = nctCartService;
        this.nctUserService = nctUserService;
        this.nctOrderService = nctOrderService;
        this.nctProductService = nctProductService;
    }

    private NctUser getCurrentUser(UserDetails userDetails) {
        if (userDetails == null) {
            return null;
        }
        return nctUserService.nctFindByUsername(userDetails.getUsername()).orElse(null);
    }

    @GetMapping("/checkout")
    public String checkoutPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<NctCartItem> cartItems = nctCartService.nctGetCartItems(currentUser);
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        double total = nctCartService.nctCalculateCartTotal(currentUser);

        model.addAttribute("nctCartItems", cartItems);
        model.addAttribute("nctTotal", total);
        if (!model.containsAttribute("nctOrder")) {
            model.addAttribute("nctOrder", new NctOrder());
        }
        model.addAttribute("nctPageTitle", "Thanh toán - UMACT Store");

        return "user/nct-checkout";
    }

    @GetMapping("/checkout/buy-now")
    public String buyNowPage(@RequestParam("productId") Long productId,
                             @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            NctProduct product = nctProductService.nctGetProductById(productId)
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại."));

            if (product.getNctStockQuantity() < quantity) {
                redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm không đủ số lượng trong kho.");
                return "redirect:/products/" + productId;
            }

            NctCartItem singleItem = new NctCartItem();
            singleItem.setNctProduct(product);
            singleItem.setNctQuantity(quantity);

            List<NctCartItem> items = Collections.singletonList(singleItem);
            double total = product.getNctPrice().doubleValue() * quantity;

            model.addAttribute("nctCartItems", items);
            model.addAttribute("nctTotal", total);
            if (!model.containsAttribute("nctOrder")) {
                model.addAttribute("nctOrder", new NctOrder());
            }
            model.addAttribute("nctPageTitle", "Thanh toán - UMACT Store");

            model.addAttribute("isBuyNow", true);
            model.addAttribute("buyNowProductId", productId);
            model.addAttribute("buyNowQuantity", quantity);

            return "user/nct-checkout";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/checkout")
    public String placeOrder(@AuthenticationPrincipal UserDetails userDetails,
                             @ModelAttribute("nctOrder") NctOrder nctOrder,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model,
                             @RequestParam(name = "isBuyNow", defaultValue = "false") boolean isBuyNow,
                             @RequestParam(name = "productId", required = false) Long productId,
                             @RequestParam(name = "quantity", required = false) Integer quantity) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            if (isBuyNow && productId != null && quantity != null) {
                try {
                    NctProduct product = nctProductService.nctGetProductById(productId)
                            .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại."));
                    NctCartItem singleItem = new NctCartItem();
                    singleItem.setNctProduct(product);
                    singleItem.setNctQuantity(quantity);
                    model.addAttribute("nctCartItems", Collections.singletonList(singleItem));
                    model.addAttribute("nctTotal", product.getNctPrice().doubleValue() * quantity);
                    model.addAttribute("isBuyNow", true);
                    model.addAttribute("buyNowProductId", productId);
                    model.addAttribute("buyNowQuantity", quantity);
                } catch (RuntimeException e) {
                    redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
                    return "redirect:/";
                }
            } else {
                List<NctCartItem> cartItems = nctCartService.nctGetCartItems(currentUser);
                double total = nctCartService.nctCalculateCartTotal(currentUser);
                model.addAttribute("nctCartItems", cartItems);
                model.addAttribute("nctTotal", total);
            }
            model.addAttribute("nctPageTitle", "Thanh toán - UMACT Store");
            return "user/nct-checkout";
        }

        try {
            if (isBuyNow) {
                nctOrderService.nctCreateOrderFromSingleProduct(
                        currentUser,
                        productId,
                        quantity,
                        nctOrder.getNctShippingAddress(),
                        nctOrder.getNctPhone(),
                        nctOrder.getNctPaymentMethod()
                );
            } else {
                nctOrderService.nctCreateOrder(
                        currentUser,
                        nctOrder.getNctShippingAddress(),
                        nctOrder.getNctPhone(),
                        nctOrder.getNctPaymentMethod()
                );
            }
            redirectAttributes.addFlashAttribute("successMessage", "Đặt hàng thành công!");
            return "redirect:/orders";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("nctOrder", nctOrder);
            if (isBuyNow) {
                return "redirect:/checkout/buy-now?productId=" + productId + "&quantity=" + quantity;
            } else {
                return "redirect:/checkout";
            }
        }
    }

    @GetMapping("/orders")
    public String showOrderList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<NctOrder> orders = nctOrderService.nctGetUserOrders(currentUser);
        model.addAttribute("nctOrders", orders);
        model.addAttribute("nctPageTitle", "Đơn hàng của bạn - UMACT Store");
        return "user/nct-order-list";
    }

    @GetMapping("/orders/{id}")
    public String showOrderDetail(@PathVariable("id") Long orderId,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return "redirect:/login";
        }

        Optional<NctOrder> orderOpt = nctOrderService.nctGetOrderById(orderId);

        if (orderOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đơn hàng.");
            return "redirect:/orders";
        }

        NctOrder order = orderOpt.get();
        if (!order.getNctUser().getNctUserId().equals(currentUser.getNctUserId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn không có quyền xem đơn hàng này.");
            return "redirect:/orders";
        }

        model.addAttribute("nctOrder", order);
        model.addAttribute("nctPageTitle", "Chi tiết đơn hàng #" + order.getNctOrderId() + " - UMACT Store");
        return "user/nct-order-detail";
    }
}
