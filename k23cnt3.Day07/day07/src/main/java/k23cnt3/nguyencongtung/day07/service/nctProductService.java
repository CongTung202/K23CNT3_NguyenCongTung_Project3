package k23cnt3.nguyencongtung.day07.service;

import k23cnt3.nguyencongtung.day07.entity.nctProduct;
import k23cnt3.nguyencongtung.day07.repository.nctProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class nctProductService {
    
    @Autowired
    private nctProductRepository nctProductRepository;
    
    public List<nctProduct> nctGetAllProducts() {
        return nctProductRepository.findAll();
    }
    
    public Optional<nctProduct> nctFindById(Long nctId) {
        return nctProductRepository.findById(nctId);
    }
    
    public nctProduct nctSaveProduct(nctProduct nctProduct) {
        return nctProductRepository.save(nctProduct);
    }
    
    public void nctDeleteProduct(Long nctId) {
        nctProductRepository.deleteById(nctId);
    }
}