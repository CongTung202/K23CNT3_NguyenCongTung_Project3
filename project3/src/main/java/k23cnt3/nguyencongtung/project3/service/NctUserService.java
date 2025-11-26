package k23cnt3.nguyencongtung.project3.service;

    import k23cnt3.nguyencongtung.project3.entity.NctUser;
    import k23cnt3.nguyencongtung.project3.repository.NctUserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.List;
    import java.util.Optional;

    @Service
    @Transactional
    public class NctUserService {
        private final NctUserRepository nctUserRepository;

        @Autowired
        public NctUserService(NctUserRepository nctUserRepository) {
            this.nctUserRepository = nctUserRepository;
        }

        public NctUser nctRegisterUser(NctUser nctUser) {
            // Password is now saved as plain text
            return nctUserRepository.save(nctUser);
        }

        public Optional<NctUser> nctFindByUsername(String nctUsername) {
            return nctUserRepository.findByNctUsername(nctUsername);
        }

        public void nctChangePassword(NctUser user, String currentPassword, String newPassword) {
            // Compare plain text passwords
            if (!currentPassword.equals(user.getNctPassword())) {
                throw new IllegalArgumentException("Mật khẩu hiện tại không đúng.");
            }
            // Save the new password as plain text
            user.setNctPassword(newPassword);
            nctUserRepository.save(user);
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