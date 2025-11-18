package k23cnt3.nguyencongtung.day08.repository;

import k23cnt3.nguyencongtung.day08.entity.nctAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface nctAuthorRepository extends JpaRepository<nctAuthor, Long> {
    nctAuthor findByCode(String code);  // ĐỔI TÊN: findByNctCode -> findByCode
}