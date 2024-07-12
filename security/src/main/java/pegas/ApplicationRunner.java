package pegas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class, args);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http.csrf(i->i.disable())
//                .formLogin(i->i.loginPage("http://localhost:8080/403.html"))
//                .authorizeHttpRequests(i->i
//                        .requestMatchers("/login.html").permitAll()
//                        .anyRequest().authenticated())
//                .exceptionHandling(i->i.authenticationEntryPoint(((request, response, authException) -> {
//                    response.sendRedirect("http://localhost:8080/login.html");
////                    authException.printStackTrace();
////                    response.sendError(HttpStatus.UNAUTHORIZED.value());
//                })))
//                .sessionManagement(i->i.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(i->i.realmName("cheese"))
                .authorizeHttpRequests(i->i.anyRequest().authenticated());

        return http.build();
    }
}
