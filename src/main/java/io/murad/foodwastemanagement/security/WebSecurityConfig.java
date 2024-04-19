package io.murad.foodwastemanagement.security;

import io.murad.foodwastemanagement.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) ->
                        authz.requestMatchers("/", "/register", "/profile", "/user").permitAll()
                                .requestMatchers("/donor-dashboard/**").hasRole("DONOR")
                                .requestMatchers("/foods/**").hasRole("DONOR")
                                .requestMatchers("/cart/**").hasAnyRole("DONOR", "CONSUMER")
                                .requestMatchers("/order/**").hasAnyRole("DONOR", "CONSUMER")
                                .requestMatchers("/consumer-dashboard/**").hasRole("CONSUMER")
                                .anyRequest()
                                .authenticated()

                )
                .formLogin(login ->
//                                login.loginPage("/sign-in")
//                                        .permitAll()
//                                        .successHandler(successHandler())
//                                        .loginProcessingUrl("/login-success")
//                                        .successForwardUrl("/login-success")
                                login
                                        .successHandler(successHandler())
                                        .loginProcessingUrl("/login-success")
//                                        .defaultSuccessUrl("/")
                                        .permitAll()
                )
                .logout(out ->
                        out.logoutUrl("/logout")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessUrl("/login?logout")
                );
//                .authenticationProvider(authenticationProvider);
        http.userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationHandler();
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthenticationProvider;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
