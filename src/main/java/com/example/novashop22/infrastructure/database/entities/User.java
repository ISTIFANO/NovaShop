package com.example.novashop22.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @Column(name = "firstname")
   private String firstname;
   @Column(name = "lastname")
   private String lastname;


   @Column(name = "email" ,unique = true, nullable = false)
   private String email;
   @Column(name = "password")
   private String password;

   @Column(name = "phone")
   private String phone;
@ManyToMany(cascade = {CascadeType.PERSIST ,CascadeType.MERGE} ,fetch = FetchType.EAGER)

@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
private Set<Role> roles = new HashSet<>();

   @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
   @JoinTable(
           name = "user_address",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "address_id")
   )
   private List<Address> addresses = new ArrayList<>();


   @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
   private Cart cart;

}
