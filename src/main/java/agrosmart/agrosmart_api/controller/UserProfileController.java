package agrosmart.agrosmart_api.controller;

import agrosmart.agrosmart_api.service.UserService;
import agrosmart.agrosmart_api.dto.KycProfileDTO;
import agrosmart.agrosmart_api.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {
    @Autowired
    private UserService userService;

    @PutMapping("/update")
    public String updateProfile(@RequestParam String email,
                                @RequestParam String name,
                                @RequestParam String phone,
                                @RequestParam String location,
                                @RequestParam String address) {
        userService.updateProfile(email, name, phone, location, address);
        return "Profile updated";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String email,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword) {
        boolean success = userService.changePassword(email, currentPassword, newPassword);
        return success ? "Password changed" : "Current password incorrect";
    }

    @PostMapping("/profile-pic")
    public String uploadProfilePic(@RequestParam String email, @RequestParam("file") MultipartFile file) {
        try {
            userService.updateProfilePic(email, file);
            return "Profile picture updated";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to update profile picture";
        }
    }

    @PutMapping("/kyc-update")
    public ResponseEntity<?> updateKyc(Authentication authentication, @RequestBody KycProfileDTO dto) {
        String email = authentication.getName(); // logged-in user’s email
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        userService.updateKyc(email, dto);
        return ResponseEntity.ok("KYC Profile updated");
    }


    @GetMapping("/kyc-profile")
    public ResponseEntity<?> getUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        KycProfileDTO dto = new KycProfileDTO();
        dto.setVoice(user.getVoice());
        dto.setRole(user.getRole());
        dto.setTools(user.getTools());
        dto.setCrops(user.getCrops());
        dto.setLanguage(user.getLanguage());
        return ResponseEntity.ok(dto);
    }

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        boolean deleted = userService.deleteUserById(id);
        if (deleted) {
            return ResponseEntity.ok("User deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}