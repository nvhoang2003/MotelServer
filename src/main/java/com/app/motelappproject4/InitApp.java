package com.app.motelappproject4;

import com.app.motelappproject4.models.User;
import com.app.motelappproject4.models.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InitApp {
    @Autowired
    private UsersRepository usersRepository;
    public void adminUserInit() {
        User adminUser = new User();
        adminUser.setId(1); // Normally, this would be set by the database automatically
        adminUser.setUserName("admin");
        adminUser.setPassword("admin");
        adminUser.setEmail("admin@gmail.com");
        adminUser.setFirstName("ADMIN");
        adminUser.setLastName("ADMIN");
        adminUser.setGender(0); // Assuming 0 is unspecified, 1 for male, 2 for female
        adminUser.setBirthDate(new Date()); // Sets the birthDate to the current date
        adminUser.setAddress("Admin");
        adminUser.setPhone("Admin");
        adminUser.setCreateBy(1); // Assuming this is the ID of the admin or system user who created this fake user
        adminUser.setUpdateBy(1); // Assuming this is the ID of the admin or system user who last updated this fake user
        adminUser.setCreateDate(new Date()); // Sets the createDate to the current date
        adminUser.setUpdateDate(new Date()); // Sets the updateDate to the current date
        adminUser.setIsDeleted(0); // Assuming 0 means not deleted
        adminUser.setDescription("Init Admin");
        if (usersRepository.findUserByEmail(adminUser.getEmail()) == null) {
            usersRepository.save(adminUser);
        }
    }
}
