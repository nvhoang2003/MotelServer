// UserRoleController.java
package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.UserRole;
import com.app.motelappproject4.models.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserRolesController {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @GetMapping("/api/userRoles")
    public List<UserRole> index() {
        return (List<UserRole>) userRoleRepository.findAll();
    }

    @GetMapping("/api/userRoles/{id}")
    public Optional<UserRole> find(@PathVariable int id) {
        return userRoleRepository.findById(id);
    }

    @PostMapping("/api/userRoles")
    public UserRole create(@RequestBody UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @PutMapping("/api/userRoles/{id}")
    public int update(@PathVariable int id, @RequestBody UserRole updatedUserRole) {
        Optional<UserRole> optionalUserRole = userRoleRepository.findById(id);
        if (optionalUserRole.isPresent()) {
            UserRole existingUserRole = optionalUserRole.get();
            // Update fields here
            // For example: existingUserRole.setUser(updatedUserRole.getUser());
            userRoleRepository.save(existingUserRole);
            return 1; // Success
        }
        return 0; // Failed to update
    }

    @DeleteMapping("/api/userRoles/{id}")
    public int delete(@PathVariable int id) {
        if (userRoleRepository.existsById(id)) {
            userRoleRepository.deleteById(id);
            return 1; // Success
        }
        return 0; // Failed to delete
    }
}
