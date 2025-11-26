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

        // Thống kê
        long nctPendingCount = nctOrders.stream()
                .filter(order -> order.getNctStatus() == NctOrder.NctOrderStatus.PENDING)
                .count();
        long nctConfirmedCount = nctOrders.stream()
                .filter(order -> order.getNctStatus() == NctOrder.NctOrderStatus.CONFIRMED)
                .count();
        long nctShippingCount = nctOrders.stream()
                .filter(order -> order.getNctStatus() == NctOrder.NctOrderStatus.SHIPPING)
                .count();
        long nctDeliveredCount = nctOrders.stream()
                .filter(order -> order.getNctStatus() == NctOrder.NctOrderStatus.DELIVERED)
                .count();
        long nctCancelledCount = nctOrders.stream()
                .filter(order -> order.getNctStatus() == NctOrder.NctOrderStatus.CANCELLED)
                .count();

        model.addAttribute("nctOrders", nctOrders);
        model.addAttribute("nctPendingCount", nctPendingCount);
        model.addAttribute("nctConfirmedCount", nctConfirmedCount);
        model.addAttribute("nctShippingCount", nctShippingCount);
        model.addAttribute("nctDeliveredCount", nctDeliveredCount);
        model.addAttribute("nctCancelledCount", nctCancelledCount);
        model.addAttribute("nctPageTitle", "Quản lý Đơn hàng - Admin");
        return "admin/orders/nct-order-list";
    }

    @GetMapping("/view/{id}")
    public String nctViewOrder(@PathVariable Long id, Model model) {
        Optional<NctOrder> nctOrderOpt = nctOrderService.nctGetOrderById(id);
        if (nctOrderOpt.isEmpty()) {
            return "redirect:/admin/orders";
        }

        NctOrder nctOrder = nctOrderOpt.get();
        model.addAttribute("nctOrder", nctOrder);
        model.addAttribute("nctOrderStatuses", NctOrder.NctOrderStatus.values());
        model.addAttribute("nctPageTitle", "Chi tiết Đơn hàng #" + nctOrder.getNctOrderId());
        return "admin/orders/nct-order-view";
    }

    @GetMapping("/edit/{id}")
    public String nctShowEditForm(@PathVariable Long id, Model model) {
        Optional<NctOrder> nctOrderOpt = nctOrderService.nctGetOrderById(id);
        if (nctOrderOpt.isEmpty()) {
            return "redirect:/admin/orders";
        }

        NctOrder nctOrder = nctOrderOpt.get();
        if (!nctOrder.isNctEditable()) {
            return "redirect:/admin/orders/view/" + id;
        }

        model.addAttribute("nctOrder", nctOrder);
        model.addAttribute("nctPageTitle", "Chỉnh sửa Đơn hàng #" + nctOrder.getNctOrderId());
        return "admin/orders/nct-order-edit";
    }

    @PostMapping("/edit/{id}")
    public String nctUpdateOrder(
            @PathVariable Long id,
            @ModelAttribute NctOrder nctOrder,
            RedirectAttributes nctRedirectAttributes) {

        try {
            Optional<NctOrder> nctExistingOrderOpt = nctOrderService.nctGetOrderById(id);
            if (nctExistingOrderOpt.isEmpty()) {
                nctRedirectAttributes.addFlashAttribute("nctError", "Đơn hàng không tồn tại!");
                return "redirect:/admin/orders";
            }

            NctOrder nctExistingOrder = nctExistingOrderOpt.get();

            // Chỉ cho phép chỉnh sửa một số thông tin
            nctExistingOrder.setNctShippingAddress(nctOrder.getNctShippingAddress());
            nctExistingOrder.setNctPhone(nctOrder.getNctPhone());
            nctExistingOrder.setNctUpdatedAt(java.time.LocalDateTime.now());

            nctOrderService.nctSaveOrder(nctExistingOrder);
            nctRedirectAttributes.addFlashAttribute("nctSuccess", "Cập nhật đơn hàng thành công!");

        } catch (Exception e) {
            nctRedirectAttributes.addFlashAttribute("nctError", "Lỗi khi cập nhật đơn hàng: " + e.getMessage());
        }

        return "redirect:/admin/orders/view/" + id;
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

    // SỬA: Thêm RedirectAttributes parameter
    @GetMapping("/cancel/{id}")
    public String nctShowCancelForm(@PathVariable Long id, Model model, RedirectAttributes nctRedirectAttributes) {
        Optional<NctOrder> nctOrderOpt = nctOrderService.nctGetOrderById(id);
        if (nctOrderOpt.isEmpty()) {
            return "redirect:/admin/orders";
        }

        NctOrder nctOrder = nctOrderOpt.get();
        if (!nctOrder.isNctCancellable()) {
            nctRedirectAttributes.addFlashAttribute("nctError", "Không thể hủy đơn hàng này!");
            return "redirect:/admin/orders/view/" + id;
        }

        model.addAttribute("nctOrder", nctOrder);
        model.addAttribute("nctPageTitle", "Hủy Đơn hàng #" + nctOrder.getNctOrderId());
        return "admin/orders/nct-order-cancel";
    }

    @PostMapping("/cancel/{id}")
    public String nctCancelOrder(
            @PathVariable Long id,
            @RequestParam(required = false) String nctCancelReason,
            RedirectAttributes nctRedirectAttributes) {

        try {
            nctOrderService.nctCancelOrder(id);
            nctRedirectAttributes.addFlashAttribute("nctSuccess", "Hủy đơn hàng thành công!");
        } catch (Exception e) {
            nctRedirectAttributes.addFlashAttribute("nctError", "Lỗi khi hủy đơn hàng: " + e.getMessage());
        }
        return "redirect:/admin/orders";
    }

    @GetMapping("/delete/{id}")
    public String nctShowDeleteForm(@PathVariable Long id, Model model) {
        Optional<NctOrder> nctOrderOpt = nctOrderService.nctGetOrderById(id);
        if (nctOrderOpt.isEmpty()) {
            return "redirect:/admin/orders";
        }

        model.addAttribute("nctOrder", nctOrderOpt.get());
        model.addAttribute("nctPageTitle", "Xóa Đơn hàng #" + id);
        return "admin/orders/nct-order-delete";
    }

    @PostMapping("/delete/{id}")
    public String nctDeleteOrder(@PathVariable Long id, RedirectAttributes nctRedirectAttributes) {
        try {
            nctOrderService.nctDeleteOrder(id);
            nctRedirectAttributes.addFlashAttribute("nctSuccess", "Xóa đơn hàng thành công!");
        } catch (Exception e) {
            nctRedirectAttributes.addFlashAttribute("nctError", "Lỗi khi xóa đơn hàng: " + e.getMessage());
        }
        return "redirect:/admin/orders";
    }
}