package k23cnt3.Day06.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "nct_students")
public class NctStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nctId;

    @Column(name = "nct_name")
    private String nctName;

    @Column(name = "nct_email")
    private String nctEmail;

    private Integer nctAge;

    // Constructors
    public NctStudent() {}

    public NctStudent(String nctName, String nctEmail, Integer nctAge) {
        this.nctName = nctName;
        this.nctEmail = nctEmail;
        this.nctAge = nctAge;
    }

    // Getters and Setters
    public Long getNctId() { return nctId; }
    public void setNctId(Long nctId) { this.nctId = nctId; }

    public String getNctName() { return nctName; }
    public void setNctName(String nctName) { this.nctName = nctName; }

    public String getNctEmail() { return nctEmail; }
    public void setNctEmail(String nctEmail) { this.nctEmail = nctEmail; }

    public Integer getNctAge() { return nctAge; }
    public void setNctAge(Integer nctAge) { this.nctAge = nctAge; }
}