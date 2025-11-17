package k23cnt3.nguyencongtung.day07.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity

@Table(name = "nct_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class nctCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nct_id")
    private Long nctId;
    
    @Column(name = "nct_category_name")
    private String nctCategoryName;
    
    @Column(name = "nct_category_status")
    private Boolean nctCategoryStatus;
}