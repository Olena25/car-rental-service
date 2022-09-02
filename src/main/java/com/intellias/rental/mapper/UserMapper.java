package com.intellias.rental.mapper;

import com.intellias.rental.dto.user.UserRequest;
import com.intellias.rental.dto.user.UserResponse;
import com.intellias.rental.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToUser(UserRequest userRequest);

    @Mapping(target = "walletAmount", source = "wallet.amount")
    UserResponse mapToUserResponse(User user);

}
