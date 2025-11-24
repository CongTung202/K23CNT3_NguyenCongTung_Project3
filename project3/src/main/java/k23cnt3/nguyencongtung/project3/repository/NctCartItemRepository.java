package k23cnt3.nguyencongtung.project3.repository;

import k23cnt3.nguyencongtung.project3.entity.NctCartItem;
import k23cnt3.nguyencongtung.project3.entity.NctUser;
import k23cnt3.nguyencongtung.project3.entity.NctProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NctCartItemRepository extends JpaRepository<NctCartItem, Long> {
    List<NctCartItem> findByNctUser(NctUser nctUser);
    Optional<NctCartItem> findByNctUserAndNctProduct(NctUser nctUser, NctProduct nctProduct);

    @Query("SELECT SUM(ci.nctQuantity) FROM NctCartItem ci WHERE ci.nctUser.nctUserId = :userId")
    Integer countNctCartItemsByNctUser(@Param("userId") Long nctUserId);

    @Modifying
    @Query("DELETE FROM NctCartItem ci WHERE ci.nctUser.nctUserId = :userId")
    void deleteByNctUserId(@Param("userId") Long nctUserId);

    @Modifying
    @Query("DELETE FROM NctCartItem ci WHERE ci.nctUser.nctUserId = :userId AND ci.nctProduct.nctProductId = :productId")
    void deleteByNctUserAndNctProduct(@Param("userId") Long nctUserId, @Param("productId") Long nctProductId);
}