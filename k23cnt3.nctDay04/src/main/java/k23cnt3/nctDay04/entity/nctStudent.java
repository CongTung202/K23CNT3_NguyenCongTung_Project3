package k23cnt3.nctDay04.entity;

public class nctStudent {
    Long nctId;
    String nctName;
    int nctAge;
    String nctGender;
    String nctAddress;
    String nctPhone;
    String nctEmail;

    public nctStudent() {
    }

    public nctStudent(Long nctId, String nctPhone, String nctAddress, String nctGender, String nctName, int nctAge, String nctEmail) {
        this.nctId = nctId;
        this.nctName = nctName;
        this.nctAge = nctAge;
        this.nctGender = nctGender;
        this.nctAddress = nctAddress;
        this.nctPhone = nctPhone;
        this.nctEmail = nctEmail;
    }

    public Long getNctId() {
        return nctId;
    }

    public String getNctName() {
        return nctName;
    }

    public int getNctAge() {
        return nctAge;
    }

    public String getNctGender() {
        return nctGender;
    }

    public String getNctAddress() {
        return nctAddress;
    }

    public String getNctPhone() {
        return nctPhone;
    }

    public String getNctEmail() {
        return nctEmail;
    }

    public void setNctId(Long nctId) {
        this.nctId = nctId;
    }

    public void setNctName(String nctName) {
        this.nctName = nctName;
    }

    public void setNctAge(int nctAge) {
        this.nctAge = nctAge;
    }

    public void setNctGender(String nctGender) {
        this.nctAddress = nctGender;
    }

    public void setNctAddress(String nctAddress) {
        this.nctAddress = nctAddress;
    }

    public void setNctPhone(String nctPhone) {
        this.nctPhone = nctPhone;
    }

    public void setNctEmail(String nctEmail) {
        this.nctEmail = nctEmail;
    }
}
