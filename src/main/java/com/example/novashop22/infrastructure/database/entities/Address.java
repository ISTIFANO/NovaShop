package com.example.novashop22.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;


    @Column(nullable = false)
    private String street;


    @Column(name = "building_name", nullable = false)
    private String buildingName;


    @Column(nullable = false)
    private String city;


    @Column(nullable = false)
    private String state;


    @Column(nullable = false)
    private String country;


    @Column(nullable = false)
    private String pincode;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(String country, String state, String city, String pincode, String street, String buildingName) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
        this.street = street;
        this.buildingName = buildingName;
    }
}

