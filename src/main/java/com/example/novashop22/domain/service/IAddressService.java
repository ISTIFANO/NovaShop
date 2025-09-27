package com.example.novashop22.domain.service;

import com.example.novashop22.domain.model.AddressDTO;
import com.example.novashop22.infrastructure.database.entities.Address;

import java.util.List;


public interface IAddressService {

    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAddresses();

    AddressDTO getAddress(Long addressId);

    AddressDTO updateAddress(Long addressId, Address address);

    String deleteAddress(Long addressId);
}
