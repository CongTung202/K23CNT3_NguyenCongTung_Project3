package k23cnt3.nguyencongtung.day07.repository;

import k23cnt3.nguyencongtung.day07.entity.nctCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface nctCategoryRepository extends JpaRepository<nctCategory, Long> {
}