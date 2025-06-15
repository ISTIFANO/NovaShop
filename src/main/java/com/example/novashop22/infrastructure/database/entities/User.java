package com.example.novashop22.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @Column(name = "firstname")
   private String firstname;
   @Column(name = "lastname")
   private String lastname;


   @Column(name = "email")
   private String email;
   @Column(name = "password")
   private String password;

   @Column(name = "phone")
   private String phone;

}
