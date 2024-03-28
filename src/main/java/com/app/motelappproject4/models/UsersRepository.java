package com.app.motelappproject4.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {
     static User findUserByEmail(String email){

        User user = new User();
        user.setEmail(email);
        user.setPassword("123456");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        return user;
    }
}
