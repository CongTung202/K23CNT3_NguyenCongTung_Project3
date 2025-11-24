package k23cnt3.nguyencongtung.project3.controller.user;

import k23cnt3.nguyencongtung.project3.entity.NctProduct;
import k23cnt3.nguyencongtung.project3.service.NctProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class NctHomeController {

    private final NctProductService nctProductService;

    @Autowired
    public NctHomeController(NctProductService nctProductService) {
        this.nctProductService = nctProductService;
    }

    @GetMapping
    public String nctHomePage(Model model) {
        List<NctProduct> nctFeaturedProducts = nctProductService.nctGetFeaturedProducts();
        List<NctProduct> nctNewProducts = nctProductService.nctGetActiveProducts();

        model.addAttribute("nctFeaturedProducts", nctFeaturedProducts);
        model.addAttribute("nctNewProducts", nctNewProducts);
        model.addAttribute("nctPageTitle", "NCT Otaku Store - Đồ lưu niệm Anime & Game");

        return "user/nct-home";
    }

    @GetMapping("/about")
    public String nctAboutPage(Model model) {
        model.addAttribute("nctPageTitle", "Giới thiệu - NCT Otaku Store");
        return "user/nct-about";
    }

    @GetMapping("/contact")
    public String nctContactPage(Model model) {
        model.addAttribute("nctPageTitle", "Liên hệ - NCT Otaku Store");
        return "user/nct-contact";
    }
}