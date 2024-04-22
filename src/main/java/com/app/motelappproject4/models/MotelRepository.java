// MotelRepository.java
package com.app.motelappproject4.models;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MotelRepository extends CrudRepository<Motel, Integer> {
    List<Motel> getMotelsByCreatedBy_Id(int createdById);
}