package k23cnt3.Day05.entity;

public class NctInfo {
    private String nctName;
    private String nctNickName;
    private String nctEmail;
    private String nctWebsite;

    public NctInfo() {}

    public NctInfo(String nctName, String nctNickName, String nctEmail, String nctWebsite) {
        this.nctName = nctName;
        this.nctNickName = nctNickName;
        this.nctEmail = nctEmail;
        this.nctWebsite = nctWebsite;
    }

    // Getter và Setter với tiền tố nct
    public String getNctName() { return nctName; }
    public void setNctName(String nctName) { this.nctName = nctName; }

    public String getNctNickName() { return nctNickName; }
    public void setNctNickName(String nctNickName) { this.nctNickName = nctNickName; }

    public String getNctEmail() { return nctEmail; }
    public void setNctEmail(String nctEmail) { this.nctEmail = nctEmail; }

    public String getNctWebsite() { return nctWebsite; }
    public void setNctWebsite(String nctWebsite) { this.nctWebsite = nctWebsite; }
}