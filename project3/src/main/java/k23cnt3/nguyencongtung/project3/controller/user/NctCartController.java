package k23cnt3.nguyencongtung.project3.controller.user;

import k23cnt3.nguyencongtung.project3.entity.NctCartItem;
import k23cnt3.nguyencongtung.project3.entity.NctUser;
import k23cnt3.nguyencongtung.project3.service.NctCartService;
import k23cnt3.nguyencongtung.project3.service.NctUserService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final NctUserService nctUserService;

    @Autowired
    public NctCartController(NctCartService nctCartService, NctUserService nctUserService) {
        this.nctCartService = nctCartService;
        this.nctUserService = nctUserService;
    }

    private NctUser getCurrentUser(UserDetails userDetails) {
        if (userDetails == null) {
            return null;
        }
        return nctUserService.nctFindByUsername(userDetails.getUsername()).orElse(null);
    }

    @GetMapping
    public String nctShowCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return "redirect:/login";
        }
        List<NctCartItem> cartItems = nctCartService.nctGetCartItems(currentUser);
        double total = nctCartService.nctCalculateCartTotal(currentUser);

        model.addAttribute("nctCartItems", cartItems);
        model.addAttribute("nctTotal", total);
        model.addAttribute("nctPageTitle", "Giỏ hàng - UMACT Store");
        // You will need to create a 'user/nct-cart.html' view for this
        return "user/nct-cart";
    }

    @PostMapping("/add")
    public String nctAddToCart(@RequestParam("productId") Long productId,
                               @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                               @RequestParam(value = "redirectTo", required = false) String redirectTo,
                               @AuthenticationPrincipal UserDetails userDetails,
                               RedirectAttributes redirectAttributes,
                               HttpServletRequest request) {
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

        if ("checkout".equals(redirectTo)) {
            return "redirect:/checkout";
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/products");
    }

    @PostMapping("/update")
    public String nctUpdateCart(@RequestParam("cartItemId") Long cartItemId,
                                @RequestParam("quantity") Integer quantity,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            nctCartService.nctUpdateCartItem(currentUser, cartItemId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Số lượng sản phẩm đã được cập nhật.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String nctRemoveFromCart(@RequestParam("cartItemId") Long cartItemId,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    RedirectAttributes redirectAttributes) {
        NctUser currentUser = getCurrentUser(userDetails);
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            nctCartService.nctRemoveFromCart(currentUser, cartItemId);
            redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được xóa khỏi giỏ hàng.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/cart";
    }
}