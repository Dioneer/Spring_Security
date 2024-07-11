package pegas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class, args);
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http.csrf(i->i.disable())
//                .sessionManagement(i->i.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(i->i.anyRequest().authenticated());
//        return http.build();
//    }
}
