package k23cnt3.nguyencongtung.project3.repository;

import k23cnt3.nguyencongtung.project3.entity.NctCategory;
import k23cnt3.nguyencongtung.project3.entity.NctProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NctProductRepository extends JpaRepository<NctProduct, Long> {
    List<NctProduct> findByNctCategory(NctCategory nctCategory);
    List<NctProduct> findByNctProductNameContainingIgnoreCase(String nctProductName);
    List<NctProduct> findByNctStatus(NctProduct.NctStatus nctStatus);

    @Query("SELECT p FROM NctProduct p WHERE p.nctPrice BETWEEN :minPrice AND :maxPrice")
    List<NctProduct> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    @Query("SELECT p FROM NctProduct p ORDER BY p.nctCreatedAt DESC LIMIT 8")
    List<NctProduct> findTop8ByOrderByNctCreatedAtDesc();

    List<NctProduct> findByNctStockQuantityGreaterThan(Integer nctStockQuantity);
}