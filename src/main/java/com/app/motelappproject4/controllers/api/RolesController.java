package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.Role;
import com.app.motelappproject4.models.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RolesController {
    @Autowired
    private RoleRepository roleRepository;

    // GET all roles
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }

    // GET a single role by ID
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable int id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST a new role
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role savedRole = roleRepository.save(role);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    // PUT update a role
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable int id, @RequestBody Role updatedRole) {
        return roleRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setName(updatedRole.getName());
                    existingRole.setDescription(updatedRole.getDescription());
                    existingRole.setIsDeleted(updatedRole.getIsDeleted());
                    Role savedRole = roleRepository.save(existingRole);
                    return ResponseEntity.ok(savedRole);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE a role
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
