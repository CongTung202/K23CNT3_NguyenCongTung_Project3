package k23cnt3.Day06.controller;

import k23cnt3.Day06.dto.NctStudentDTO;
import k23cnt3.Day06.service.NctStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nct-students")
public class NctStudentController {

    @Autowired
    private NctStudentService nctStudentService;

    @GetMapping
    public String nctShowStudentList(Model model) {
        model.addAttribute("nctStudents", nctStudentService.nctGetAllStudents());
        return "nct-students/nct-student-list";
    }

    @GetMapping("/nct-add")
    public String nctShowAddForm(Model model) {
        model.addAttribute("nctStudent", new NctStudentDTO());
        return "nct-students/nct-student-form";
    }

    @GetMapping("/nct-edit/{nctId}")
    public String nctShowEditForm(@PathVariable("nctId") Long nctId, Model model) {
        NctStudentDTO student = nctStudentService.nctGetStudentById(nctId);
        model.addAttribute("nctStudent", student);
        return "nct-students/nct-student-form";
    }

    @PostMapping("/nct-save")
    public String nctSaveStudent(@ModelAttribute("nctStudent") NctStudentDTO nctStudentDTO) {
        nctStudentService.nctCreateStudent(nctStudentDTO);
        return "redirect:/nct-students";
    }

    @PostMapping("/nct-update/{nctId}")
    public String nctUpdateStudent(@PathVariable("nctId") Long nctId,
                                   @ModelAttribute("nctStudent") NctStudentDTO nctStudentDTO) {
        nctStudentService.nctUpdateStudent(nctId, nctStudentDTO);
        return "redirect:/nct-students";
    }

    @GetMapping("/nct-delete/{nctId}")
    public String nctDeleteStudent(@PathVariable("nctId") Long nctId) {
        nctStudentService.nctDeleteStudent(nctId);
        return "redirect:/nct-students";
    }
}