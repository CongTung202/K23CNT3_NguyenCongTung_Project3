package k23cnt3.Day06.dto;

public class NctStudentDTO {
    private Long nctId;
    private String nctName;
    private String nctEmail;
    private Integer nctAge;

    // Constructors
    public NctStudentDTO() {}

    public NctStudentDTO(Long nctId, String nctName, String nctEmail, Integer nctAge) {
        this.nctId = nctId;
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