package k23cnt3.nguyencongtung.project3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class NctSecurityConfig {

    @Bean
    public PasswordEncoder nctPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain nctSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // SỬA Ở ĐÂY: Dùng authorizeHttpRequests thay vì authorizeRequests
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Cho phép tất cả truy cập
                )
                // Cú pháp disable CSRF chuẩn cho Spring Security 6.1+
                .csrf(AbstractHttpConfigurer::disable)
                // Hoặc viết kiểu lambda: .csrf(csrf -> csrf.disable())

                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
}