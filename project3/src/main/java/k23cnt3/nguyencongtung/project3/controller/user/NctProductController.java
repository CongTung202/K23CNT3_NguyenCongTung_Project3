package k23cnt3.nguyencongtung.project3.controller.user;

import k23cnt3.nguyencongtung.project3.entity.NctProduct;
import k23cnt3.nguyencongtung.project3.service.NctProductService;
import k23cnt3.nguyencongtung.project3.service.NctCategoryService;
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
            @RequestParam(value = "minPrice", required = false) Double nctMinPrice,
            @RequestParam(value = "maxPrice", required = false) Double nctMaxPrice,
            Model model) {

        List<NctProduct> nctProducts;

        if (nctSearchKeyword != null && !nctSearchKeyword.isEmpty()) {
            nctProducts = nctProductService.nctSearchProducts(nctSearchKeyword);
            model.addAttribute("nctSearchKeyword", nctSearchKeyword);
        } else if (nctMinPrice != null && nctMaxPrice != null) {
            nctProducts = nctProductService.nctGetProductsByPriceRange(nctMinPrice, nctMaxPrice);
        } else {
            nctProducts = nctProductService.nctGetActiveProducts();
        }

        model.addAttribute("nctProducts", nctProducts);
        model.addAttribute("nctCategories", nctCategoryService.nctGetAllCategories());
        model.addAttribute("nctPageTitle", "Sản phẩm - NCT Otaku Store");

        return "user/nct-products";
    }

    @GetMapping("/{id}")
    public String nctProductDetail(@PathVariable("id") Long nctProductId, Model model) {
        Optional<NctProduct> nctProductOpt = nctProductService.nctGetProductById(nctProductId);

        if (nctProductOpt.isPresent()) {
            NctProduct nctProduct = nctProductOpt.get();
            model.addAttribute("nctProduct", nctProduct);
            model.addAttribute("nctRelatedProducts", nctProductService.nctGetProductsByCategory(nctProduct.getNctCategory()));
            model.addAttribute("nctPageTitle", nctProduct.getNctProductName() + " - NCT Otaku Store");
            return "user/nct-product-detail";
        }

        return "redirect:/products";
    }
}