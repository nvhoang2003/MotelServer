
// RolePermissionController.java
package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.RolePermission;
import com.app.motelappproject4.models.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RolePermissionsController {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @GetMapping("/api/rolePermissions")
    public List<RolePermission> index() {
        return (List<RolePermission>) rolePermissionRepository.findAll();
    }

    @GetMapping("/api/rolePermissions/{id}")
    public Optional<RolePermission> find(@PathVariable int id) {
        return rolePermissionRepository.findById(id);
    }

    @PostMapping("/api/rolePermissions")
    public RolePermission create(@RequestBody RolePermission rolePermission) {
        return rolePermissionRepository.save(rolePermission);
    }

    @PutMapping("/api/rolePermissions/{id}")
    public int update(@PathVariable int id, @RequestBody RolePermission updatedRolePermission) {
        Optional<RolePermission> optionalRolePermission = rolePermissionRepository.findById(id);
        if (optionalRolePermission.isPresent()) {
            RolePermission existingRolePermission = optionalRolePermission.get();
            // Update fields here
            // For example: existingRolePermission.setRole(updatedRolePermission.getRole());
            rolePermissionRepository.save(existingRolePermission);
            return 1; // Success
        }
        return 0; // Failed to update
    }

    @DeleteMapping("/api/rolePermissions/{id}")
    public int delete(@PathVariable int id) {
        if (rolePermissionRepository.existsById(id)) {
            rolePermissionRepository.deleteById(id);
            return 1; // Success
        }
        return 0; // Failed to delete
    }
}
