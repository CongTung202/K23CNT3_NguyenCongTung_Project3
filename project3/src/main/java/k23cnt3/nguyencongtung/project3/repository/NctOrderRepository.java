package k23cnt3.nguyencongtung.project3.repository;

import k23cnt3.nguyencongtung.project3.entity.NctOrder;
import k23cnt3.nguyencongtung.project3.entity.NctUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NctOrderRepository extends JpaRepository<NctOrder, Long> {
    List<NctOrder> findByNctUser(NctUser nctUser);
    List<NctOrder> findByNctStatus(NctOrder.NctOrderStatus nctStatus);


    @Query("SELECT o FROM NctOrder o ORDER BY o.nctCreatedAt DESC")
    List<NctOrder> findAllByOrderByNctCreatedAtDesc();

    @Query("SELECT COUNT(o) FROM NctOrder o WHERE o.nctStatus = :status")
    Long countByNctStatus(@Param("status") NctOrder.NctOrderStatus nctStatus);

    @Query("SELECT SUM(o.nctTotalAmount) FROM NctOrder o WHERE o.nctStatus = 'DELIVERED'")
    Double getTotalRevenue();
}