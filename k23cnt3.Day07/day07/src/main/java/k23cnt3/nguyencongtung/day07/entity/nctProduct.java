package k23cnt3.nguyencongtung.day07.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nct_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class nctProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nct_id")
    private Long nctId;
    
    @Column(name = "nct_name")
    private String nctName;
    
    @Column(name = "nct_image_url")
    private String nctImageUrl;
    
    @Column(name = "nct_quantity")
    private Integer nctQuantity;
    
    @Column(name = "nct_price")
    private Double nctPrice;
    
    @Column(name = "nct_content")
    private String nctContent;
    
    @Column(name = "nct_status")
    private Boolean nctStatus;
    
    @ManyToOne
    @JoinColumn(name = "nct_category_id", nullable = false)
    private nctCategory nctCategory;
}