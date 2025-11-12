package k23cnt3.Day06.service;

import k23cnt3.Day06.dto.NctStudentDTO;
import k23cnt3.Day06.entity.NctStudent;
import k23cnt3.Day06.repository.NctStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NctStudentService {

    @Autowired
    private NctStudentRepository nctStudentRepository;

    // Lấy tất cả students
    public List<NctStudentDTO> nctGetAllStudents() {
        List<NctStudent> students = nctStudentRepository.findAll();
        return students.stream()
                .map(this::nctConvertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy student theo ID
    public NctStudentDTO nctGetStudentById(Long nctId) {
        NctStudent student = nctStudentRepository.findById(nctId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + nctId));
        return nctConvertToDTO(student);
    }

    // Thêm mới student
    public NctStudentDTO nctCreateStudent(NctStudentDTO nctStudentDTO) {
        NctStudent student = new NctStudent();
        student.setNctName(nctStudentDTO.getNctName());
        student.setNctEmail(nctStudentDTO.getNctEmail());
        student.setNctAge(nctStudentDTO.getNctAge());

        NctStudent savedStudent = nctStudentRepository.save(student);
        return nctConvertToDTO(savedStudent);
    }

    // Cập nhật student
    public NctStudentDTO nctUpdateStudent(Long nctId, NctStudentDTO nctStudentDTO) {
        NctStudent existingStudent = nctStudentRepository.findById(nctId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + nctId));

        existingStudent.setNctName(nctStudentDTO.getNctName());
        existingStudent.setNctEmail(nctStudentDTO.getNctEmail());
        existingStudent.setNctAge(nctStudentDTO.getNctAge());

        NctStudent updatedStudent = nctStudentRepository.save(existingStudent);
        return nctConvertToDTO(updatedStudent);
    }

    // Xóa student
    public void nctDeleteStudent(Long nctId) {
        if (!nctStudentRepository.existsById(nctId)) {
            throw new RuntimeException("Student not found with id: " + nctId);
        }
        nctStudentRepository.deleteById(nctId);
    }

    // Chuyển đổi Entity sang DTO
    private NctStudentDTO nctConvertToDTO(NctStudent student) {
        return new NctStudentDTO(
                student.getNctId(),
                student.getNctName(),
                student.getNctEmail(),
                student.getNctAge()
        );
    }
}