package k23cnt3.nguyencongtung.project3.service;

import k23cnt3.nguyencongtung.project3.entity.NctUser;
import k23cnt3.nguyencongtung.project3.repository.NctUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
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

    public boolean nctUsernameExists(String nctUsername) {
        return nctUserRepository.existsByNctUsername(nctUsername);
    }

    public boolean nctEmailExists(String nctEmail) {
        return nctUserRepository.existsByNctEmail(nctEmail);
    }
}