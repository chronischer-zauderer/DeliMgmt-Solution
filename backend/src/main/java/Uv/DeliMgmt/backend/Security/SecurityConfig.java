package Uv.DeliMgmt.backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private JwtAuthEntryPoint jwtAuthEntryPoint;
    private UserDetailsServiceImpl userDetailsService;
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

 @Bean
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

     return http
             .csrf(csrf -> csrf.disable())
             .exceptionHandling()
             .authenticationEntryPoint(jwtAuthEntryPoint)
             .and()
             .httpBasic(Customizer.withDefaults())
             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             .authorizeHttpRequests(https -> {
                 //Configurar los endpoints privados
                 https.requestMatchers(HttpMethod.POST,"/auth/login").permitAll();
                 //Configurar el resto de endpoints -  NO ESPECIFICADOS
                 https.anyRequest().authenticated();
             })
             .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
             .build();
 }
 @Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
 }
@Bean
 public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) throws Exception {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    return daoAuthenticationProvider;
 }

 @Bean
 public PasswordEncoder passwordEncoder() {
    //return NoOpPasswordEncoder.getInstance();
     return new BCryptPasswordEncoder();
 }
 @Bean
 public JwtAuthFilter jwtAuthFilter () throws Exception {
        return new JwtAuthFilter();
 }
}
