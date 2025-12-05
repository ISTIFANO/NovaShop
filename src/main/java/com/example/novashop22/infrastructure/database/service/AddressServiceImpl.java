package com.example.novashop22.infrastructure.database.service;


import com.example.novashop22.commun.exception.APIException;
import com.example.novashop22.commun.exception.ResourceNotFoundException;
import com.example.novashop22.domain.model.AddressDTO;
import com.example.novashop22.domain.service.IAddressService;
import com.example.novashop22.infrastructure.database.entities.Address;
import com.example.novashop22.infrastructure.database.entities.User;
import com.example.novashop22.infrastructure.database.repositories.AddressRepo;
import com.example.novashop22.infrastructure.database.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {

        String country = addressDTO.getCountry();
        String state = addressDTO.getState();
        String city = addressDTO.getCity();
        String pincode = addressDTO.getPincode();
        String street = addressDTO.getStreet();
        String buildingName = addressDTO.getBuildingName();

        Address addressFromDB = addressRepo.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country,
                state, city, pincode, street, buildingName);

        if (addressFromDB != null) {
            throw new APIException("Address already exists with addressId: " + addressFromDB.getAddressId());
        }

        Address address = modelMapper.map(addressDTO, Address.class);

        Address savedAddress = addressRepo.save(address);

        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddresses() {
        List<Address> addresses = addressRepo.findAll();

        List<AddressDTO> addressDTOs = addresses.stream().map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());

        return addressDTOs;
    }

    @Override
    public AddressDTO getAddress(Long addressId) {
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, Address address) {
        Address addressFromDB = addressRepo.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(
                address.getCountry(), address.getState(), address.getCity(), address.getPincode(), address.getStreet(),
                address.getBuildingName());

        if (addressFromDB == null) {
            addressFromDB = addressRepo.findById(addressId)
                    .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

            addressFromDB.setCountry(address.getCountry());
            addressFromDB.setState(address.getState());
            addressFromDB.setCity(address.getCity());
            addressFromDB.setPincode(address.getPincode());
            addressFromDB.setStreet(address.getStreet());
            addressFromDB.setBuildingName(address.getBuildingName());

            Address updatedAddress = addressRepo.save(addressFromDB);

            return modelMapper.map(updatedAddress, AddressDTO.class);
        } else {
            List<User> users = userRepo.findUsersByAddressId(addressId);
            final Address a = addressFromDB;

            users.forEach(user -> user.getAddresses().add(a));

            deleteAddress(addressId);

            return modelMapper.map(addressFromDB, AddressDTO.class);
        }
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address addressFromDB = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        List<User> users = userRepo.findUsersByAddressId(addressId);

        users.forEach(user -> {
            user.getAddresses().remove(addressFromDB);

            userRepo.save(user);
        });

        addressRepo.deleteById(addressId);

        return "Address deleted succesfully with addressId: " + addressId;
    }
}