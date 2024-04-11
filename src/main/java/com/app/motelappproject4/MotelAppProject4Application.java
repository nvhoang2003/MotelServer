package com.app.motelappproject4;

import com.app.motelappproject4.models.User;
import com.app.motelappproject4.models.UserRole;
import com.app.motelappproject4.models.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class MotelAppProject4Application {
    public static void main(String[] args) {
        SpringApplication.run(MotelAppProject4Application.class, args);
    }
}

