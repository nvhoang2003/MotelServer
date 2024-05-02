package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.Permission;
import com.app.motelappproject4.models.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PermissionsController {
    @Autowired
    private PermissionRepository permissionRepository;

    // GET all permissions
    @GetMapping("/api/permissions")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = (List<Permission>) permissionRepository.findAll();
        return ResponseEntity.ok(permissions);
    }

    // GET a single permission by ID
    @GetMapping("/api/permissions/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable int id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST a new permission
    @PostMapping("/api/permissions")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        Permission savedPermission = permissionRepository.save(permission);
        return new ResponseEntity<>(savedPermission, HttpStatus.CREATED);
    }

    // PUT update a permission
    @PutMapping("/api/permissions/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable int id, @RequestBody Permission updatedPermission) {
        return permissionRepository.findById(id).map(existingPermission -> {
            // Update fields here
            existingPermission.setName(updatedPermission.getName());
            existingPermission.setDescription(updatedPermission.getDescription());
            existingPermission.setIsDeleted(updatedPermission.getIsDeleted());
            Permission savedPermission = permissionRepository.save(existingPermission);
            return ResponseEntity.ok(savedPermission);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE a permission
    @DeleteMapping("/api/permissions/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable int id) {
        if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
