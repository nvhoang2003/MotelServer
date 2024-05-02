package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.Like;
import com.app.motelappproject4.models.Motel;
import com.app.motelappproject4.models.MotelRepository;
import com.app.motelappproject4.auth.JwtUntil;
import com.app.motelappproject4.dtos.CreateMotelDTO;
import com.app.motelappproject4.models.*;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import net.datafaker.Faker;
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

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JwtUntil jwtUntil;

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

//    @GetMapping("/api/myMotels")
//    public List<Motel> myMotels(HttpServletRequest request){
//        String accessToken = jwtUntil.resolveToken(request);
//        Claims claims = jwtUntil.resolveClaims(request);
//
//        Integer userId = claims.get("userid", Integer.class);
//        User u = usersRepository.findById(userId).get();
//        return (List<Motel>) motelRepository.findMotelByUser(u);
//    }

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

    @PostMapping("/api/motels/{id}/update")
    public ResponseEntity<Motel> update(@PathVariable int id, @RequestBody Motel updatedMotel) {
        System.out.println("Update motel");
        Optional<Motel> motelOptional = motelRepository.findById(id);
        if (!motelOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Motel motel = motelOptional.get();
        motel.setAcreage(updatedMotel.getAcreage());
        motel.setAmount(updatedMotel.getAmount());
        motel.setDescription(updatedMotel.getDescription());
        motel.setStatus(updatedMotel.getStatus());
        motel.setIsDeleted(updatedMotel.getIsDeleted());
        Motel savedMotel = motelRepository.save(motel);
        return ResponseEntity.ok(savedMotel);
    }

    @DeleteMapping("/api/motels/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return motelRepository.findById(id).map(motel -> {
            motelRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
