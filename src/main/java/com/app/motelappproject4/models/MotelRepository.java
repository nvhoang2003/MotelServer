// MotelRepository.java
package com.app.motelappproject4.models;

import org.springframework.data.repository.CrudRepository;

public interface MotelRepository extends CrudRepository<Motel, Integer> {
    Motel findMotelByUser(User u);
}