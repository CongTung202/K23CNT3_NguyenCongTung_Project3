package k23cnt3.nctDay04.service;

import k23cnt3.nctDay04.entity.nctStudent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service // Thêm annotation @Service
public class nctServiceStudent {
    private List<nctStudent> nctStudents = new ArrayList<>();

    public nctServiceStudent() {
        // Sửa các ID để không trùng lặp
        nctStudents.addAll(Arrays.asList(
                new nctStudent(1L, "0987654321", "157 Pho Xom", "Male", "Boka Chan", 20, "BokaChan@gmail.com"),
                new nctStudent(2L, "0987654322", "158 Pho Xom", "Female", "Boka Chan 2", 21, "BokaChan2@gmail.com"),
                new nctStudent(3L, "0987654323", "159 Pho Xom", "Male", "Boka Chan 3", 22, "BokaChan3@gmail.com")
        ));
    }

    public List<nctStudent> getNctStudents() {
        return nctStudents;
    }

    public nctStudent getNctStudent(Long nctId) {
        Optional<nctStudent> student = nctStudents.stream()
                .filter(s -> s.getNctId().equals(nctId))
                .findFirst();
        return student.orElse(null);
    }

    public nctStudent addNctStudent(nctStudent student) {
        // Tạo ID mới tự động
        Long newId = nctStudents.stream()
                .mapToLong(nctStudent::getNctId)
                .max()
                .orElse(0L) + 1;
        student.setNctId(newId);
        nctStudents.add(student);
        return student;
    }

    public nctStudent updateNctStudent(Long nctId, nctStudent student) {
        nctStudent existingStudent = getNctStudent(nctId);
        if (existingStudent != null) {
            existingStudent.setNctName(student.getNctName());
            existingStudent.setNctAddress(student.getNctAddress());
            existingStudent.setNctEmail(student.getNctEmail());
            existingStudent.setNctGender(student.getNctGender());
            existingStudent.setNctPhone(student.getNctPhone());
            existingStudent.setNctAge(student.getNctAge());
            return existingStudent;
        }
        return null;
    }

    public boolean deleteNctStudent(Long nctId) {
        nctStudent student = getNctStudent(nctId);
        if (student != null) {
            nctStudents.remove(student);
            return true;
        }
        return false;
    }
}