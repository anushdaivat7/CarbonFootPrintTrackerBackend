package com.example.carbontracker;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5713")
@RestController
@RequestMapping("/api/carbon")
public class CarbonLogController {
    private final CarbonService carbonService;
    private final AuthService authService;

    public CarbonLogController(CarbonService carbonService, AuthService authService) {
        this.carbonService = carbonService;
        this.authService = authService;
    }

    @PostMapping("/log")
    public CarbonLog logTransport(@Valid @RequestBody CarbonLogDTO dto) {
        return carbonService.logCarbon(dto);
    }

    @GetMapping("/user/email/{email}")
    public UserSummaryDTO getUserSummaryByEmail(@PathVariable String email) {
        return carbonService.getUserSummaryByEmail(email);
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardEntryDTO> getLeaderboard() {
        return carbonService.getLeaderboard();
    }

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody RegisterDTO dto) {
        return authService.register(dto);
    }

@PostMapping("/login")
@CrossOrigin(origins = "http://localhost:5713")
public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO dto) {
    UserPublicDTO user = authService.login(dto);
    if (user != null) {
        return ResponseEntity.ok(user); // ✅ send fullName, email, id in JSON
    } else {
        return ResponseEntity.status(401).body("Invalid email or password");
    }
}
    @GetMapping("/users")
    public List<UserPublicDTO> getAllUsers() {
        return authService.getAllUsers();
    }
    //
    @GetMapping("/summary/{email}")
    public List<EmissionSummaryDTO> getEmissionSummary(
            @PathVariable String email,
            @RequestParam String range) {
        return carbonService.getSummaryByRange(email, range);
    }
    //
    @GetMapping("/userLog/{email}")
    @CrossOrigin(origins = "http://localhost:5713")
    public ResponseEntity<List<CarbonLog>> getUserLogs(@PathVariable String email) {
        List<CarbonLog> logs = carbonService.getLogsByEmail(email);
        return ResponseEntity.ok(logs);
    }
}
