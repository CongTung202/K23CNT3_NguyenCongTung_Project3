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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class NctCheckoutController {

    private final NctCartService nctCartService;
    private final NctUserService nctUserService;
    private final NctOrderService nctOrderService;
    private final NctProductService nctProductService;

    @Autowired
    public NctCheckoutController(NctCartService nctCartService, NctUserService nctUserService, NctOrderService nctOrderService, NctProductService nctProductService) {
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
    public String nctShowCheckoutForm(@AuthenticationPrincipal UserDetails userDetails, Model model, RedirectAttributes redirectAttributes) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<NctCartItem> cartItems = nctCartService.nctGetCartItems(currentUser);
        if (cartItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("nctError", "Giỏ hàng của bạn đang trống.");
            return "redirect:/cart";
        }

        double total = nctCartService.nctCalculateCartTotal(currentUser);
        NctOrder order = new NctOrder();
        order.setNctShippingAddress(currentUser.getNctAddress());
        order.setNctPhone(currentUser.getNctPhone());

        model.addAttribute("nctCartItems", cartItems);
        model.addAttribute("nctTotal", total);
        model.addAttribute("nctOrder", order);
        model.addAttribute("isBuyNow", false);
        model.addAttribute("nctPageTitle", "Thanh toán");
        return "user/nct-checkout";
    }

    @GetMapping("/checkout/buy-now")
    public String nctShowBuyNowForm(@RequestParam("productId") Long productId,
                                    @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                                    @AuthenticationPrincipal UserDetails userDetails, Model model, RedirectAttributes redirectAttributes) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return "redirect:/login";
        }

        NctProduct product = nctProductService.nctGetProductById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        if (!nctProductService.nctIsProductInStock(productId, quantity)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm không đủ số lượng trong kho.");
            return "redirect:/products/" + productId;
        }

        NctCartItem tempCartItem = new NctCartItem();
        tempCartItem.setNctProduct(product);
        tempCartItem.setNctQuantity(quantity);

        double total = product.getNctPrice().doubleValue() * quantity;

        NctOrder order = new NctOrder();
        order.setNctShippingAddress(currentUser.getNctAddress());
        order.setNctPhone(currentUser.getNctPhone());

        model.addAttribute("nctCartItems", Collections.singletonList(tempCartItem));
        model.addAttribute("nctTotal", total);
        model.addAttribute("nctOrder", order);
        model.addAttribute("isBuyNow", true);
        model.addAttribute("buyNowProductId", productId);
        model.addAttribute("buyNowQuantity", quantity);
        model.addAttribute("nctPageTitle", "Thanh toán Mua ngay");

        return "user/nct-checkout";
    }

    @PostMapping("/checkout")
    @ResponseBody
    public ResponseEntity<?> nctProcessCheckout(@ModelAttribute("nctOrder") NctOrder nctOrderDetails,
                                                @RequestParam(name = "isBuyNow", defaultValue = "false") boolean isBuyNow,
                                                @RequestParam(name = "productId", required = false) Long productId,
                                                @RequestParam(name = "quantity", required = false) Integer quantity,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Vui lòng đăng nhập."));
        }

        try {
            if (isBuyNow) {
                if (productId == null || quantity == null) {
                    return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Thiếu thông tin sản phẩm."));
                }
                nctOrderService.nctCreateOrderFromSingleProduct(currentUser, productId, quantity,
                        nctOrderDetails.getNctShippingAddress(), nctOrderDetails.getNctPhone(), nctOrderDetails.getNctPaymentMethod());
            } else {
                nctOrderService.nctCreateOrderFromCart(currentUser, nctOrderDetails);
            }
            return ResponseEntity.ok(Map.of("success", true, "message", "Đặt hàng thành công!", "redirectUrl", "/orders"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
