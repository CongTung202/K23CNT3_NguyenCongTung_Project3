package k23cnt3.nguyencongtung.project3.repository;

import k23cnt3.nguyencongtung.project3.entity.NctUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NctUserRepository extends JpaRepository<NctUser, Long> {
    Optional<NctUser> findByNctUsername(String nctUsername);

    Optional<NctUser> findByNctEmail(String nctEmail);

    boolean existsByNctUsername(String nctUsername);

    boolean existsByNctEmail(String nctEmail);
}