package com.example.booking_service.mapper;


import com.example.booking_service.entity.User;
import com.example.booking_service.web.model.request.UpsertUserRequest;
import com.example.booking_service.web.model.response.UserListResponse;
import com.example.booking_service.web.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UpsertUserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UpsertUserRequest request);

    UserResponse userToResponse(User user);

    User responseToUSer(UserResponse userResponse);

    default UserListResponse userListToUserListResponse(Page<User> userPage) {
        UserListResponse response = new UserListResponse();
        response.setUserResponseList(userPage.getContent().stream()
                .map(this::userToResponse)
                .toList());

        response.setTotalElements(userPage.getTotalElements());
        response.setTotalPages(userPage.getTotalPages());
        response.setCurrentPage(userPage.getNumber());
        response.setPageSize(userPage.getSize());
        return response;
    }
}
