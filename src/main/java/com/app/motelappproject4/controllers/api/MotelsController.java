package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.Like;
import com.app.motelappproject4.models.Motel;
import com.app.motelappproject4.models.MotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

@RestController
public class MotelsController {
    @Autowired
    private MotelRepository motelRepository;

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

    @GetMapping("/api/motels/{id}")
    public ResponseEntity<Motel> find(@PathVariable int id) {
        Optional<Motel> motel = motelRepository.findById(id);
        if (!motel.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(motel.get());
    }

    @PostMapping("/api/motels")
    public ResponseEntity<Motel> create(@RequestBody Motel motel) {
        try {
            Motel savedMotel = motelRepository.save(motel);
            return ResponseEntity.status(201).body(savedMotel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/api/motels/{id}")
    public ResponseEntity<Motel> update(@PathVariable int id, @RequestBody Motel updatedMotel) {
        return motelRepository.findById(id).map(existingMotel -> {
            // Cập nhật các thuộc tính của existingMotel từ updatedMotel
            existingMotel.setAcreage(updatedMotel.getAcreage());
            existingMotel.setAmount(updatedMotel.getAmount());
            existingMotel.setDescription(updatedMotel.getDescription());
            existingMotel.setStatus(updatedMotel.getStatus());
            existingMotel.setDistrict(updatedMotel.getDistrict());
            existingMotel.setIsDeleted(updatedMotel.getIsDeleted());
            // Lưu lại đối tượng motel đã cập nhật
            Motel savedMotel = motelRepository.save(existingMotel);
            return ResponseEntity.ok(savedMotel);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/motels/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return motelRepository.findById(id).map(motel -> {
            motelRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
