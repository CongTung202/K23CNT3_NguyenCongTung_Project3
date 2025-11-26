package k23cnt3.nguyencongtung.project3.controller.user;

import k23cnt3.nguyencongtung.project3.entity.NctCategory;
import k23cnt3.nguyencongtung.project3.entity.NctProduct;
import k23cnt3.nguyencongtung.project3.service.NctCategoryService;
import k23cnt3.nguyencongtung.project3.service.NctProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class NctProductController {

    private final NctProductService nctProductService;
    private final NctCategoryService nctCategoryService;

    @Autowired
    public NctProductController(NctProductService nctProductService, NctCategoryService nctCategoryService) {
        this.nctProductService = nctProductService;
        this.nctCategoryService = nctCategoryService;
    }

    @GetMapping
    public String nctProductsPage(
            @RequestParam(value = "category", required = false) Long nctCategoryId,
            @RequestParam(value = "search", required = false) String nctSearchKeyword,
            Model model) {

        List<NctProduct> nctProducts;
        String nctHeaderTitle = "Tất cả sản phẩm";

        if (nctSearchKeyword != null && !nctSearchKeyword.isEmpty()) {
            nctProducts = nctProductService.nctSearchActiveProducts(nctSearchKeyword);
            model.addAttribute("nctSearchKeyword", nctSearchKeyword);
            nctHeaderTitle = "Kết quả cho '" + nctSearchKeyword + "'";
        } else if (nctCategoryId != null) {
            nctProducts = nctProductService.nctGetActiveProductsByCategoryId(nctCategoryId);
            Optional<NctCategory> categoryOpt = nctCategoryService.nctGetCategoryById(nctCategoryId);
            if (categoryOpt.isPresent()) {
                nctHeaderTitle = categoryOpt.get().getNctCategoryName();
            }
            model.addAttribute("nctSelectedCategoryId", nctCategoryId);
        } else {
            nctProducts = nctProductService.nctGetActiveProducts();
        }

        model.addAttribute("nctProducts", nctProducts);
        model.addAttribute("nctCategories", nctCategoryService.nctGetCategoriesWithActiveProducts());
        model.addAttribute("nctHeaderTitle", nctHeaderTitle);
        model.addAttribute("nctPageTitle", nctHeaderTitle + " - UMACT Store");

        return "user/nct-products";
    }

    @GetMapping("/{id}")
    public String nctProductDetail(@PathVariable("id") Long nctProductId, Model model) {
        Optional<NctProduct> nctProductOpt = nctProductService.nctGetProductById(nctProductId);

        if (nctProductOpt.isPresent()) {
            NctProduct nctProduct = nctProductOpt.get();
            model.addAttribute("nctProduct", nctProduct);
            if (nctProduct.getNctCategory() != null) {
                model.addAttribute("nctRelatedProducts", nctProductService.nctGetActiveProductsByCategory(nctProduct.getNctCategory()));
            }
            model.addAttribute("nctPageTitle", nctProduct.getNctProductName() + " - UMACT Store");
            return "user/nct-product-detail";
        }

        return "redirect:/products";
    }
}