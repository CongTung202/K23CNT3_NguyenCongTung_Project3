package k23cnt3.nguyencongtung.day07.controller;

import k23cnt3.nguyencongtung.day07.entity.nctCategory;
import k23cnt3.nguyencongtung.day07.entity.nctProduct;
import k23cnt3.nguyencongtung.day07.service.nctCategoryService;
import k23cnt3.nguyencongtung.day07.service.nctProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/nct/products")
public class nctProductController {
    
    @Autowired
    private nctProductService nctProductService;
    
    @Autowired
    private nctCategoryService nctCategoryService;
    
    @GetMapping
    public String nctListProducts(Model nctModel) {
        nctModel.addAttribute("nctProducts", nctProductService.nctGetAllProducts());
        return "nct/product/nct-product-list";
    }
    
    @GetMapping("/nct-create")
    public String nctShowCreateForm(Model nctModel) {
        nctModel.addAttribute("nctProduct", new nctProduct());
        List<nctCategory> nctCategories = nctCategoryService.nctGetAllCategories();
        nctModel.addAttribute("nctCategories", nctCategories);
        return "nct/product/nct-product-form";
    }
    
    @GetMapping("/nct-edit/{nctId}")
    public String nctShowEditForm(@PathVariable("nctId") Long nctId, Model nctModel) {
        nctModel.addAttribute("nctProduct", nctProductService.nctFindById(nctId).orElse(null));
        List<nctCategory> nctCategories = nctCategoryService.nctGetAllCategories();
        nctModel.addAttribute("nctCategories", nctCategories);
        return "nct/product/nct-product-form";
    }
    
    @PostMapping("/nct-create")
    public String nctSaveProduct(@ModelAttribute("nctProduct") nctProduct nctProduct) {
        nctProductService.nctSaveProduct(nctProduct);
        return "redirect:/nct/products";
    }
    
    @PostMapping("/nct-create/{nctId}")
    public String nctUpdateProduct(@PathVariable Long nctId, @ModelAttribute nctProduct nctProduct) {
        nctProduct.setNctId(nctId);
        nctProductService.nctSaveProduct(nctProduct);
        return "redirect:/nct/products";
    }
    
    @GetMapping("/nct-delete/{nctId}")
    public String nctDeleteProduct(@PathVariable("nctId") Long nctId) {
        nctProductService.nctDeleteProduct(nctId);
        return "redirect:/nct/products";
    }
}