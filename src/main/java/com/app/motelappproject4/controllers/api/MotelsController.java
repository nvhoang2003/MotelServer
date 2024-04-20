// MotelController.java
package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.auth.JwtUntil;
import com.app.motelappproject4.dtos.CreateMotelDTO;
import com.app.motelappproject4.models.*;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<Motel> index() {
        return (List<Motel>) motelRepository.findAll();
    }

    @GetMapping("/api/myMotels")
    public List<Motel> myMotels(HttpServletRequest request){
        String accessToken = jwtUntil.resolveToken(request);
        Claims claims = jwtUntil.resolveClaims(request);

        Integer userId = claims.get("userid", Integer.class);
        User u = usersRepository.findById(userId).get();
        return (List<Motel>) motelRepository.findMotelByUser(u);
    }

    @GetMapping("/api/motels/{id}")
    public Optional<Motel> find(@PathVariable int id) {
        return motelRepository.findById(id);
    }

    @PostMapping("/api/motels")
    public Motel create(@RequestBody CreateMotelDTO motel)
    {

        return motelRepository.save(motel);
    }

    @PutMapping("/api/motels/{id}")
    public int update(@PathVariable int id, @RequestBody Motel updatedMotel) {
        Optional<Motel> optionalMotel = motelRepository.findById(id);
        if (optionalMotel.isPresent()) {
            Motel existingMotel = optionalMotel.get();
            // Update fields here
            // For example: existingMotel.setStatus(updatedMotel.getStatus());
            motelRepository.save(existingMotel);
            return 1; // Success
        }
        return 0; // Failed to update
    }

    @DeleteMapping("/api/motels/{id}")
    public int delete(@PathVariable int id) {
        if (motelRepository.existsById(id)) {
            motelRepository.deleteById(id);
            return 1; // Success
        }
        return 0; // Failed to delete
    }

    @GetMapping("/api/seed/motels")
    public List<Motel> seedMotelsData() {
        Faker faker = new Faker();
        List<User> users = (List<User>) usersRepository.findAll();
        List<Motel> list = new ArrayList<Motel>();
        for (int i = 0; i < 10; i++) {
            Motel motel = new Motel();
            motel.setStatus(faker.lorem().word());
            motel.setIsDeleted(faker.number().numberBetween(0, 1)); // Assuming 0: Not Deleted, 1: Deleted
            motel.setAmount(faker.number().numberBetween(1000, 5000));
            motel.setAcreage(faker.number().numberBetween(20, 100));
            motel.setDescription(faker.lorem().paragraph());
            if (!users.isEmpty()) {
                motel.setCreatedBy(users.get(faker.number().numberBetween(0, users.size())));
            }
            motelRepository.save(motel);
            list.add(motel);
        }
        motelRepository.saveAll(list);
        return list;
    }
}