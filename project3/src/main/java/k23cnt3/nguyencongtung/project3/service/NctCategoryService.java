package k23cnt3.nguyencongtung.project3.service;

import k23cnt3.nguyencongtung.project3.entity.NctCategory;
import k23cnt3.nguyencongtung.project3.repository.NctCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NctCategoryService {
    private final NctCategoryRepository nctCategoryRepository;

    @Autowired
    public NctCategoryService(NctCategoryRepository nctCategoryRepository) {
        this.nctCategoryRepository = nctCategoryRepository;
    }
    public List<NctCategory> nctGetAllCategories() {
        return nctCategoryRepository.findAll();
    }

    public Optional<NctCategory> nctGetCategoryById(Long nctId) {
        return nctCategoryRepository.findById(nctId);
    }

    public NctCategory nctSaveCategory(NctCategory nctCategory) {
        return nctCategoryRepository.save(nctCategory);
    }

    public void nctDeleteCategory(Long nctId) {
        nctCategoryRepository.deleteById(nctId);
    }

    public boolean nctCategoryExistsByName(String nctCategoryName) {
        return nctCategoryRepository.existsByNctCategoryName(nctCategoryName);
    }

    public List<NctCategory> nctSearchCategories(String nctKeyword) {
        return nctCategoryRepository.findByNctCategoryNameContainingIgnoreCase(nctKeyword);
    }
}