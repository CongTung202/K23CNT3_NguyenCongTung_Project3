package k23cnt3.Day06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        nctPrintStartupMessage();
    }

    private static void nctPrintStartupMessage() {
        System.out.println("==========================================");
        System.out.println("K23CNT3 - DAY06 - CRUD APPLICATION - NCT");
        System.out.println("Đang chạy tại: http://localhost:8080");
        System.out.println("Danh sách sinh viên: http://localhost:8080/nct-students");
        System.out.println("==========================================");
    }
} 