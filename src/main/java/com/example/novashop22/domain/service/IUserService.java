package com.example.novashop22.domain.service;


import com.example.novashop22.domain.model.UserDTO;
import com.example.novashop22.domain.model.UserResponse;

public interface IUserService {

    UserDTO registerUser(UserDTO userDTO);

    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    UserDTO getUserById(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    String deleteUser(Long userId);
}