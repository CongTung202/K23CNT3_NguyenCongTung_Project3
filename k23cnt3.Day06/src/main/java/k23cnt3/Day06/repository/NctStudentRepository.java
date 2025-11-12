package k23cnt3.Day06.repository;

import k23cnt3.Day06.entity.NctStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NctStudentRepository extends JpaRepository<NctStudent, Long> {
    // Đơn giản, không cần method custom lúc đầu
}