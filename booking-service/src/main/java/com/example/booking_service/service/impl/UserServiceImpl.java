package com.example.booking_service.service.impl;

import com.example.booking_service.entity.RoleType;
import com.example.booking_service.entity.User;
import com.example.booking_service.exception.EntityNotFoundException;
import com.example.booking_service.mapper.UserMapper;
import com.example.booking_service.repository.UserRepository;
import com.example.booking_service.service.UserService;
import com.example.booking_service.utils.BeanUtils;
import com.example.booking_service.web.model.request.UpsertUserRequest;
import com.example.booking_service.web.model.response.UserListResponse;
import com.example.booking_service.web.model.response.UserResponse;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserListResponse findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return userMapper.userListToUserListResponse(userRepository.findAll(pageable));
    }

    @Override
    public UserResponse findById(Long id) {
        return userMapper.userToResponse(userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("User with ID:{0} not found!", id))));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found" + username));
    }

    @Override
    public UserResponse save(UpsertUserRequest request, RoleType roleType) {
        if (existsByUsernameAndEmail(request.getUsername(), request.getEmail())) {
            throw new EntityExistsException(MessageFormat.format("Username {0} or email {1} already exists",
                    request.getUsername(), request.getEmail()));
        }

        User user = userMapper.requestToUser(request);
        user.setRole(roleType);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.userToResponse(savedUser);
    }

    @Override
    public UserResponse update(Long id, UpsertUserRequest request, RoleType roleType) {
        User existedUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("User with ID {0} not found", id)));

        User updatedUser = userMapper.requestToUser(id, request);

        if (updatedUser.getPassword() != null) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        BeanUtils.copyUserNotNullProperties(updatedUser, existedUser);

        if (roleType != null) {
            existedUser.setRole(roleType);
        }

        return userMapper.userToResponse(userRepository.save(existedUser));
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsernameAndEmail(String username, String email) {
        return userRepository.existsByUsernameAndEmail(username, email);
    }
}
