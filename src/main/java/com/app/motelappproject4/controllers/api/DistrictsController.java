package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.dtos.DistrictCreateDTO;
import com.app.motelappproject4.models.City;
import com.app.motelappproject4.models.CityRepository;
import com.app.motelappproject4.models.District;
import com.app.motelappproject4.models.DistrictsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class DistrictsController {

    @Autowired
    private DistrictsRepository districtsRepository;

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/api/districts/getList/{cityId}")
    public ResponseEntity<List<District>> getDistrictsByCityId(@PathVariable Integer cityId) {
        List<District> listDistrict = new ArrayList<>();

        if (cityId != null) {
            listDistrict = districtsRepository.getListDistrictByCityId(cityId);
        } else {
            listDistrict = (List<District>) districtsRepository.findAll();
        }

        return new ResponseEntity<>(listDistrict, HttpStatus.OK);
    }

    @GetMapping("/api/districts/getById/{id}")
    public ResponseEntity<District> getDistrictById(@PathVariable int id) {
        Optional<District> optionalDistrict = districtsRepository.findById(id);
        if (optionalDistrict.isPresent()) {
            return new ResponseEntity<>(optionalDistrict.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/districts")
    public ResponseEntity<District> createDistrict(@RequestBody DistrictCreateDTO district) {
        District disAdd = new District();
        disAdd.setName(district.getName());

        Optional<City> optionalCity = cityRepository.findById(district.getCity_id());
        if (optionalCity.isPresent()) {
            disAdd.setCity(optionalCity.get());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // City ID not found
        }

        District savedDistrict = districtsRepository.save(disAdd);
        return new ResponseEntity<>(savedDistrict, HttpStatus.CREATED);
    }

    @PutMapping("/api/districts/{id}")
    public ResponseEntity<District> updateDistrict(@PathVariable int id, @RequestBody District updatedDistrict) {
        Optional<District> optionalDistrict = districtsRepository.findById(id);
        if (optionalDistrict.isPresent()) {
            District existingDistrict = optionalDistrict.get();
            existingDistrict.setName(updatedDistrict.getName());
            districtsRepository.save(existingDistrict);
            return new ResponseEntity<>(existingDistrict, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/districts/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable int id) {
        if (districtsRepository.existsById(id)) {
            districtsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // District ID not found
        }
    }

    @GetMapping("/rest/auth/api/districts/city/{cityId}")
    public ResponseEntity<List<District>> getDistrictsByCityIdAuth(@PathVariable int cityId) {
        List<District> districts = districtsRepository.getListDistrictByCityId(cityId);
        return new ResponseEntity<>(districts, HttpStatus.OK);
    }

    @GetMapping("/rest/auth/api/districts/city")
    public ResponseEntity<List<District>> getAllDistrictsAuth() {
        List<District> districts = districtsRepository.getListDistrictByCityId();
        return new ResponseEntity<>(districts, HttpStatus.OK);
    }
}
