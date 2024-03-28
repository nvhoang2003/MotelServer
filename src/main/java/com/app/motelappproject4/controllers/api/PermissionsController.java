// PermissionController.java
package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.Permission;
import com.app.motelappproject4.models.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PermissionsController {
    @Autowired
    private PermissionRepository permissionRepository;

    @GetMapping("/api/permissions")
    public List<Permission> index() {
        return (List<Permission>) permissionRepository.findAll();
    }

    @GetMapping("/api/permissions/{id}")
    public Optional<Permission> find(@PathVariable int id) {
        return permissionRepository.findById(id);
    }

    @PostMapping("/api/permissions")
    public Permission create(@RequestBody Permission permission) {
        return permissionRepository.save(permission);
    }

    @PutMapping("/api/permissions/{id}")
    public int update(@PathVariable int id, @RequestBody Permission updatedPermission) {
        Optional<Permission> optionalPermission = permissionRepository.findById(id);
        if (optionalPermission.isPresent()) {
            Permission existingPermission = optionalPermission.get();
            // Update fields here
            // For example: existingPermission.setName(updatedPermission.getName());
            permissionRepository.save(existingPermission);
            return 1; // Success
        }
        return 0; // Failed to update
    }

    @DeleteMapping("/api/permissions/{id}")
    public int delete(@PathVariable int id) {
        if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id);
            return 1; // Success
        }
        return 0; // Failed to delete
    }
}
