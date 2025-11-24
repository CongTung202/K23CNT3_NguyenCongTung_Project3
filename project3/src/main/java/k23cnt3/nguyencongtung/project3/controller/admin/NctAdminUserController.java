package k23cnt3.nguyencongtung.project3.controller.admin;

import k23cnt3.nguyencongtung.project3.entity.NctUser;
import k23cnt3.nguyencongtung.project3.service.NctUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class NctAdminUserController {

    private final NctUserService nctUserService;

    @Autowired
    public NctAdminUserController(NctUserService nctUserService) {
        this.nctUserService = nctUserService;
    }

    @GetMapping
    public String nctUserList(Model model) {
        List<NctUser> nctUsers = nctUserService.nctGetAllUsers();
        model.addAttribute("nctUsers", nctUsers);
        model.addAttribute("nctPageTitle", "Quản lý Người dùng - Admin");
        return "admin/users/nct-user-list";
    }

    @GetMapping("/view/{id}")
    public String nctViewUser(@PathVariable Long id, Model model) {
        Optional<NctUser> nctUserOpt = nctUserService.nctGetUserById(id);
        if (nctUserOpt.isEmpty()) {
            return "redirect:/admin/users";
        }

        model.addAttribute("nctUser", nctUserOpt.get());
        model.addAttribute("nctPageTitle", "Chi tiết Người dùng - Admin");
        return "admin/users/nct-user-view";
    }

    @GetMapping("/edit/{id}")
    public String nctShowEditForm(@PathVariable Long id, Model model) {
        Optional<NctUser> nctUserOpt = nctUserService.nctGetUserById(id);
        if (nctUserOpt.isEmpty()) {
            return "redirect:/admin/users";
        }

        model.addAttribute("nctUser", nctUserOpt.get());
        model.addAttribute("nctPageTitle", "Chỉnh sửa Người dùng - Admin");
        return "admin/users/nct-user-edit";
    }

    @PostMapping("/edit/{id}")
    public String nctUpdateUser(
            @PathVariable Long id,
            @ModelAttribute NctUser nctUser,
            @RequestParam String nctRole,
            RedirectAttributes nctRedirectAttributes) {

        try {
            Optional<NctUser> nctExistingUserOpt = nctUserService.nctGetUserById(id);
            if (nctExistingUserOpt.isEmpty()) {
                nctRedirectAttributes.addFlashAttribute("nctError", "Người dùng không tồn tại!");
                return "redirect:/admin/users";
            }

            NctUser nctExistingUser = nctExistingUserOpt.get();
            nctExistingUser.setNctFullName(nctUser.getNctFullName());
            nctExistingUser.setNctEmail(nctUser.getNctEmail());
            nctExistingUser.setNctPhone(nctUser.getNctPhone());
            nctExistingUser.setNctAddress(nctUser.getNctAddress());
            nctExistingUser.setNctRole(NctUser.NctRole.valueOf(nctRole));

            nctUserService.nctSaveUser(nctExistingUser);
            nctRedirectAttributes.addFlashAttribute("nctSuccess", "Cập nhật người dùng thành công!");

        } catch (Exception e) {
            nctRedirectAttributes.addFlashAttribute("nctError", "Lỗi khi cập nhật người dùng: " + e.getMessage());
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String nctShowDeleteForm(@PathVariable Long id, Model model) {
        Optional<NctUser> nctUserOpt = nctUserService.nctGetUserById(id);
        if (nctUserOpt.isEmpty()) {
            return "redirect:/admin/users";
        }

        model.addAttribute("nctUser", nctUserOpt.get());
        model.addAttribute("nctPageTitle", "Xóa Người dùng - Admin");
        return "admin/users/nct-user-delete";
    }

    @PostMapping("/delete/{id}")
    public String nctDeleteUser(@PathVariable Long id, RedirectAttributes nctRedirectAttributes) {
        try {
            // Kiểm tra xem user có đơn hàng không trước khi xóa
            Optional<NctUser> nctUserOpt = nctUserService.nctGetUserById(id);
            if (nctUserOpt.isPresent()) {
                NctUser nctUser = nctUserOpt.get();
                if (nctUser.getNctOrders() != null && !nctUser.getNctOrders().isEmpty()) {
                    nctRedirectAttributes.addFlashAttribute("nctError",
                            "Không thể xóa người dùng vì có đơn hàng liên quan!");
                    return "redirect:/admin/users";
                }

                nctUserService.nctDeleteUser(id);
                nctRedirectAttributes.addFlashAttribute("nctSuccess", "Xóa người dùng thành công!");
            }
        } catch (Exception e) {
            nctRedirectAttributes.addFlashAttribute("nctError", "Lỗi khi xóa người dùng: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
}