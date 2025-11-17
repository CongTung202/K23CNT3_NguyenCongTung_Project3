package k23cnt3.nguyencongtung.day07.controller;

import k23cnt3.nguyencongtung.day07.entity.nctCategory;
import k23cnt3.nguyencongtung.day07.service.nctCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nct/category")
public class nctCategoryController {
    
    @Autowired
    private nctCategoryService nctCategoryService;
    
    @GetMapping
    public String nctListCategories(Model nctModel) {
        nctModel.addAttribute("nctCategories", nctCategoryService.nctGetAllCategories());
        return "nct/category/nct-category-list";
    }
    
    @GetMapping("/nct-create")
    public String nctShowCreateForm(Model nctModel) {
        nctModel.addAttribute("nctCategory", new nctCategory());
        return "nct/category/nct-category-form";
    }
    
    @GetMapping("/nct-edit/{nctId}")
    public String nctShowEditForm(@PathVariable("nctId") Long nctId, Model nctModel) {
        nctModel.addAttribute("nctCategory", nctCategoryService.nctGetCategoryById(nctId).orElse(null));
        return "nct/category/nct-category-form";
    }
    
    @PostMapping("/nct-create")
    public String nctSaveCategory(@ModelAttribute("nctCategory") nctCategory nctCategory) {
        nctCategoryService.nctSaveCategory(nctCategory);
        return "redirect:/nct/category";
    }
    
    @PostMapping("/nct-create/{nctId}")
    public String nctUpdateCategory(@PathVariable Long nctId, @ModelAttribute nctCategory nctCategory) {
        nctCategory.setNctId(nctId);
        nctCategoryService.nctSaveCategory(nctCategory);
        return "redirect:/nct/category";
    }
    
    @GetMapping("/nct-delete/{nctId}")
    public String nctDeleteCategory(@PathVariable("nctId") Long nctId) {
        nctCategoryService.nctDeleteCategory(nctId);
        return "redirect:/nct/category";
    }
}