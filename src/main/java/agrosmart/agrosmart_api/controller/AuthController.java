package agrosmart.agrosmart_api.controller;

import agrosmart.agrosmart_api.service.AuthService;
import agrosmart.agrosmart_api.dto.UserDTO;
import agrosmart.agrosmart_api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

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

    // Added: Secure endpoint to fetch user details using JWT authentication
    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication authentication) {
        String email = authentication.getName();
        User user = authService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return ResponseEntity.ok(userDTO);
    }
    // @GetMapping("/user")
    // public User getUser(Authentication authentication) {
    //     String email = authentication.getName(); // gets email from JWT token
    //     return authService.getUserByEmail(email);
    // }
}