package k23cnt3.nguyencongtung.day08.controller;

import k23cnt3.nguyencongtung.day08.entity.nctAuthor;
import k23cnt3.nguyencongtung.day08.entity.nctBook;
import k23cnt3.nguyencongtung.day08.service.nctAuthorService;
import k23cnt3.nguyencongtung.day08.service.nctBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class nctBookController {
    
    @Autowired
    private nctBookService bookService;  // ĐỔI TÊN: nctBookService -> bookService
    
    @Autowired
    private nctAuthorService authorService;  // ĐỔI TÊN: nctAuthorService -> authorService

    private static final String UPLOAD_DIR = "src/main/resources/static/";
    private static final String UPLOAD_PathFile = "images/products/";

    // Hiển thị toàn bộ sách
    @GetMapping
    public String listBooks(Model model) {
        try {
            List<nctBook> books = bookService.getAllBooks();
            model.addAttribute("books", books);
            return "books/book-list";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    // Thêm mới sách - GET
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        try {
            model.addAttribute("book", new nctBook());
            model.addAttribute("authors", authorService.getAllAuthors());
            return "books/book-form";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    // Thêm mới sách - POST
    @PostMapping("/new")
    public String saveBook(@ModelAttribute nctBook book,
                         @RequestParam(required = false) List<Long> authorIds,
                         @RequestParam(value = "imageBook", required = false) MultipartFile imageFile) {
        try {
            if (authorIds == null) {
                authorIds = new ArrayList<>();
            }
            
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    // Tạo thư mục nếu chưa tồn tại
                    Path uploadPath = Paths.get(UPLOAD_DIR + UPLOAD_PathFile);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }
                    
                    // Lấy phần mở rộng của file ảnh
                    String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
                    String fileExtension = "";
                    if (originalFilename.contains(".")) {
                        fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    }
                    
                    // Tạo tên file mới + phần mở rộng gốc
                    String newFileName = book.getCode() + fileExtension;
                    Path filePath = uploadPath.resolve(newFileName);
                    Files.copy(imageFile.getInputStream(), filePath);
                    
                    // Lưu đường dẫn ảnh vào thuộc tính imgUrl của Book
                    book.setImgUrl("/" + UPLOAD_PathFile + newFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            List<nctAuthor> authors = authorService.findAllById(authorIds);
            book.setAuthors(authors);
            bookService.saveBook(book);
            return "redirect:/books";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/books?error=true";
        }
    }

    // Form sửa thông tin sách
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            nctBook book = bookService.getBookById(id);
            if (book == null) {
                return "redirect:/books";
            }
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.getAllAuthors());
            return "books/book-form";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/books";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return "redirect:/books";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/books?error=true";
        }
    }
}