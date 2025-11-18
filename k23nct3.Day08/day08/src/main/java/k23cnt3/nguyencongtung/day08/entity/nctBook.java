package k23cnt3.nguyencongtung.day08.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nct_book")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class nctBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ĐỔI TÊN: nctId -> id
    
    @Column(name = "code", unique = true, nullable = false)
    private String code;  // ĐỔI TÊN: nctCode -> code
    
    @Column(name = "name", nullable = false)
    private String name;  // ĐỔI TÊN: nctName -> name
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;  // ĐỔI TÊN: nctDescription -> description
    
    @Column(name = "img_url")
    private String imgUrl;  // ĐỔI TÊN: nctImgUrl -> imgUrl
    
    @Column(name = "quantity")
    private Integer quantity;  // ĐỔI TÊN: nctQuantity -> quantity
    
    @Column(name = "price")
    private Double price;  // ĐỔI TÊN: nctPrice -> price
    
    @Column(name = "is_active")
    private Boolean isActive;  // ĐỔI TÊN: nctIsActive -> isActive
    
    // Tạo mối quan hệ với bảng author
    @ManyToMany
    @JoinTable(
        name = "nct_book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<nctAuthor> authors = new ArrayList<>();  // ĐỔI TÊN: nctAuthors -> authors
}