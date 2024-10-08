package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Dto.LoginDto;
import Uv.DeliMgmt.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String login() {
        return "Hola";
    }

    @PutMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        return null;
    }
}
