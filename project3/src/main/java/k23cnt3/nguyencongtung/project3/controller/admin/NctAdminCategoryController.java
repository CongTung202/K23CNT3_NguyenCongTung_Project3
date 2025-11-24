package k23cnt3.nguyencongtung.project3.controller.admin;

import k23cnt3.nguyencongtung.project3.entity.NctCategory;
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
@RequestMapping("/admin/categories")
public class NctAdminCategoryController {

    private final NctCategoryService nctCategoryService;

    @Autowired
    public NctAdminCategoryController(NctCategoryService nctCategoryService) {
        this.nctCategoryService = nctCategoryService;
    }

    @GetMapping
    public String nctCategoryManagement(Model model) {
        List<NctCategory> nctCategories = nctCategoryService.nctGetAllCategories();
        model.addAttribute("nctCategories", nctCategories);
        model.addAttribute("nctCategory", new NctCategory());
        model.addAttribute("nctPageTitle", "Quản lý Danh mục - Admin");
        return "admin/nct-categories";
    }

    @PostMapping("/save")
    public String nctSaveCategory(
            @ModelAttribute NctCategory nctCategory,
            @RequestParam(value = "nctImageFile", required = false) MultipartFile nctImageFile,
            RedirectAttributes nctRedirectAttributes) {

        try {
            // Xử lý upload ảnh
            if (nctImageFile != null && !nctImageFile.isEmpty()) {
                String nctFileName = System.currentTimeMillis() + "_" + nctImageFile.getOriginalFilename();
                Path nctFilePath = Paths.get(NctAdminProductController.nctUploadDirectory, nctFileName);
                Files.createDirectories(nctFilePath.getParent());
                Files.write(nctFilePath, nctImageFile.getBytes());
                nctCategory.setNctImageUrl("/uploads/" + nctFileName);
            }

            nctCategoryService.nctSaveCategory(nctCategory);
            nctRedirectAttributes.addFlashAttribute("nctSuccess", "Lưu danh mục thành công!");

        } catch (Exception e) {
            nctRedirectAttributes.addFlashAttribute("nctError", "Lỗi khi lưu danh mục: " + e.getMessage());
        }

        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    @ResponseBody
    public NctCategory nctGetCategoryForEdit(@PathVariable Long id) {
        return nctCategoryService.nctGetCategoryById(id).orElse(null);
    }

    @PostMapping("/delete/{id}")
    public String nctDeleteCategory(@PathVariable Long id, RedirectAttributes nctRedirectAttributes) {
        try {
            nctCategoryService.nctDeleteCategory(id);
            nctRedirectAttributes.addFlashAttribute("nctSuccess", "Xóa danh mục thành công!");
        } catch (Exception e) {
            nctRedirectAttributes.addFlashAttribute("nctError", "Lỗi khi xóa danh mục: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }
}