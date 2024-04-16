package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.dtos.DistrictCreateDTO;
import com.app.motelappproject4.models.City;
import com.app.motelappproject4.models.CityRepository;
import com.app.motelappproject4.models.District;
import com.app.motelappproject4.models.DistrictsRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class DistrictsController {
    @Autowired
    DistrictsRepository districtsRepository;

    @Autowired
    CityRepository cityRepository;

    @GetMapping("/api/districts/getList/{cityId}")
    public List<District> index(@PathVariable Integer cityId) {
        List<District> listDistrict = new ArrayList<District>();
        if(cityId == null){
            listDistrict = (List<District>) districtsRepository.findAll();
        }else{
            listDistrict = (List<District>) districtsRepository.getListDistrictByCityId(cityId);
        }
        return listDistrict;
    }

    @GetMapping("/api/districts/getById/{id}")
    public Optional<District> find(@PathVariable int id) {
        return districtsRepository.findById(id);
    }

    @PostMapping("/api/districts")
    public District create(@RequestBody DistrictCreateDTO district) {
        District disAdd = new District();

        disAdd.setName(district.getName());

        City city = cityRepository.findById(district.getCity_id()).get();
        disAdd.setCity(city);
        return districtsRepository.save(disAdd);
    }

    @PutMapping("/api/districts/{id}")
    public int update(@PathVariable int id, @RequestBody District updatedDistrict) {
        Optional<District> optionalDistricts = districtsRepository.findById(id);
        if (optionalDistricts.isPresent()) {
            District existingDistrict = optionalDistricts.get();
            existingDistrict.setName(updatedDistrict.getName());
            // Assuming you might also want to update the city relation
            districtsRepository.save(existingDistrict);
            return 1; // Success
        }
        return 0; // Failed to update
    }

    @DeleteMapping("/api/districts/{id}")
    public int delete(@PathVariable int id) {
        if (districtsRepository.existsById(id)) {
            districtsRepository.deleteById(id);
            return 1; // Success
        }
        return 0; // Failed to delete
    }
    @GetMapping("/api/seed/districts")
    public String seedDistrictsData() {
        Faker faker = new Faker();
        List<City> cities = (List<City>) cityRepository.findAll(); // Giả sử bạn đã có cityRepository
        if (cities.isEmpty()) {
            return "Please seed cities first.";
        }
        for (int i = 0; i < 10; i++) {
            District district = new District();
            district.setName(faker.address().cityName());
            // Lấy ngẫu nhiên một City từ danh sách
            City city = cities.get(faker.number().numberBetween(0, cities.size()));
            district.setCity(city); // Thiết lập mối quan hệ với City
            districtsRepository.save(district);
        }
        return "Seeded districts data with city associations";
    }

    // Test xem hàm đã hoạt động chuẩn chưa
    @GetMapping("/rest/auth/api/districts/city/{cityId}")
    public List<District> getDistrictsByCityId(@PathVariable int cityId) {
        return districtsRepository.getListDistrictByCityId(cityId);
    }
    @GetMapping("/rest/auth/api/districts/city")
    public List<District> getDistrictsByCityId() {
        return districtsRepository.getListDistrictByCityId();
    }
}
