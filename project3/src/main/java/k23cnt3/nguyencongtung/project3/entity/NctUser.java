package k23cnt3.nguyencongtung.project3.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "nct_users")
@Data
public class NctUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nct_user_id")
    private Long nctUserId;

    @Column(name = "nct_username", unique = true, nullable = false)
    private String nctUsername;

    @Column(name = "nct_email", unique = true, nullable = false)
    private String nctEmail;

    @Column(name = "nct_password", nullable = false)
    private String nctPassword;

    @Column(name = "nct_full_name")
    private String nctFullName;

    @Column(name = "nct_phone")
    private String nctPhone;

    @Column(name = "nct_address")
    private String nctAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "nct_role")
    private NctRole nctRole = NctRole.USER;

    @Column(name = "nct_created_at")
    private LocalDateTime nctCreatedAt = LocalDateTime.now();

    @Column(name = "nct_updated_at")
    private LocalDateTime nctUpdatedAt = LocalDateTime.now();

    public enum NctRole {
        ADMIN, USER
    }

    public NctUser() {
    }

    public NctUser(Long nctUserId, String nctUsername, String nctEmail, String nctFullName, String nctPassword, String nctPhone, String nctAddress, NctRole nctRole, LocalDateTime nctCreatedAt, LocalDateTime nctUpdatedAt) {
        this.nctUserId = nctUserId;
        this.nctUsername = nctUsername;
        this.nctEmail = nctEmail;
        this.nctFullName = nctFullName;
        this.nctPassword = nctPassword;
        this.nctPhone = nctPhone;
        this.nctAddress = nctAddress;
        this.nctRole = nctRole;
        this.nctCreatedAt = nctCreatedAt;
        this.nctUpdatedAt = nctUpdatedAt;
    }

    public Long getNctUserId() {
        return nctUserId;
    }

    public void setNctUserId(Long nctUserId) {
        this.nctUserId = nctUserId;
    }

    public String getNctUsername() {
        return nctUsername;
    }

    public void setNctUsername(String nctUsername) {
        this.nctUsername = nctUsername;
    }

    public String getNctEmail() {
        return nctEmail;
    }

    public void setNctEmail(String nctEmail) {
        this.nctEmail = nctEmail;
    }

    public String getNctPassword() {
        return nctPassword;
    }

    public void setNctPassword(String nctPassword) {
        this.nctPassword = nctPassword;
    }

    public String getNctFullName() {
        return nctFullName;
    }

    public void setNctFullName(String nctFullName) {
        this.nctFullName = nctFullName;
    }

    public String getNctPhone() {
        return nctPhone;
    }

    public void setNctPhone(String nctPhone) {
        this.nctPhone = nctPhone;
    }

    public String getNctAddress() {
        return nctAddress;
    }

    public void setNctAddress(String nctAddress) {
        this.nctAddress = nctAddress;
    }

    public NctRole getNctRole() {
        return nctRole;
    }

    public void setNctRole(NctRole nctRole) {
        this.nctRole = nctRole;
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
}