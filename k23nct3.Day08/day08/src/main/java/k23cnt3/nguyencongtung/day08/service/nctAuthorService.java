package k23cnt3.nguyencongtung.day08.service;

import k23cnt3.nguyencongtung.day08.entity.nctAuthor;
import k23cnt3.nguyencongtung.day08.repository.nctAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class nctAuthorService {
    
    @Autowired
    private nctAuthorRepository nctAuthorRepository;

    public List<nctAuthor> getAllAuthors() {
        return nctAuthorRepository.findAll();
    }

    public nctAuthor saveAuthor(nctAuthor author) {
        return nctAuthorRepository.save(author);
    }

    public nctAuthor getAuthorById(Long id) {
        return nctAuthorRepository.findById(id).orElse(null);
    }

    public void deleteAuthor(Long id) {
        nctAuthorRepository.deleteById(id);
    }

    public List<nctAuthor> findAllById(List<Long> ids) {
        return nctAuthorRepository.findAllById(ids);
    }
    
    public nctAuthor getAuthorByCode(String code) {
        return nctAuthorRepository.findByCode(code);
    }
}