package k23cnt3.nguyencongtung.day07.repository;

import k23cnt3.nguyencongtung.day07.entity.nctProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface nctProductRepository extends JpaRepository<nctProduct, Long> {
}