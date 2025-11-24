package k23cnt3.nguyencongtung.project3.controller.admin;

import k23cnt3.nguyencongtung.project3.entity.NctOrder;
import k23cnt3.nguyencongtung.project3.service.NctOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/orders")
public class NctAdminOrderController {

    private final NctOrderService nctOrderService;

    @Autowired
    public NctAdminOrderController(NctOrderService nctOrderService) {
        this.nctOrderService = nctOrderService;
    }

    @GetMapping
    public String nctOrderList(Model model) {
        List<NctOrder> nctOrders = nctOrderService.nctGetAllOrders();
        model.addAttribute("nctOrders", nctOrders);
        model.addAttribute("nctPageTitle", "Quản lý Đơn hàng - Admin");
        return "admin/orders/nct-order-list";
    }

    @GetMapping("/view/{id}")
    public String nctViewOrder(@PathVariable Long id, Model model) {
        Optional<NctOrder> nctOrderOpt = nctOrderService.nctGetOrderById(id);
        if (nctOrderOpt.isEmpty()) {
            return "redirect:/admin/orders";
        }

        model.addAttribute("nctOrder", nctOrderOpt.get());
        model.addAttribute("nctPageTitle", "Chi tiết Đơn hàng - Admin");
        return "admin/orders/nct-order-view";
    }

    @PostMapping("/update-status/{id}")
    public String nctUpdateOrderStatus(
            @PathVariable Long id,
            @RequestParam NctOrder.NctOrderStatus nctStatus,
            RedirectAttributes nctRedirectAttributes) {

        try {
            nctOrderService.nctUpdateOrderStatus(id, nctStatus);
            nctRedirectAttributes.addFlashAttribute("nctSuccess", "Cập nhật trạng thái đơn hàng thành công!");
        } catch (Exception e) {
            nctRedirectAttributes.addFlashAttribute("nctError", "Lỗi khi cập nhật trạng thái: " + e.getMessage());
        }

        return "redirect:/admin/orders/view/" + id;
    }

    @GetMapping("/cancel/{id}")
    public String nctShowCancelForm(@PathVariable Long id, Model model) {
        Optional<NctOrder> nctOrderOpt = nctOrderService.nctGetOrderById(id);
        if (nctOrderOpt.isEmpty()) {
            return "redirect:/admin/orders";
        }

        model.addAttribute("nctOrder", nctOrderOpt.get());
        model.addAttribute("nctPageTitle", "Hủy Đơn hàng - Admin");
        return "admin/orders/nct-order-cancel";
    }

    @PostMapping("/cancel/{id}")
    public String nctCancelOrder(@PathVariable Long id, RedirectAttributes nctRedirectAttributes) {
        try {
            nctOrderService.nctCancelOrder(id);
            nctRedirectAttributes.addFlashAttribute("nctSuccess", "Hủy đơn hàng thành công!");
        } catch (Exception e) {
            nctRedirectAttributes.addFlashAttribute("nctError", "Lỗi khi hủy đơn hàng: " + e.getMessage());
        }
        return "redirect:/admin/orders";
    }
}