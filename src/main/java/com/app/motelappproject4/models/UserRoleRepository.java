// UserRoleRepository.java
package com.app.motelappproject4.models;

import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
    UserRole findUserRoleByUser(User u);
}
