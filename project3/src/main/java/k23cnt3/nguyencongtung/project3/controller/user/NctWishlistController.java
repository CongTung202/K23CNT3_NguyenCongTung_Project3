package k23cnt3.nguyencongtung.project3.controller.user;

        import k23cnt3.nguyencongtung.project3.entity.NctUser;
        import k23cnt3.nguyencongtung.project3.entity.NctWishlist;
        import k23cnt3.nguyencongtung.project3.service.NctUserService;
        import k23cnt3.nguyencongtung.project3.service.NctWishlistService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;

        import java.util.List;
        import java.util.Optional;

        @Controller
        @RequestMapping("/wishlist")
        public class NctWishlistController {

            private final NctWishlistService nctWishlistService;
            private final NctUserService nctUserService;

            @Autowired
            public NctWishlistController(NctWishlistService nctWishlistService, NctUserService nctUserService) {
                this.nctWishlistService = nctWishlistService;
                this.nctUserService = nctUserService;
            }

            private Optional<NctUser> getCurrentUser() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
                    return Optional.empty();
                }
                String username = authentication.getName();
                return nctUserService.nctFindByUsername(username);
            }

            @GetMapping
            public String viewWishlist(Model model) {
                Optional<NctUser> currentUserOpt = getCurrentUser();
                if (currentUserOpt.isEmpty()) {
                    return "redirect:/login";
                }
                NctUser currentUser = currentUserOpt.get();
                List<NctWishlist> wishlistItems = nctWishlistService.nctGetWishlist(currentUser);
                model.addAttribute("wishlistItems", wishlistItems);
                model.addAttribute("nctPageTitle", "Danh sách yêu thích");
                return "user/nct-wishlist";
            }

            @PostMapping("/add")
            public String addToWishlist(@RequestParam("productId") Long productId, RedirectAttributes redirectAttributes, @RequestHeader(value = "Referer", required = false) final String referer) {
                Optional<NctUser> currentUserOpt = getCurrentUser();
                if (currentUserOpt.isEmpty()) {
                    return "redirect:/login";
                }
                NctUser currentUser = currentUserOpt.get();
                boolean success = nctWishlistService.nctAddToWishlist(currentUser, productId);
                if (success) {
                    redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được thêm vào danh sách yêu thích.");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm đã có trong danh sách yêu thích hoặc không tồn tại.");
                }
                return "redirect:" + (referer != null ? referer : "/");
            }

            @PostMapping("/remove/{productId}")
            public String removeFromWishlist(@PathVariable("productId") Long productId, RedirectAttributes redirectAttributes) {
                Optional<NctUser> currentUserOpt = getCurrentUser();
                if (currentUserOpt.isEmpty()) {
                    return "redirect:/login";
                }
                NctUser currentUser = currentUserOpt.get();
                nctWishlistService.nctRemoveFromWishlist(currentUser, productId);
                redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được xóa khỏi danh sách yêu thích.");
                return "redirect:/wishlist";
            }
        }