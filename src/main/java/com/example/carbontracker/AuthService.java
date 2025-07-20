//package com.example.carbontracker;
//
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class AuthService {
//    private final UserRepository repo;
//
//    public AuthService(UserRepository repo) {
//        this.repo = repo;
//    }
//
//    public String register(RegisterDTO dto) {
//        // Debug logs
//        System.out.println("Received DTO: " + dto);
//        System.out.println("Password: " + dto.getPassword());
//        System.out.println("Confirm Password: " + dto.getConfirmPassword());
//
//        // Null-safe password comparison
//        if (dto.getPassword() == null || !dto.getPassword().equals(dto.getConfirmPassword())) {
//            return "Passwords do not match.";
//        }
//
//        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
//            return "Email cannot be empty.";
//        }
//
//        if (repo.findByEmail(dto.getEmail()).isPresent()) {
//            return "Email already registered.";
//        }
//
//        User user = new User();
//        user.setFullName(dto.getFullName());
//        user.setEmail(dto.getEmail());
//        user.setPassword(dto.getPassword()); // 🔐 Hash this before saving!
//
//        repo.save(user); // MongoDB will auto-generate ID
//        return "User registered successfully.";
//    }
//
//    public String login(LoginDTO dto) {
//        Optional<User> user = repo.findByEmail(dto.getEmail());
//
//        if (user.isPresent()) {
//            if (dto.getPassword() == null) {
//                return "Password cannot be null.";
//            }
//
//            // 🔐 In production: compare hashed passwords using BCrypt
//            if (dto.getPassword().equals(user.get().getPassword())) {
//                return "Login successful. User ID: " + user.get().getId();
//            }
//        }
//
//        return "Invalid email or password.";
//    }
//
//    public List<UserPublicDTO> getAllUsers() {
//        return repo.findAll().stream()
//                .map(user -> new UserPublicDTO(user.getId(), user.getFullName(), user.getEmail()))
//                .collect(Collectors.toList());
//    }
//
//}


//new
        package com.example.carbontracker;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final UserRepository repo;

    public AuthService(UserRepository repo) {
        this.repo = repo;
    }

    public String register(RegisterDTO dto) {
        System.out.println("Received DTO: " + dto);
        System.out.println("Password: " + dto.getPassword());
        System.out.println("Confirm Password: " + dto.getConfirmPassword());

        if (dto.getPassword() == null || !dto.getPassword().equals(dto.getConfirmPassword())) {
            return "Passwords do not match.";
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            return "Email cannot be empty.";
        }

        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            return "Email already registered.";
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // 🔐 In real-world, hash it!

        repo.save(user);
        return "User registered successfully.";
    }

    // 🔁 Updated login method
    public UserPublicDTO login(LoginDTO dto) {
        Optional<User> userOpt = repo.findByEmail(dto.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (dto.getPassword() != null && dto.getPassword().equals(user.getPassword())) {
                return new UserPublicDTO(user.getId(), user.getFullName(), user.getEmail());
            }
        }

        return null; // invalid login
    }

    public List<UserPublicDTO> getAllUsers() {
        return repo.findAll().stream()
                .map(user -> new UserPublicDTO(user.getId(), user.getFullName(), user.getEmail()))
                .collect(Collectors.toList());
    }
}
