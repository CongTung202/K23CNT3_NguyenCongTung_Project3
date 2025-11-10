package k23cnt3.nctDay04.controller;

import k23cnt3.nctDay04.entity.nctStudent;
import k23cnt3.nctDay04.service.nctServiceStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class nctStudentController {

    @Autowired
    private nctServiceStudent nctStudentService;

    @GetMapping("/nct-student-list")
    public List<nctStudent> nctGetAllStudents() {
        return nctStudentService.getNctStudents(); // Sửa thành getNctStudents
    }

    @GetMapping("/nct-student/{nctId}")
    public nctStudent nctGetStudentById(@PathVariable String nctId) { // Sửa kiểu trả về thành nctStudent
        Long nctParam = Long.parseLong(nctId);
        return nctStudentService.getNctStudent(nctParam); // Sửa thành getNctStudent
    }

    @PostMapping("/nct-student-add")
    public nctStudent nctAddStudent(@RequestBody nctStudent nctStudent) { // Sửa kiểu tham số
        return nctStudentService.addNctStudent(nctStudent); // Sửa thành addNctStudent
    }

    @PutMapping("/nct-student/{nctId}")
    public nctStudent nctUpdateStudent(@PathVariable String nctId, @RequestBody nctStudent nctStudent) {
        Long nctParam = Long.parseLong(nctId);
        return nctStudentService.updateNctStudent(nctParam, nctStudent); // Sửa thành updateNctStudent
    }

    @DeleteMapping("/nct-student/{nctId}")
    public boolean nctDeleteStudent(@PathVariable String nctId) {
        Long nctParam = Long.parseLong(nctId);
        return nctStudentService.deleteNctStudent(nctParam); // Thêm phương thức delete
    }
}