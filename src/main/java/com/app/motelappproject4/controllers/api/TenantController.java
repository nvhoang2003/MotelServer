package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.Tenant;
import com.app.motelappproject4.models.TenantRepository;
import com.app.motelappproject4.models.User;
import com.app.motelappproject4.models.Motel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {
    @Autowired
    private TenantRepository tenantRepository;

    // GET all tenants
    @GetMapping
    public ResponseEntity<List<Tenant>> getAllTenants() {
        List<Tenant> tenants = (List<Tenant>) tenantRepository.findAll();
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/motel/{id}")
    public ResponseEntity<List<Tenant>> getAllTenantsByMotel_Id(@PathVariable int id) {
        List<Tenant> tenants = (List<Tenant>) tenantRepository.getTenantsByMotel_Id(id);
        return ResponseEntity.ok(tenants);
    }

    // GET a single tenant by ID
    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenantById(@PathVariable int id) {
        Optional<Tenant> tenant = tenantRepository.findById(id);
        return tenant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT update a tenant
    @PutMapping("/{id}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable int id, @RequestBody Tenant updatedTenant) {
        return tenantRepository.findById(id).map(existingTenant -> {
            existingTenant.setUser(updatedTenant.getUser());
            existingTenant.setMotel(updatedTenant.getMotel());
            existingTenant.setStatus(updatedTenant.getStatus());
            Tenant savedTenant = tenantRepository.save(existingTenant);
            return ResponseEntity.ok(savedTenant);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE a tenant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable int id) {
        if (tenantRepository.existsById(id)) {
            tenantRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
