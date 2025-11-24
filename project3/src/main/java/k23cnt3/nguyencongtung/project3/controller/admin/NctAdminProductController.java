package k23cnt3.nguyencongtung.project3.controller.admin;

import k23cnt3.nguyencongtung.project3.entity.NctProduct;
import k23cnt3.nguyencongtung.project3.entity.NctCategory;
import k23cnt3.nguyencongtung.project3.service.NctProductService;
import k23cnt3.nguyencongtung.project3.service.NctCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/products")
public class NctAdminProductController {

    private final NctProductService nctProductService;
    private final NctCategoryService nctCategoryService;

    // Thư mục upload ảnh
    public static String nctUploadDirectory = System.getProperty("user.dir") + "/uploads";

    @Autowired
    public NctAdminProductController(NctProductService nctProductService,
                                     NctCategoryService nctCategoryService) {
        this.nctProductService = nctProductService;
        this.nctCategoryService = nctCategoryService;
    }

    @GetMapping
    public String nctProductManagement(Model model) {
        List<NctProduct> nctProducts = nctProductService.nctGetAllProducts();
        List<NctCategory> nctCategories = nctCategoryService.nctGetAllCategories();

        model.addAttribute("nctProducts", nctProducts);
        model.addAttribute("nctCategories", nctCategories);
        model.addAttribute("nctProduct", new NctProduct()); // For add form
        model.addAttribute("nctPageTitle", "Quản lý Sản phẩm - Admin");

        return "admin/nct-products";
    }

    @PostMapping("/save")
    public String nctSaveProduct(
            @ModelAttribute NctProduct nctProduct,
            @RequestParam("nctImageFile") MultipartFile nctImageFile,
            @RequestParam("nctCategoryId") Long nctCategoryId,
            RedirectAttributes nctRedirectAttributes) {

        try {
            // Xử lý upload ảnh
            if (!nctImageFile.isEmpty()) {
                String nctFileName = System.currentTimeMillis() + "_" + nctImageFile.getOriginalFilename();
                Path nctFilePath = Paths.get(nctUploadDirectory, nctFileName);
                Files.createDirectories(nctFilePath.getParent());
                Files.write(nctFilePath, nctImageFile.getBytes());
                nctProduct.setNctImageUrl("/uploads/" + nctFileName);
            }

            // Set category
            Optional<NctCategory> nctCategory = nctCategoryService.nctGetCategoryById(nctCategoryId);
            nctCategory.ifPresent(nctProduct::setNctCategory);

            nctProductService.nctSaveProduct(nctProduct);
            nctRedirectAttributes.addFlashAttribute("nctSuccess", "Lưu sản phẩm thành công!");

        } catch (Exception e) {
            nctRedirectAttributes.addFlashAttribute("nctError", "Lỗi khi lưu sản phẩm: " + e.getMessage());
        }

        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    @ResponseBody
    public NctProduct nctGetProductForEdit(@PathVariable Long id) {
        return nctProductService.nctGetProductById(id).orElse(null);
    }

    @PostMapping("/delete/{id}")
    public String nctDeleteProduct(@PathVariable Long id, RedirectAttributes nctRedirectAttributes) {
        try {
            nctProductService.nctDeleteProduct(id);
            nctRedirectAttributes.addFlashAttribute("nctSuccess", "Xóa sản phẩm thành công!");
        } catch (Exception e) {
            nctRedirectAttributes.addFlashAttribute("nctError", "Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
        return "redirect:/admin/products";
    }
}