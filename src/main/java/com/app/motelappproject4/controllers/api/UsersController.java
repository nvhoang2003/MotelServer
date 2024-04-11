package com.app.motelappproject4.controllers.api;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.app.motelappproject4.models.User;
import com.app.motelappproject4.models.UsersRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/api/users")
    public List<User> index() {
        return (List<User>) usersRepository.findAll();
    }

    @GetMapping("/api/users/{id}")
    public Optional<User> find(@PathVariable int id) {
        return usersRepository.findById(id);
    }

    @PutMapping("/api/users/{id}")
    public int update(@PathVariable int id, @RequestBody User updatedUser) {
        Optional<User> optionalUser = usersRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            // Update fields here
            // For example: existingUser.setUserName(updatedUser.getUserName());
            usersRepository.save(existingUser);
            return 1; // Success
        }
        return 0; // Failed to update
    }

    @DeleteMapping("/api/users/{id}")
    public int delete(@PathVariable int id) {
        if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);
            return 1; // Success
        }
        return 0; // Failed to delete
    }

    @GetMapping("/api/seed/users")
    public List<User> seedUsersData() {
        Faker faker = new Faker();
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUserName(faker.internet().username());
            user.setPassword(faker.internet().password());
            user.setEmail(faker.internet().emailAddress());
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setGender(faker.number().numberBetween(0, 2)); // Assuming 0: Unknown, 1: Male, 2: Female
            user.setBirthDate(faker.date().birthday());
            user.setAddress(faker.address().fullAddress());
            user.setPhone(faker.phoneNumber().phoneNumber());
            user.setCreateBy(faker.number().randomDigit());
            user.setUpdateBy(faker.number().randomDigit());
            user.setCreateDate(faker.date().past(365, TimeUnit.DAYS));
            user.setUpdateDate(faker.date().past(365, TimeUnit.DAYS));
            user.setIsDeleted(faker.number().numberBetween(0, 1)); // Assuming 0: Not Deleted, 1: Deleted
            user.setDescription(faker.lorem().sentence());
            usersRepository.save(user);
            list.add(user);
        }
        usersRepository.saveAll(list);
        return list;
    }

}
