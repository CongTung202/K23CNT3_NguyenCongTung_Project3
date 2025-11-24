package k23cnt3.nguyencongtung.project3.service;

import k23cnt3.nguyencongtung.project3.entity.NctUser;
import k23cnt3.nguyencongtung.project3.repository.NctUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NctUserService {
    private final NctUserRepository nctUserRepository;
    private final PasswordEncoder nctPasswordEncoder;

    @Autowired
    public NctUserService(NctUserRepository nctUserRepository, PasswordEncoder nctPasswordEncoder) {
        this.nctUserRepository = nctUserRepository;
        this.nctPasswordEncoder = nctPasswordEncoder;
    }

    public NctUser nctRegisterUser(NctUser nctUser) {
        nctUser.setNctPassword(nctPasswordEncoder.encode(nctUser.getNctPassword()));
        return nctUserRepository.save(nctUser);
    }

    public Optional<NctUser> nctFindByUsername(String nctUsername) {
        return nctUserRepository.findByNctUsername(nctUsername);
    }

    public Optional<NctUser> nctFindByEmail(String nctEmail) {
        return nctUserRepository.findByNctEmail(nctEmail);
    }

    public boolean nctUsernameExists(String nctUsername) {
        return nctUserRepository.existsByNctUsername(nctUsername);
    }

    public boolean nctEmailExists(String nctEmail) {
        return nctUserRepository.existsByNctEmail(nctEmail);
    }

    // THÊM CÁC METHOD MỚI
    public List<NctUser> nctGetAllUsers() {
        return nctUserRepository.findAll();
    }

    public Optional<NctUser> nctGetUserById(Long id) {
        return nctUserRepository.findById(id);
    }

    public NctUser nctSaveUser(NctUser nctUser) {
        return nctUserRepository.save(nctUser);
    }

    public void nctDeleteUser(Long id) {
        nctUserRepository.deleteById(id);
    }

    public long nctCountTotalUsers() {
        return nctUserRepository.count();
    }

    public long nctCountUsersByRole(NctUser.NctRole role) {
        return nctUserRepository.countByNctRole(role);
    }

    public List<NctUser> nctSearchUsers(String keyword) {
        return nctUserRepository.findByNctUsernameContainingOrNctEmailContainingOrNctFullNameContaining(keyword);
    }
}