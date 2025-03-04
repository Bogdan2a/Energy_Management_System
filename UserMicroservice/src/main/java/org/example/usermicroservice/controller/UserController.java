package org.example.usermicroservice.controller;

import org.example.usermicroservice.JwtTokenUtil;
import org.example.usermicroservice.entity.User;
import org.example.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/isOn")
    public boolean isOn() {
        System.out.println("isOn");
        return true;
    }

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User validatedUser = userService.validateUser(user.getName(), user.getPassword());
        if (validatedUser != null) {
            // Generate JWT token
            String token = jwtTokenUtil.generateToken(validatedUser.getName(), validatedUser.getRole().toString());

            return ResponseEntity.ok(Map.of(
                    "id", validatedUser.getId(),
                    "message", "Logged in successfully!",
                    "role", validatedUser.getRole().toString(),
                    "token", token, // Return the token to the client
                    "redirectUrl", "admin".equals(validatedUser.getRole().toString()) ?
                            "/api/users/admin/dashboard" : "/api/users/client/dashboard"
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }




    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "Welcome to the Admin Dashboard!";
    }

    @GetMapping("/client/dashboard")
    public String clientDashboard() {
        return "Welcome to the Client Dashboard!";
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> updatedUser = userService.updateUser(id, userDetails);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
