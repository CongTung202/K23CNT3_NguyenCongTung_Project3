package k23cnt3.nguyencongtung.day08.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nct_author")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class nctAuthor {
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
    
    @Column(name = "email")
    private String email;  // ĐỔI TÊN: nctEmail -> email
    
    @Column(name = "phone")
    private String phone;  // ĐỔI TÊN: nctPhone -> phone
    
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;  // ĐỔI TÊN: nctAddress -> address
    
    @Column(name = "is_active")
    private boolean isActive;  // ĐỔI TÊN: nctIsActive -> isActive
    
    // Tạo mối quan hệ với bảng book
    @ManyToMany(mappedBy = "authors")  // CẬP NHẬT: mappedBy = "authors"
    private List<nctBook> books = new ArrayList<>();  // ĐỔI TÊN: nctBooks -> books
}