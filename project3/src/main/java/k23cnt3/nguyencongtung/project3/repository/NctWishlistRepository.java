package k23cnt3.nguyencongtung.project3.repository;

import k23cnt3.nguyencongtung.project3.entity.NctUser;
import k23cnt3.nguyencongtung.project3.entity.NctProduct;
import k23cnt3.nguyencongtung.project3.entity.NctWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NctWishlistRepository extends JpaRepository<NctWishlist, Long> {
    List<NctWishlist> findByNctUser(NctUser nctUser);
    Optional<NctWishlist> findByNctUserAndNctProduct(NctUser nctUser, NctProduct nctProduct);
    boolean existsByNctUserAndNctProduct(NctUser nctUser, NctProduct nctProduct);

    @Modifying
    @Query("DELETE FROM NctWishlist w WHERE w.nctUser.nctUserId = :userId AND w.nctProduct.nctProductId = :productId")
    void deleteByNctUserAndNctProduct(@Param("userId") Long nctUserId, @Param("productId") Long nctProductId);
}