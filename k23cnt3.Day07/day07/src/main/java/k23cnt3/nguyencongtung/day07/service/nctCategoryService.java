package k23cnt3.nguyencongtung.day07.service;

import k23cnt3.nguyencongtung.day07.entity.nctCategory;
import k23cnt3.nguyencongtung.day07.repository.nctCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class nctCategoryService {
    @Autowired
    private nctCategoryRepository nctCategoryRepository;
    
    public List<nctCategory> nctGetAllCategories() {
        return nctCategoryRepository.findAll();
    }
    
    public Optional<nctCategory> nctGetCategoryById(Long nctId) {
        return nctCategoryRepository.findById(nctId);
    }
    
    public nctCategory nctSaveCategory(nctCategory nctCategory) {
        return nctCategoryRepository.save(nctCategory);
    }
    
    public void nctDeleteCategory(Long nctId) {
        nctCategoryRepository.deleteById(nctId);
    }
}