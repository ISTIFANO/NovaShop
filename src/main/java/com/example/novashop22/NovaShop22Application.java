package com.example.novashop22;

import com.example.novashop22.commun.payload.AppConstants;
import com.example.novashop22.infrastructure.database.entities.Role;
import com.example.novashop22.infrastructure.database.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class NovaShop22Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(NovaShop22Application.class, args);
    }
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
        try {
            Role adminRole = new Role();
            adminRole.setId(AppConstants.ADMIN_ID);
            adminRole.setRole("ADMIN");

            Role userRole = new Role();
            userRole.setId(AppConstants.USER_ID);
            userRole.setRole("USER");

            List<Role> roles = List.of(adminRole, userRole);

            List<Role> savedRoles = roleRepo.saveAll(roles);

            savedRoles.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
