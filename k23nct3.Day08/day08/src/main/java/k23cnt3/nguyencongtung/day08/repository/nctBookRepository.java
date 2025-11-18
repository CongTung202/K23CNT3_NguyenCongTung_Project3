package k23cnt3.nguyencongtung.day08.repository;

import k23cnt3.nguyencongtung.day08.entity.nctBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface nctBookRepository extends JpaRepository<nctBook, Long> {
    nctBook findByCode(String code);  // ĐỔI TÊN: findByNctCode -> findByCode
}