// RoleController.java
package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.Role;
import com.app.motelappproject4.models.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RolesController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/api/roles")
    public List<Role> index() {
        return (List<Role>) roleRepository.findAll();
    }

    @GetMapping("/api/roles/{id}")
    public Optional<Role> find(@PathVariable int id) {
        return roleRepository.findById(id);
    }

    @PostMapping("/api/roles")
    public Role create(@RequestBody Role role) {
        return roleRepository.save(role);
    }

    @PutMapping("/api/roles/{id}")
    public int update(@PathVariable int id, @RequestBody Role updatedRole) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role existingRole = optionalRole.get();
            // Update fields here
            // For example: existingRole.setName(updatedRole.getName());
            roleRepository.save(existingRole);
            return 1; // Success
        }
        return 0; // Failed to update
    }

    @DeleteMapping("/api/roles/{id}")
    public int delete(@PathVariable int id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return 1; // Success
        }
        return 0; // Failed to delete
    }
}
