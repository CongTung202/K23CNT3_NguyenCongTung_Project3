package k23cnt3.nguyencongtung.project3.repository;

import k23cnt3.nguyencongtung.project3.entity.NctUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NctUserRepository extends JpaRepository<NctUser, Long> {
    Optional<NctUser> findByNctUsername(String nctUsername);
    Optional<NctUser> findByNctEmail(String nctEmail);
    boolean existsByNctUsername(String nctUsername);
    boolean existsByNctEmail(String nctEmail);

    // THÊM CÁC METHOD MỚI
    long countByNctRole(NctUser.NctRole nctRole);

    @Query("SELECT u FROM NctUser u WHERE u.nctUsername LIKE %:keyword% OR u.nctEmail LIKE %:keyword% OR u.nctFullName LIKE %:keyword%")
    List<NctUser> findByNctUsernameContainingOrNctEmailContainingOrNctFullNameContaining(
            @Param("keyword") String keyword);

    @Query("SELECT u FROM NctUser u ORDER BY u.nctCreatedAt DESC")
    List<NctUser> findAllByOrderByNctCreatedAtDesc();
}