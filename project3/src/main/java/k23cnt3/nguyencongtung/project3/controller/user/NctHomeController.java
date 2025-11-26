package k23cnt3.nguyencongtung.project3.controller.user;

import k23cnt3.nguyencongtung.project3.entity.NctCategory;
import k23cnt3.nguyencongtung.project3.entity.NctProduct;
import k23cnt3.nguyencongtung.project3.service.NctCategoryService;
import k23cnt3.nguyencongtung.project3.service.NctProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class NctHomeController {

    private final NctProductService nctProductService;
    private final NctCategoryService nctCategoryService;

    @Autowired
    public NctHomeController(NctProductService nctProductService, NctCategoryService nctCategoryService) {
        this.nctProductService = nctProductService;
        this.nctCategoryService = nctCategoryService;
    }

    @GetMapping
    public String nctHomePage(Model model) {
        // Lấy danh sách danh mục
        List<NctCategory> nctCategories = nctCategoryService.nctGetAllCategories();

        // Lấy sản phẩm theo từng danh mục (giới hạn 12 sản phẩm mỗi danh mục)
        Map<String, List<NctProduct>> nctProductsByCategory = new HashMap<>();
        Map<String, String> nctCategoryUrls = new HashMap<>();

        for (NctCategory category : nctCategories) {
            List<NctProduct> products = nctProductService.nctGetActiveProductsByCategoryId(category.getNctCategoryId());
            // Giới hạn 12 sản phẩm đầu tiên
            if (products.size() > 12) {
                products = products.subList(0, 12);
            }
            nctProductsByCategory.put(category.getNctCategoryName(), products);
            nctCategoryUrls.put(category.getNctCategoryName(), "/products?category=" + category.getNctCategoryId());
        }

        // Lấy sản phẩm nổi bật
        List<NctProduct> nctFeaturedProducts = nctProductService.nctGetActiveFeaturedProducts();

        model.addAttribute("nctCategories", nctCategories);
        model.addAttribute("nctProductsByCategory", nctProductsByCategory);
        model.addAttribute("nctCategoryUrls", nctCategoryUrls);
        model.addAttribute("nctFeaturedProducts", nctFeaturedProducts);
        model.addAttribute("nctPageTitle", "OTAKU.vn - Figure Chính Hãng Nhật Bản");

        return "user/nct-home";
    }

    @GetMapping("/about")
    public String nctAboutPage(Model model) {
        model.addAttribute("nctPageTitle", "Giới thiệu - OTAKU.vn");
        return "user/nct-about";
    }

    @GetMapping("/contact")
    public String nctContactPage(Model model) {
        model.addAttribute("nctPageTitle", "Liên hệ - OTAKU.vn");
        return "user/nct-contact";
    }
}