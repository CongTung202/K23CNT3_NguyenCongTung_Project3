package k23cnt3.Day05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] Args) {
        SpringApplication.run(Application.class, Args);
        PrintStartupMessage();
    }

    private static void PrintStartupMessage() {
        System.out.println("Ứng dụng K23CNT3 Day05 với tiền tố NCT đang chạy tại: http://localhost:8080");
    }

    // Thêm phương thức với tiền tố nct
    public void DisplayInfo() {
        System.out.println("NCT Application Info");
    }
}