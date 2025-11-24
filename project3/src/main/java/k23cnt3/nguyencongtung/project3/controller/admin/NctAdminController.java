package k23cnt3.nguyencongtung.project3.controller.admin;

import k23cnt3.nguyencongtung.project3.entity.NctOrder;
import k23cnt3.nguyencongtung.project3.entity.NctUser;
import k23cnt3.nguyencongtung.project3.service.NctProductService;
import k23cnt3.nguyencongtung.project3.service.NctCategoryService;
import k23cnt3.nguyencongtung.project3.service.NctOrderService;
import k23cnt3.nguyencongtung.project3.service.NctUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class NctAdminController {

    private final NctProductService nctProductService;
    private final NctCategoryService nctCategoryService;
    private final NctOrderService nctOrderService;
    private final NctUserService nctUserService; // THÊM DÒNG NÀY

    @Autowired
    public NctAdminController(NctProductService nctProductService,
                              NctCategoryService nctCategoryService,
                              NctOrderService nctOrderService,
                              NctUserService nctUserService) { // THÊM PARAMETER NÀY
        this.nctProductService = nctProductService;
        this.nctCategoryService = nctCategoryService;
        this.nctOrderService = nctOrderService;
        this.nctUserService = nctUserService; // THÊM DÒNG NÀY
    }

    @GetMapping("/dashboard")
    public String nctDashboard(Model model) {
        Long nctTotalProducts = (long) nctProductService.nctGetAllProducts().size();
        Long nctTotalCategories = (long) nctCategoryService.nctGetAllCategories().size();
        Long nctTotalOrders = nctOrderService.nctGetOrderCountByStatus(null);
        Double nctTotalRevenue = nctOrderService.nctGetTotalRevenue();
        Long nctTotalUsers = nctUserService.nctCountTotalUsers(); // THÊM DÒNG NÀY

        model.addAttribute("nctTotalProducts", nctTotalProducts);
        model.addAttribute("nctTotalCategories", nctTotalCategories);
        model.addAttribute("nctTotalOrders", nctTotalOrders);
        model.addAttribute("nctTotalRevenue", nctTotalRevenue);
        model.addAttribute("nctTotalUsers", nctTotalUsers); // THÊM DÒNG NÀY
        model.addAttribute("nctPageTitle", "Dashboard - Admin");

        return "admin/nct-dashboard";
    }
}