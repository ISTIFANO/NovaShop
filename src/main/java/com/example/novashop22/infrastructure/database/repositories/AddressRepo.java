package com.example.novashop22.infrastructure.database.repositories;

import com.example.novashop22.infrastructure.database.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
    Address findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(String country, String state, String city,String pincode, String street, String buildingName);
}
