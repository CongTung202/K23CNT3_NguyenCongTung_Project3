package k23cnt3.nguyencongtung.project3.service;

import k23cnt3.nguyencongtung.project3.entity.*;
import k23cnt3.nguyencongtung.project3.repository.NctOrderRepository;
import k23cnt3.nguyencongtung.project3.repository.NctOrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NctOrderService {
    private final NctOrderRepository nctOrderRepository;
    private final NctOrderItemRepository nctOrderItemRepository;
    private final NctCartService nctCartService;
    private final NctProductService nctProductService;

    @Autowired
    public NctOrderService(NctOrderRepository nctOrderRepository,
                           NctOrderItemRepository nctOrderItemRepository,
                           NctCartService nctCartService,
                           NctProductService nctProductService) {
        this.nctOrderRepository = nctOrderRepository;
        this.nctOrderItemRepository = nctOrderItemRepository;
        this.nctCartService = nctCartService;
        this.nctProductService = nctProductService;
    }

    public List<NctOrder> nctGetAllOrders() {
        return nctOrderRepository.findAllByOrderByNctCreatedAtDesc();
    }

    public List<NctOrder> nctGetUserOrders(NctUser nctUser) {
        return nctOrderRepository.findByNctUser(nctUser);
    }

    public Optional<NctOrder> nctGetOrderById(Long nctOrderId) {
        return nctOrderRepository.findById(nctOrderId);
    }

    public NctOrder nctCreateOrder(NctUser nctUser, String nctShippingAddress, String nctPhone,
                                   NctOrder.NctPaymentMethod nctPaymentMethod) {
        // Lấy giỏ hàng của user
        List<NctCartItem> nctCartItems = nctCartService.nctGetCartItems(nctUser);
        if (nctCartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống");
        }

        // Tính tổng tiền
        BigDecimal nctTotalAmount = BigDecimal.valueOf(nctCartService.nctCalculateCartTotal(nctUser));

        // Tạo đơn hàng
        NctOrder nctOrder = new NctOrder();
        nctOrder.setNctUser(nctUser);
        nctOrder.setNctTotalAmount(nctTotalAmount);
        nctOrder.setNctShippingAddress(nctShippingAddress);
        nctOrder.setNctPhone(nctPhone);
        nctOrder.setNctPaymentMethod(nctPaymentMethod);
        nctOrder.setNctStatus(NctOrder.NctOrderStatus.PENDING);

        NctOrder nctSavedOrder = nctOrderRepository.save(nctOrder);

        // Tạo order items và cập nhật số lượng tồn kho
        for (NctCartItem nctCartItem : nctCartItems) {
            NctProduct nctProduct = nctCartItem.getNctProduct();

            // Kiểm tra số lượng tồn kho
            if (nctProduct.getNctStockQuantity() < nctCartItem.getNctQuantity()) {
                throw new RuntimeException("Sản phẩm " + nctProduct.getNctProductName() + " không đủ số lượng tồn kho");
            }

            // Cập nhật số lượng tồn kho
            nctProduct.setNctStockQuantity(nctProduct.getNctStockQuantity() - nctCartItem.getNctQuantity());
            nctProductService.nctSaveProduct(nctProduct);

            // Tạo order item
            NctOrderItem nctOrderItem = new NctOrderItem();
            nctOrderItem.setNctOrder(nctSavedOrder);
            nctOrderItem.setNctProduct(nctProduct);
            nctOrderItem.setNctQuantity(nctCartItem.getNctQuantity());
            nctOrderItem.setNctPrice(nctProduct.getNctPrice());
            nctOrderItemRepository.save(nctOrderItem);
        }

        // Xóa giỏ hàng
        nctCartService.nctClearCart(nctUser);

        return nctSavedOrder;
    }

    public NctOrder nctUpdateOrderStatus(Long nctOrderId, NctOrder.NctOrderStatus nctStatus) {
        Optional<NctOrder> nctOrderOpt = nctOrderRepository.findById(nctOrderId);
        if (nctOrderOpt.isPresent()) {
            NctOrder nctOrder = nctOrderOpt.get();
            nctOrder.setNctStatus(nctStatus);
            nctOrder.setNctUpdatedAt(LocalDateTime.now());
            return nctOrderRepository.save(nctOrder);
        }
        throw new RuntimeException("Đơn hàng không tồn tại");
    }

    public void nctCancelOrder(Long nctOrderId) {
        Optional<NctOrder> nctOrderOpt = nctOrderRepository.findById(nctOrderId);
        if (nctOrderOpt.isPresent()) {
            NctOrder nctOrder = nctOrderOpt.get();

            // Chỉ cho phép hủy đơn hàng ở trạng thái PENDING
            if (nctOrder.getNctStatus() != NctOrder.NctOrderStatus.PENDING) {
                throw new RuntimeException("Không thể hủy đơn hàng ở trạng thái hiện tại");
            }

            // Hoàn trả số lượng tồn kho
            for (NctOrderItem nctOrderItem : nctOrder.getNctOrderItems()) {
                NctProduct nctProduct = nctOrderItem.getNctProduct();
                nctProduct.setNctStockQuantity(nctProduct.getNctStockQuantity() + nctOrderItem.getNctQuantity());
                nctProductService.nctSaveProduct(nctProduct);
            }

            nctOrder.setNctStatus(NctOrder.NctOrderStatus.CANCELLED);
            nctOrder.setNctUpdatedAt(LocalDateTime.now());
            nctOrderRepository.save(nctOrder);
        }
    }

    public Long nctGetOrderCountByStatus(NctOrder.NctOrderStatus nctStatus) {
        return nctOrderRepository.countByNctStatus(nctStatus);
    }

    public Double nctGetTotalRevenue() {
        Double revenue = nctOrderRepository.getTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }

    public List<Object[]> nctGetBestSellingProducts() {
        return nctOrderItemRepository.findBestSellingProducts();
    }
}