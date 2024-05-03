package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.dtos.CreateMotelDTO;
import com.app.motelappproject4.models.Motel;
import com.app.motelappproject4.models.MotelRepository;
import com.app.motelappproject4.auth.JwtUntil;
import com.app.motelappproject4.models.*;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class MotelsController {
    @Autowired
    private MotelRepository motelRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JwtUntil jwtUntil;
    @Autowired
    private TenantRepository tenantRepository;

    @GetMapping("/api/motels")
    public ResponseEntity<List<Motel>> index() {
        List<Motel> motels = (List<Motel>) motelRepository.findAll();
        if (motels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motels);
    }

    @GetMapping("/api/motels/user/{userId}")
    public ResponseEntity<List<Motel>> getMotelsByCreatedBy_Id(@PathVariable int userId) {
        List<Motel> motels = (List<Motel>) motelRepository.getMotelsByCreatedBy_Id(userId);
        if (motels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motels);
    }

    @GetMapping("/api/motelsOfTenant")
    public List<Motel> myMotels(HttpServletRequest request){
        String accessToken = jwtUntil.resolveToken(request);
        Claims claims = jwtUntil.resolveClaims(request);

        Integer userId = claims.get("userid", Integer.class);
        User u = usersRepository.findById(userId).get();

        List<Tenant> t = (List<Tenant>) tenantRepository.getTenantsByUser(u);
        List<Motel> res = new ArrayList<>();
        t.forEach(tenant -> {
            res.add(tenant.getMotel());
        });

        return res;
    }

    @GetMapping("/api/motels/{id}")
    public ResponseEntity<CreateMotelDTO> find(@PathVariable int id) {
        Optional<Motel> motel = motelRepository.findById(id);
        if (!motel.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        var currentMotel = motel.get();
        CreateMotelDTO res = new CreateMotelDTO();

        res.setAcreage(currentMotel.getAcreage());
        res.setAmount(currentMotel.getAmount());
        res.setDescription(currentMotel.getDescription());

        List<Tenant> currentTenant = (List<Tenant>) tenantRepository.getTenantsByMotel(currentMotel);
        StringBuilder emailTenant = new StringBuilder();

        AtomicInteger counter = new AtomicInteger(0);

        currentTenant.forEach(tenant -> {
            int idx = counter.getAndIncrement();
            emailTenant.append(tenant.getUser().getEmail());

            if(idx != currentTenant.size() - 1){
                emailTenant.append(", ");
            }
        });

        res.setEmailTenant(emailTenant.toString());

        return ResponseEntity.ok(res);
    }

    @PostMapping("/api/motels")
    public ResponseEntity<String> create(@RequestBody CreateMotelDTO motelToAdd) {
        try {
            Motel motel = new Motel();

            motel.setAmount(motelToAdd.getAmount());
            motel.setAcreage(motelToAdd.getAcreage());
            motel.setDescription(motelToAdd.getDescription());
            motel.setCreatedBy(motelToAdd.getCreatedBy());
            var emailTenant = motelToAdd.getEmailTenant();
            List<String> listTeant = Arrays.stream(emailTenant.split(",")).toList();
            List<Tenant> lt = new ArrayList<>();
            listTeant.forEach(oneTenant -> {
                var user = usersRepository.findUserByEmail(oneTenant.trim());
                if(user != null) {
                    Tenant t = new Tenant();
                    t.setUser(user);

                    lt.add(t);
                }else{
                    throw new RuntimeException("Email " + oneTenant + " Bị Sai");
                }
            });

            Motel savedMotel = motelRepository.save(motel);
            lt.forEach(t ->{
                t.setMotel(savedMotel);
                Tenant tSave = tenantRepository.save(t);
            });

            return ResponseEntity.status(201).body("Ok");
        } catch (RuntimeException e) {
            // Khi có lỗi xảy ra, Spring sẽ tự động rollback lại tất cả các thay đổi trong database
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/api/motels/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody CreateMotelDTO updatedMotel) {
        try{
            var existingMotelObj = motelRepository.findById(id).get();

            var deleteTenants = tenantRepository.getTenantsByMotel(existingMotelObj);

            var emailTenant = updatedMotel.getEmailTenant();
            List<String> listTeant = Arrays.stream(emailTenant.split(",")).toList();
            List<Tenant> lt = new ArrayList<>();
            listTeant.forEach(oneTenant -> {
                var user = usersRepository.findUserByEmail(oneTenant.trim());
                if(user != null) {
                    Tenant t = new Tenant();
                    t.setUser(user);

                    lt.add(t);
                }else{
                    throw new RuntimeException("Email " + oneTenant + " Bị Sai");
                }
            });

            lt.forEach(t -> {
                t.setMotel(existingMotelObj);

                Tenant tSave = tenantRepository.save(t);
            });

            deleteTenants.forEach(tenant -> {
                tenantRepository.deleteById(tenant.getId());
            });

                // Cập nhật các thuộc tính của existingMotel từ updatedMotel
            existingMotelObj.setAcreage(updatedMotel.getAcreage());
            existingMotelObj.setAmount(updatedMotel.getAmount());
            existingMotelObj.setDescription(updatedMotel.getDescription());

                // Lưu lại đối tượng motel đã cập nhật
            Motel savedMotel = motelRepository.save(existingMotelObj);

            return ResponseEntity.ok("Ok");

        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/api/motels/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return motelRepository.findById(id).map(motel -> {
            motelRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
