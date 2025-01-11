package com.example.booking_service.web.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertUserRequest {

    @NotBlank(message = "User's name cannot be empty")
    @Size(min = 3, message = "Min size: {min}")
    private String username;

    @Email
    private String email;

    private String password;
}
