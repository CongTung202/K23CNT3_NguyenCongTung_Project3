package k23cnt3.nguyencongtung.project3.service;

import k23cnt3.nguyencongtung.project3.entity.NctOrder;
import k23cnt3.nguyencongtung.project3.entity.NctUser;

import java.util.List;
import java.util.Optional;

public interface NctOrderServiceInterface {

    // Lấy tất cả đơn hàng
    List<NctOrder> nctGetAllOrders();

    // Lấy đơn hàng theo user
    List<NctOrder> nctGetUserOrders(NctUser nctUser);

    // Lấy đơn hàng theo ID
    Optional<NctOrder> nctGetOrderById(Long nctOrderId);

    // Tạo đơn hàng mới
    NctOrder nctCreateOrder(NctUser nctUser, String nctShippingAddress, String nctPhone,
                            NctOrder.NctPaymentMethod nctPaymentMethod);

    // Tạo đơn hàng từ giỏ hàng
    NctOrder nctCreateOrderFromCart(NctUser nctUser, NctOrder nctOrderDetails);

    // Tạo đơn hàng từ một sản phẩm duy nhất (Mua ngay)
    NctOrder nctCreateOrderFromSingleProduct(NctUser nctUser, Long nctProductId, Integer nctQuantity, String nctShippingAddress, String nctPhone, NctOrder.NctPaymentMethod nctPaymentMethod);

    // Cập nhật trạng thái đơn hàng
    NctOrder nctUpdateOrderStatus(Long nctOrderId, NctOrder.NctOrderStatus nctStatus);

    // Hủy đơn hàng
    void nctCancelOrder(Long nctOrderId);

    // Đếm đơn hàng theo trạng thái
    Long nctGetOrderCountByStatus(NctOrder.NctOrderStatus nctStatus);

    // Tính tổng doanh thu
    Double nctGetTotalRevenue();

    // Lấy sản phẩm bán chạy
    List<Object[]> nctGetBestSellingProducts();

    // Lưu đơn hàng (thêm mới)
    NctOrder nctSaveOrder(NctOrder order);

    // Xóa đơn hàng (thêm mới)
    void nctDeleteOrder(Long orderId);

    // Lấy đơn hàng theo trạng thái (thêm mới)
    List<NctOrder> nctGetOrdersByStatus(NctOrder.NctOrderStatus status);
}
