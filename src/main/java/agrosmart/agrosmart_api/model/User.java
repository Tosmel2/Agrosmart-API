package agrosmart.agrosmart_api.model;

import jakarta.persistence.*;
// import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String email;

    private String password;

    private String phone;
    private String location;
    private String crops;
    private String tools;
    private String language;
    private String role;
    private String voice;
    private String address;


    @Column(name = "profile_pic", columnDefinition = "BYTEA") // Force correct type
    private byte[] profilePic;

    // private String role; // e.g., "USER", "ADMIN";

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getVoice() { return voice; }
    public void setVoice(String voice) { this.voice = voice; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getCrops() { return crops; }
    public void setCrops(String crops) { this.crops = crops; }

    public String getTools() { return tools; }
    public void setTools(String tools) { this.tools = tools; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public byte[] getProfilePic() { return profilePic; }
    public void setProfilePic(byte[] profilePic) { this.profilePic = profilePic; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}