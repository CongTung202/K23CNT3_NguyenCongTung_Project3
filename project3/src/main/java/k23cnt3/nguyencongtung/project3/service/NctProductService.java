package k23cnt3.nguyencongtung.project3.service;

import k23cnt3.nguyencongtung.project3.entity.NctCategory;
import k23cnt3.nguyencongtung.project3.entity.NctProduct;
import k23cnt3.nguyencongtung.project3.repository.NctProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class NctProductService {
    private final NctProductRepository nctProductRepository;

    @Autowired
    public NctProductService(NctProductRepository nctProductRepository) {
        this.nctProductRepository = nctProductRepository;
    }

    // Lấy tất cả sản phẩm
    public List<NctProduct> nctGetAllProducts() {
        return nctProductRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<NctProduct> nctGetProductById(Long nctId) {
        return nctProductRepository.findById(nctId);
    }

    // Lưu sản phẩm
    public NctProduct nctSaveProduct(NctProduct nctProduct) {
        return nctProductRepository.save(nctProduct);
    }

    // Xóa sản phẩm
    public void nctDeleteProduct(Long nctId) {
        nctProductRepository.deleteById(nctId);
    }

    // Lấy sản phẩm theo danh mục
    public List<NctProduct> nctGetProductsByCategory(NctCategory nctCategory) {
        return nctProductRepository.findByNctCategory(nctCategory);
    }

    // Lấy sản phẩm theo ID danh mục
    public List<NctProduct> nctGetProductsByCategoryId(Long categoryId) {
        return nctProductRepository.findByNctCategory_NctCategoryId(categoryId);
    }

    // Lấy sản phẩm theo danh mục và trạng thái active
    public List<NctProduct> nctGetActiveProductsByCategory(NctCategory nctCategory) {
        return nctProductRepository.findByNctCategoryAndNctStatus(nctCategory, NctProduct.NctStatus.ACTIVE);
    }

    // Lấy sản phẩm theo ID danh mục và trạng thái active
    public List<NctProduct> nctGetActiveProductsByCategoryId(Long categoryId) {
        return nctProductRepository.findByNctCategory_NctCategoryIdAndNctStatus(categoryId, NctProduct.NctStatus.ACTIVE);
    }

    // Tìm kiếm sản phẩm
    public List<NctProduct> nctSearchProducts(String nctKeyword) {
        return nctProductRepository.findByNctProductNameContainingIgnoreCase(nctKeyword);
    }

    // Tìm kiếm sản phẩm active
    public List<NctProduct> nctSearchActiveProducts(String nctKeyword) {
        return nctProductRepository.findByNctProductNameContainingIgnoreCaseAndNctStatus(nctKeyword, NctProduct.NctStatus.ACTIVE);
    }

    // Lấy sản phẩm nổi bật (8 sản phẩm mới nhất)
    public List<NctProduct> nctGetFeaturedProducts() {
        return nctProductRepository.findTop8ByOrderByNctCreatedAtDesc();
    }

    // Lấy sản phẩm nổi bật active
    public List<NctProduct> nctGetActiveFeaturedProducts() {
        return nctProductRepository.findTop8ByNctStatusOrderByNctCreatedAtDesc(NctProduct.NctStatus.ACTIVE);
    }

    // Lấy sản phẩm active
    public List<NctProduct> nctGetActiveProducts() {
        return nctProductRepository.findByNctStatus(NctProduct.NctStatus.ACTIVE);
    }

    // Lấy sản phẩm còn hàng
    public List<NctProduct> nctGetProductsInStock() {
        return nctProductRepository.findByNctStockQuantityGreaterThan(0);
    }

    // Lấy sản phẩm còn hàng và active
    public List<NctProduct> nctGetActiveProductsInStock() {
        return nctProductRepository.findByNctStockQuantityGreaterThanAndNctStatus(0, NctProduct.NctStatus.ACTIVE);
    }

    // Lấy sản phẩm theo khoảng giá
    public List<NctProduct> nctGetProductsByPriceRange(Double nctMinPrice, Double nctMaxPrice) {
        return nctProductRepository.findByPriceRange(nctMinPrice, nctMaxPrice);
    }

    // Lấy sản phẩm theo khoảng giá và active
    public List<NctProduct> nctGetActiveProductsByPriceRange(Double nctMinPrice, Double nctMaxPrice) {
        return nctProductRepository.findByNctPriceBetweenAndNctStatus(nctMinPrice, nctMaxPrice, NctProduct.NctStatus.ACTIVE);
    }

    // Tìm kiếm nâng cao
    public List<NctProduct> nctAdvancedSearch(Long categoryId, Double minPrice, Double maxPrice, String keyword) {
        return nctProductRepository.findByAdvancedSearch(categoryId, minPrice, maxPrice, NctProduct.NctStatus.ACTIVE, keyword);
    }

    // Đếm số sản phẩm theo danh mục
    public Long nctCountProductsByCategory(Long categoryId) {
        return nctProductRepository.countByNctCategoryId(categoryId);
    }

    // Cập nhật số lượng tồn kho
    public void nctUpdateStockQuantity(Long productId, Integer newQuantity) {
        Optional<NctProduct> productOpt = nctProductRepository.findById(productId);
        if (productOpt.isPresent()) {
            NctProduct product = productOpt.get();
            product.setNctStockQuantity(newQuantity);
            nctProductRepository.save(product);
        }
    }

    // Giảm số lượng tồn kho (khi đặt hàng)
    public boolean nctDecreaseStockQuantity(Long productId, Integer quantity) {
        Optional<NctProduct> productOpt = nctProductRepository.findById(productId);
        if (productOpt.isPresent()) {
            NctProduct product = productOpt.get();
            if (product.getNctStockQuantity() >= quantity) {
                product.setNctStockQuantity(product.getNctStockQuantity() - quantity);
                nctProductRepository.save(product);
                return true;
            }
        }
        return false;
    }

    // Tăng số lượng tồn kho (khi hủy đơn hàng)
    public void nctIncreaseStockQuantity(Long productId, Integer quantity) {
        Optional<NctProduct> productOpt = nctProductRepository.findById(productId);
        if (productOpt.isPresent()) {
            NctProduct product = productOpt.get();
            product.setNctStockQuantity(product.getNctStockQuantity() + quantity);
            nctProductRepository.save(product);
        }
    }

    // Kiểm tra sản phẩm có đủ hàng không
    public boolean nctIsProductInStock(Long productId, Integer quantity) {
        Optional<NctProduct> productOpt = nctProductRepository.findById(productId);
        return productOpt.isPresent() && productOpt.get().getNctStockQuantity() >= quantity;
    }
}