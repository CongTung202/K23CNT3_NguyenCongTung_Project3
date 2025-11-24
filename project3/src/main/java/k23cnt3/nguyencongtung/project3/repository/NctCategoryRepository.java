package k23cnt3.nguyencongtung.project3.repository;

import k23cnt3.nguyencongtung.project3.entity.NctCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NctCategoryRepository extends JpaRepository<NctCategory, Long> {
    List<NctCategory> findByNctCategoryNameContainingIgnoreCase(String nctCategoryName);
    boolean existsByNctCategoryName(String nctCategoryName);
}