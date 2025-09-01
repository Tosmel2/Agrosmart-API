package agrosmart.agrosmart_api.controller;

import agrosmart.agrosmart_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String cpassword) {
        if (!password.equals(cpassword)) {
            return "Passwords do not match";
        }
        authService.register(name, email, password);
        return "Registration successful";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        String token = authService.login(email, password);
        if (token != null) {
            return "Login successful. Token: " + token;
        } else {
            return "Invalid credentials";
        }
    }
}