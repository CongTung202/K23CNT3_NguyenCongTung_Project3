package k23cnt3.nguyencongtung.project3.service;

import k23cnt3.nguyencongtung.project3.entity.NctCategory;
import k23cnt3.nguyencongtung.project3.entity.NctProduct;
import k23cnt3.nguyencongtung.project3.repository.NctProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NctProductService {
    private final NctProductRepository nctProductRepository;

    @Autowired
    public NctProductService(NctProductRepository nctProductRepository) {
        this.nctProductRepository = nctProductRepository;
    }
    public List<NctProduct> nctGetAllProducts() {
        return nctProductRepository.findAll();
    }

    public Optional<NctProduct> nctGetProductById(Long nctId) {
        return nctProductRepository.findById(nctId);
    }

    public NctProduct nctSaveProduct(NctProduct nctProduct) {
        return nctProductRepository.save(nctProduct);
    }

    public void nctDeleteProduct(Long nctId) {
        nctProductRepository.deleteById(nctId);
    }

    public List<NctProduct> nctGetProductsByCategory(NctCategory nctCategory) {
        return nctProductRepository.findByNctCategory(nctCategory);
    }

    public List<NctProduct> nctSearchProducts(String nctKeyword) {
        return nctProductRepository.findByNctProductNameContainingIgnoreCase(nctKeyword);
    }

    public List<NctProduct> nctGetFeaturedProducts() {
        return nctProductRepository.findTop8ByOrderByNctCreatedAtDesc();
    }

    public List<NctProduct> nctGetActiveProducts() {
        return nctProductRepository.findByNctStatus(NctProduct.NctStatus.ACTIVE);
    }

    public List<NctProduct> nctGetProductsInStock() {
        return nctProductRepository.findByNctStockQuantityGreaterThan(0);
    }

    public List<NctProduct> nctGetProductsByPriceRange(Double nctMinPrice, Double nctMaxPrice) {
        return nctProductRepository.findByPriceRange(nctMinPrice, nctMaxPrice);
    }
}