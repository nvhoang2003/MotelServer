package com.app.motelappproject4.controllers.api;
import com.app.motelappproject4.models.City;
import com.app.motelappproject4.models.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class CityController {
    @Autowired
    CityRepository cityRepository;

    @GetMapping("/api/city")
    public ResponseEntity<List<City>> index() {
        List<City> cities = (List<City>) cityRepository.findAll();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/api/city/{id}")
    public ResponseEntity<City> find(@PathVariable int id) {
        Optional<City> optionalCity = cityRepository.findById(id);
        if (optionalCity.isPresent()) {
            return new ResponseEntity<>(optionalCity.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/city")
    public ResponseEntity<City> create(@RequestBody City city) {
        City savedCity = cityRepository.save(city);
        return new ResponseEntity<>(savedCity, HttpStatus.CREATED);
    }

    @PutMapping("/api/city/{id}")
    public ResponseEntity<City> update(@PathVariable int id, @RequestBody City city) {
        Optional<City> optionalCity = cityRepository.findById(id);
        if (optionalCity.isPresent()) {
            City existingCity = optionalCity.get();
            existingCity.setName(city.getName());
            cityRepository.save(existingCity);
            return new ResponseEntity<>(existingCity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/city/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
