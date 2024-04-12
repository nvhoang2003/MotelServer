// DistrictsRepository.java
package com.app.motelappproject4.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DistrictsRepository extends CrudRepository<District, Integer> {
    @Query("SELECT d FROM District d WHERE (:cityId is null or d.city.id = :cityId)")
    List<District> getListDistrictByCityId(@Param("cityId") Integer cityId);

    @Query("SELECT d FROM District d")
    List<District> getListDistrictByCityId();
}
