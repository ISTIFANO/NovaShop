package com.example.novashop22.infrastructure.http.controller.internal;


import java.util.List;

import com.example.novashop22.domain.model.AddressDTO;
import com.example.novashop22.domain.service.IAddressService;
import com.example.novashop22.infrastructure.database.entities.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//
//import com.app.entites.Address;
//import com.app.payloads.AddressDTO;
//import com.app.services.AddressService;

//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Addresses", description = "Manage addresses for users and admins")
@RestController
@RequestMapping("/api/admin")
//@SecurityRequirement(name = "E-Commerce Application")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @Operation(summary = "Create address", description = "Create a new address")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Address created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/address")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO);

        return new ResponseEntity<AddressDTO>(savedAddressDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all addresses", description = "Retrieve all addresses (admin)")
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Addresses found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddresses() {
        List<AddressDTO> addressDTOs = addressService.getAddresses();

        return new ResponseEntity<List<AddressDTO>>(addressDTOs, HttpStatus.FOUND);
    }

    @Operation(summary = "Get address", description = "Get an address by id")
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Address found"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long addressId) {
        AddressDTO addressDTO = addressService.getAddress(addressId);

        return new ResponseEntity<AddressDTO>(addressDTO, HttpStatus.FOUND);
    }

    @Operation(summary = "Update address", description = "Update an existing address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address updated"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody Address address) {
        AddressDTO addressDTO = addressService.updateAddress(addressId, address);

        return new ResponseEntity<AddressDTO>(addressDTO, HttpStatus.OK);
    }

    @Operation(summary = "Delete address", description = "Delete an address by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address deleted"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
        String status = addressService.deleteAddress(addressId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }
}
