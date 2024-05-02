package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.auth.JwtUntil;
import com.app.motelappproject4.models.User;
import com.app.motelappproject4.models.UsersRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtUntil jwtUtil;

    // GET all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = (List<User>) usersRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // GET user profile based on JWT token
    @GetMapping("/getMyProfile")
    public ResponseEntity<User> getMyProfile(HttpServletRequest request) {
        String accessToken = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.resolveClaims(request);
        Integer userId = claims.get("userid", Integer.class);
        return usersRepository.findById(userId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET a single user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> user = usersRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT update a user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        return usersRepository.findById(id).map(existingUser -> {
            // Update fields here
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            User savedUser = usersRepository.save(existingUser);
            return ResponseEntity.ok(savedUser);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
