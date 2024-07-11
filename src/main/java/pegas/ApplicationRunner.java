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
        var context = SpringApplication.run(ApplicationRunner.class,args);
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http.csrf(CsrfConfigurer::disable)
////                .sessionManagement(j->j.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
////        return http.build();
//        http.httpBasic(basic-> basic.realmName("chicago"))
//                .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
//                .sessionManagement(j->j.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.httpBasic(i->i.realmName("start"))
                .authorizeHttpRequests(j->j.anyRequest().authenticated())
                .sessionManagement(i->i.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
