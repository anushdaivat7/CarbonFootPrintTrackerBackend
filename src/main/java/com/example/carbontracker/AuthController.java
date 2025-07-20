package com.example.carbontracker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5174") // ✅ Applies to all
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterDTO dto) {
        return authService.register(dto);
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        UserPublicDTO user = authService.login(dto); // Now returns user data

        if (user != null) {
            return ResponseEntity.ok(user); // ✅ Return user data in JSON
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }


    @GetMapping("/users")
    public List<UserPublicDTO> getAllUsers() {
        return authService.getAllUsers();
    }
}
