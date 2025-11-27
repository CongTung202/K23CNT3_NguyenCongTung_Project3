package k23cnt3.nguyencongtung.project3.controller.user;

import k23cnt3.nguyencongtung.project3.entity.NctCartItem;
import k23cnt3.nguyencongtung.project3.entity.NctUser;
import k23cnt3.nguyencongtung.project3.service.NctCartService;
import k23cnt3.nguyencongtung.project3.service.NctUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class NctCartController {

    private final NctCartService nctCartService;
    private final NctUserService nctUserService; // Giả định service này đã tồn tại

    @Autowired
    public NctCartController(NctCartService nctCartService, NctUserService nctUserService) {
        this.nctCartService = nctCartService;
        this.nctUserService = nctUserService;
    }

    private NctUser getCurrentUser(UserDetails userDetails) {
        if (userDetails == null) {
            return null;
        }
        // Giả định bạn có phương thức findByUsername trong NctUserService
        return nctUserService.nctFindByUsername(userDetails.getUsername()).orElse(null);
    }

    @GetMapping
    public String nctViewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<NctCartItem> cartItems = nctCartService.nctGetCartItems(currentUser);
        Double total = nctCartService.nctCalculateCartTotal(currentUser);

        model.addAttribute("nctCartItems", cartItems);
        model.addAttribute("nctTotal", total);
        model.addAttribute("nctPageTitle", "Giỏ hàng - OTAKU.vn");
        return "user/nct-cart";
    }

    @PostMapping("/add")
    public String nctAddToCart(@RequestParam("productId") Long productId,
                               @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                               @AuthenticationPrincipal UserDetails userDetails,
                               RedirectAttributes redirectAttributes) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            nctCartService.nctAddToCart(currentUser, productId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được thêm vào giỏ hàng!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/products";
    }

    @PostMapping("/update")
    public String nctUpdateCart(@RequestParam("cartItemId") Long cartItemId,
                                @RequestParam("quantity") Integer quantity,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        if (getCurrentUser(userDetails) == null) {
            return "redirect:/login";
        }
        if (quantity <= 0) {
            nctCartService.nctRemoveFromCart(cartItemId);
            redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được xóa khỏi giỏ hàng.");
        } else {
            nctCartService.nctUpdateCartItemQuantity(cartItemId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Số lượng sản phẩm đã được cập nhật.");
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove/{id}")
    public String nctRemoveFromCart(@PathVariable("id") Long cartItemId,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    RedirectAttributes redirectAttributes) {
        if (getCurrentUser(userDetails) == null) {
            return "redirect:/login";
        }
        nctCartService.nctRemoveFromCart(cartItemId);
        redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được xóa khỏi giỏ hàng.");
        return "redirect:/cart";
    }
}