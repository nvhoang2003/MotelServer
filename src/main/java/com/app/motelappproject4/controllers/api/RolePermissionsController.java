package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.RolePermission;
import com.app.motelappproject4.models.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rolePermissions")
public class RolePermissionsController {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    // GET all role permissions
    @GetMapping
    public ResponseEntity<List<RolePermission>> getAllRolePermissions() {
        List<RolePermission> rolePermissions = (List<RolePermission>) rolePermissionRepository.findAll();
        return ResponseEntity.ok(rolePermissions);
    }

    // GET a single role permission by ID
    @GetMapping("/{id}")
    public ResponseEntity<RolePermission> getRolePermissionById(@PathVariable int id) {
        Optional<RolePermission> rolePermission = rolePermissionRepository.findById(id);
        return rolePermission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST a new role permission
    @PostMapping
    public ResponseEntity<RolePermission> createRolePermission(@RequestBody RolePermission rolePermission) {
        RolePermission savedRolePermission = rolePermissionRepository.save(rolePermission);
        return new ResponseEntity<>(savedRolePermission, HttpStatus.CREATED);
    }

    // PUT update a role permission
    @PutMapping("/{id}")
    public ResponseEntity<RolePermission> updateRolePermission(@PathVariable int id, @RequestBody RolePermission updatedRolePermission) {
        return rolePermissionRepository.findById(id).map(existingRolePermission -> {
            // Update fields here
            // Assuming RolePermission has methods to set role and permission
            existingRolePermission.setRole(updatedRolePermission.getRole());
            existingRolePermission.setPermission(updatedRolePermission.getPermission());
            RolePermission savedRolePermission = rolePermissionRepository.save(existingRolePermission);
            return ResponseEntity.ok(savedRolePermission);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE a role permission
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRolePermission(@PathVariable int id) {
        if (rolePermissionRepository.existsById(id)) {
            rolePermissionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
