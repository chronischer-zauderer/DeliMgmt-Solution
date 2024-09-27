package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Services.JwtUtil;
import Uv.DeliMgmt.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Validar usuario (aquí deberías hacer la validación real)
        if (userService.validateUser(username, password)) {
            return jwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    // Otros métodos...
}
