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

    // SỬA: Gộp các method trùng lặp
    List<NctOrder> findByNctUserOrderByNctCreatedAtDesc(NctUser nctUser);

    List<NctOrder> findByNctStatusOrderByNctCreatedAtDesc(NctOrder.NctOrderStatus status);

    List<NctOrder> findByNctUser_NctUserIdOrderByNctCreatedAtDesc(Long userId);

    // SỬA: Sử dụng method query của Spring Data JPA thay vì @Query
    Long countByNctStatus(NctOrder.NctOrderStatus nctStatus);

    // SỬA: Thêm phương thức mặc định
    List<NctOrder> findAllByOrderByNctCreatedAtDesc();

    @Query("SELECT MONTH(o.nctCreatedAt) as month, COUNT(o) as orderCount, SUM(o.nctTotalAmount) as revenue " +
            "FROM NctOrder o WHERE YEAR(o.nctCreatedAt) = YEAR(CURRENT_DATE) " +
            "GROUP BY MONTH(o.nctCreatedAt) ORDER BY month")
    List<Object[]> getMonthlyOrderStats();

    @Query("SELECT SUM(o.nctTotalAmount) FROM NctOrder o WHERE o.nctStatus = k23cnt3.nguyencongtung.project3.entity.NctOrder.NctOrderStatus.DELIVERED")
    Double getTotalRevenue();

    List<NctOrder> findByNctUser(NctUser nctUser);
    // - List<NctOrder> findByNctStatus(NctOrder.NctOrderStatus nctStatus);
    // - @Query("SELECT o FROM NctOrder o ORDER BY o.nctCreatedAt DESC")
    // - @Query("SELECT COUNT(o) FROM NctOrder o WHERE o.nctStatus = :status")
}