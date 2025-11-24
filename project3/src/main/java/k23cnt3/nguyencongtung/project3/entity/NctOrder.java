package k23cnt3.nguyencongtung.project3.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nct_orders")
@Data
public class NctOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nct_order_id")
    private Long nctOrderId;

    @ManyToOne
    @JoinColumn(name = "nct_user_id", nullable = false)
    private NctUser nctUser;

    @Column(name = "nct_total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal nctTotalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "nct_status")
    private NctOrderStatus nctStatus = NctOrderStatus.PENDING;

    @Column(name = "nct_shipping_address", nullable = false, columnDefinition = "TEXT")
    private String nctShippingAddress;

    @Column(name = "nct_phone", nullable = false)
    private String nctPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "nct_payment_method")
    private NctPaymentMethod nctPaymentMethod = NctPaymentMethod.COD;

    @Column(name = "nct_created_at")
    private LocalDateTime nctCreatedAt = LocalDateTime.now();

    @Column(name = "nct_updated_at")
    private LocalDateTime nctUpdatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "nctOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NctOrderItem> nctOrderItems = new ArrayList<>();

    public enum NctOrderStatus {
        PENDING, CONFIRMED, SHIPPING, DELIVERED, CANCELLED
    }

    public enum NctPaymentMethod {
        COD, BANKING
    }

    public NctOrder(Long nctOrderId, NctUser nctUser, NctOrderStatus nctStatus, BigDecimal nctTotalAmount, String nctShippingAddress, NctPaymentMethod nctPaymentMethod, String nctPhone, LocalDateTime nctCreatedAt, LocalDateTime nctUpdatedAt, List<NctOrderItem> nctOrderItems) {
        this.nctOrderId = nctOrderId;
        this.nctUser = nctUser;
        this.nctStatus = nctStatus;
        this.nctTotalAmount = nctTotalAmount;
        this.nctShippingAddress = nctShippingAddress;
        this.nctPaymentMethod = nctPaymentMethod;
        this.nctPhone = nctPhone;
        this.nctCreatedAt = nctCreatedAt;
        this.nctUpdatedAt = nctUpdatedAt;
        this.nctOrderItems = nctOrderItems;
    }

    public Long getNctOrderId() {
        return nctOrderId;
    }

    public void setNctOrderId(Long nctOrderId) {
        this.nctOrderId = nctOrderId;
    }

    public NctUser getNctUser() {
        return nctUser;
    }

    public void setNctUser(NctUser nctUser) {
        this.nctUser = nctUser;
    }

    public BigDecimal getNctTotalAmount() {
        return nctTotalAmount;
    }

    public void setNctTotalAmount(BigDecimal nctTotalAmount) {
        this.nctTotalAmount = nctTotalAmount;
    }

    public NctOrderStatus getNctStatus() {
        return nctStatus;
    }

    public void setNctStatus(NctOrderStatus nctStatus) {
        this.nctStatus = nctStatus;
    }

    public String getNctShippingAddress() {
        return nctShippingAddress;
    }

    public void setNctShippingAddress(String nctShippingAddress) {
        this.nctShippingAddress = nctShippingAddress;
    }

    public String getNctPhone() {
        return nctPhone;
    }

    public void setNctPhone(String nctPhone) {
        this.nctPhone = nctPhone;
    }

    public NctPaymentMethod getNctPaymentMethod() {
        return nctPaymentMethod;
    }

    public void setNctPaymentMethod(NctPaymentMethod nctPaymentMethod) {
        this.nctPaymentMethod = nctPaymentMethod;
    }

    public LocalDateTime getNctCreatedAt() {
        return nctCreatedAt;
    }

    public void setNctCreatedAt(LocalDateTime nctCreatedAt) {
        this.nctCreatedAt = nctCreatedAt;
    }

    public LocalDateTime getNctUpdatedAt() {
        return nctUpdatedAt;
    }

    public void setNctUpdatedAt(LocalDateTime nctUpdatedAt) {
        this.nctUpdatedAt = nctUpdatedAt;
    }

    public List<NctOrderItem> getNctOrderItems() {
        return nctOrderItems;
    }

    public void setNctOrderItems(List<NctOrderItem> nctOrderItems) {
        this.nctOrderItems = nctOrderItems;
    }

    public NctOrder() {
    }
    // Thêm method tiện ích
    @Transient
    public int getNctItemCount() {
        return nctOrderItems != null ? nctOrderItems.stream()
                .mapToInt(NctOrderItem::getNctQuantity)
                .sum() : 0;
    }

    @Transient
    public String getNctFormattedTotal() {
        return "₫" + String.format("%,.0f", nctTotalAmount.doubleValue());
    }
}