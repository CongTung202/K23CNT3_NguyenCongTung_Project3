package k23cnt3.nguyencongtung.project3.service;

import k23cnt3.nguyencongtung.project3.entity.NctWishlist;
import k23cnt3.nguyencongtung.project3.entity.NctUser;
import k23cnt3.nguyencongtung.project3.entity.NctProduct;
import k23cnt3.nguyencongtung.project3.repository.NctWishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NctWishlistService {
    private final NctWishlistRepository nctWishlistRepository;
    private final NctProductService nctProductService;

    @Autowired
    public NctWishlistService(NctWishlistRepository nctWishlistRepository, NctProductService nctProductService) {
        this.nctWishlistRepository = nctWishlistRepository;
        this.nctProductService = nctProductService;
    }
    public List<NctWishlist> nctGetWishlist(NctUser nctUser) {
        return nctWishlistRepository.findByNctUser(nctUser);
    }

    public boolean nctAddToWishlist(NctUser nctUser, Long nctProductId) {
        Optional<NctProduct> nctProductOpt = nctProductService.nctGetProductById(nctProductId);
        if (nctProductOpt.isEmpty()) {
            return false;
        }

        NctProduct nctProduct = nctProductOpt.get();
        if (nctWishlistRepository.existsByNctUserAndNctProduct(nctUser, nctProduct)) {
            return false; // Đã có trong wishlist
        }

        NctWishlist nctWishlist = new NctWishlist();
        nctWishlist.setNctUser(nctUser);
        nctWishlist.setNctProduct(nctProduct);
        nctWishlistRepository.save(nctWishlist);
        return true;
    }

    public boolean nctRemoveFromWishlist(NctUser nctUser, Long nctProductId) {
        nctWishlistRepository.deleteByNctUserAndNctProduct(nctUser.getNctUserId(), nctProductId);
        return true;
    }

    public boolean nctIsInWishlist(NctUser nctUser, Long nctProductId) {
        Optional<NctProduct> nctProductOpt = nctProductService.nctGetProductById(nctProductId);
        if (nctProductOpt.isEmpty()) {
            return false;
        }
        return nctWishlistRepository.existsByNctUserAndNctProduct(nctUser, nctProductOpt.get());
    }
}