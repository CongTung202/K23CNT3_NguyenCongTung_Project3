package k23cnt3.nguyencongtung.day08.service;

import k23cnt3.nguyencongtung.day08.entity.nctBook;
import k23cnt3.nguyencongtung.day08.repository.nctBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class nctBookService {
    
    @Autowired
    private nctBookRepository nctBookRepository;

    public List<nctBook> getAllBooks() {
        try {
            return nctBookRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public nctBook saveBook(nctBook book) {
        try {
            return nctBookRepository.save(book);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public nctBook getBookById(Long id) {
        try {
            return nctBookRepository.findById(id).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteBook(Long id) {
        try {
            nctBookRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public nctBook getBookByCode(String code) {
        return nctBookRepository.findByCode(code);
    }
}