package com.example.booking_service.service;

import com.example.booking_service.entity.RoleType;
import com.example.booking_service.entity.User;
import com.example.booking_service.web.model.request.UpsertUserRequest;
import com.example.booking_service.web.model.response.UserListResponse;
import com.example.booking_service.web.model.response.UserResponse;
import org.springframework.stereotype.Component;


public interface UserService {

    UserListResponse findAll(int page, int size);

    UserResponse findById(Long id);

    User findByUsername(String username);

    UserResponse save(UpsertUserRequest request, RoleType roleType);

    UserResponse update(Long id, UpsertUserRequest request, RoleType roleType);

    void deleteById(Long id);

    boolean existsByUsernameAndEmail(String username, String email);
}
