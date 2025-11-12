package k23cnt3.Day05.controller;

import k23cnt3.Day05.entity.NctInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class NctHomeController {

    @GetMapping("/home")
    public String nctHome(Model nctModel) {
        nctModel.addAttribute("nctTitle", "Devmaster::Trang chủ - K23CNT3");
        return "NctHome";
    }

    @GetMapping("/about")
    public String nctAbout(Model nctModel) {
        nctModel.addAttribute("nctTitle", "Devmaster::Giới thiệu - K23CNT3");
        return "NctAbout";
    }

    @GetMapping("/contact")
    public String nctContact(Model nctModel) {
        nctModel.addAttribute("nctTitle", "Devmaster::Liên hệ - K23CNT3");
        return "NctContact";
    }

    @GetMapping("/")
    public String nctIndex(Model nctModel) {
        nctModel.addAttribute("nctTitle", "Devmaster::Trang chính - K23CNT3");
        return "NctIndex";
    }

    @GetMapping("/profile")
    public String nctProfile(Model nctModel) {
        List<NctInfo> nctProfileList = new ArrayList<>();
        nctProfileList.add(new NctInfo("Devmaster Academy", "nctDev",
                "contact@devmaster.edu.vn", "https://devmaster.edu.vn"));
        nctProfileList.add(new NctInfo("K23CNT3 - Lớp Spring Boot", "nctK23Cnt3",
                "k23cnt3@devmaster.edu.vn", "https://devmaster.edu.vn"));

        nctModel.addAttribute("nctDevmasterProfile", nctProfileList);
        nctModel.addAttribute("nctTitle", "Devmaster::Profile - K23CNT3");
        return "NctProfile";
    }

    // Thêm phương thức mới với tiền tố nct
    @GetMapping("/nctinfo")
    public String nctGetInfo(Model nctModel) {
        nctModel.addAttribute("nctMessage", "Thông tin từ phương thức nctGetInfo");
        return "NctInfo";
    }
}