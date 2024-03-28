package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.City;
import com.app.motelappproject4.models.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CityController {
    @Autowired
    CityRepository cityRepository;

    @GetMapping("/api/city")
    public List<City> index() {
        return (List<City>) cityRepository.findAll();
    }

    @GetMapping("/api/city/{id}")
    public Optional<City> find(@PathVariable int id) {
        return cityRepository.findById(id);
    }

    @PostMapping("/api/city")
    public City create(@RequestBody City city) {
        return cityRepository.save(city);
    }

    @PutMapping("/api/city/{id}")
    public int update(@PathVariable int id, @RequestBody City city) {
        Optional<City> optionalCity = cityRepository.findById(id);
        if (optionalCity.isPresent()) {
            City updatedCity = optionalCity.get();
            updatedCity.setName(city.getName());
            cityRepository.save(updatedCity);
            return 1;
        }
        return 0;
    }

    @DeleteMapping("/api/city/{id}")
    public int delete(@PathVariable int id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return 1;
        }
        return 0;
    }
}
