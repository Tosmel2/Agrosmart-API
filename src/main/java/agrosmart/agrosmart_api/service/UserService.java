package agrosmart.agrosmart_api.service;

import agrosmart.agrosmart_api.dto.KycProfileDTO;
import agrosmart.agrosmart_api.model.User;
import agrosmart.agrosmart_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User updateProfile(String email, String name, String phone, String location, String address) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setName(name);
        user.setPhone(phone);
        user.setLocation(location);
        user.setAddress(address);
        return userRepository.save(user);
    }

    public boolean changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow();
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void updateProfilePic(String email, MultipartFile file) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setProfilePic(file.getBytes());
        userRepository.save(user);
    }

    public void updateKyc(String email, KycProfileDTO dto) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            user.setVoice(dto.getVoice());
            user.setRole(dto.getRole());
            user.setCrops(dto.getCrops());
            user.setTools(dto.getTools());
            user.setLanguage(dto.getLanguage());
            userRepository.save(user);
        }
    }


    public User getUserByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
}
}