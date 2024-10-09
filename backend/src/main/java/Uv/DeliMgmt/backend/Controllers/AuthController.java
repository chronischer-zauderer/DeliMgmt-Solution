package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Dto.AuthResponseDto;
import Uv.DeliMgmt.backend.Dto.LoginDto;
import Uv.DeliMgmt.backend.Repositories.UserRepository;
import Uv.DeliMgmt.backend.Security.JwtGenerator;
import Uv.DeliMgmt.backend.Security.SecurityConfig;
import Uv.DeliMgmt.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtGenerator generator;

    @GetMapping("/test")
    public String login() {
        return "Hola";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        System.out.println("Login attempt: " + loginDto.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("Login attempt: " + loginDto.getUsername());
        String token = generator.generateJwt(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }


}


