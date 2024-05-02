package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.UserRole;
import com.app.motelappproject4.models.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userRoles")
public class UserRolesController {
    @Autowired
    private UserRoleRepository userRoleRepository;

    // GET all user roles
    @GetMapping
    public ResponseEntity<List<UserRole>> getAllUserRoles() {
        List<UserRole> userRoles = (List<UserRole>) userRoleRepository.findAll();
        return ResponseEntity.ok(userRoles);
    }

    // GET a single user role by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserRole> getUserRoleById(@PathVariable int id) {
        Optional<UserRole> userRole = userRoleRepository.findById(id);
        return userRole.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST a new user role
    @PostMapping
    public ResponseEntity<UserRole> createUserRole(@RequestBody UserRole userRole) {
        UserRole savedUserRole = userRoleRepository.save(userRole);
        return new ResponseEntity<>(savedUserRole, HttpStatus.CREATED);
    }

    // PUT update a user role
    @PutMapping("/{id}")
    public ResponseEntity<UserRole> updateUserRole(@PathVariable int id, @RequestBody UserRole updatedUserRole) {
        return userRoleRepository.findById(id).map(existingUserRole -> {
            // Assuming UserRole has methods to set user and role
            existingUserRole.setUser(updatedUserRole.getUser());
            existingUserRole.setRole(updatedUserRole.getRole());
            UserRole savedUserRole = userRoleRepository.save(existingUserRole);
            return ResponseEntity.ok(savedUserRole);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE a user role
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable int id) {
        if (userRoleRepository.existsById(id)) {
            userRoleRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
