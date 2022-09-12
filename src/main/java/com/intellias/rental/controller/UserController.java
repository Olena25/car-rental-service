package com.intellias.rental.controller;

import com.intellias.rental.dto.deposit.DepositRequest;
import com.intellias.rental.dto.deposit.DepositResponse;
import com.intellias.rental.dto.user.UserRequest;
import com.intellias.rental.dto.user.UserResponse;
import com.intellias.rental.service.DepositService;
import com.intellias.rental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private DepositService depositService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @GetMapping("/users/{user_id}")
    public UserResponse findUser(@PathVariable("user_id") int userId) {
        return userService.findUserById(userId);
    }

    @PostMapping("/users/{user_id}/deposit")
    public DepositResponse depositMoney(@PathVariable("user_id") int userId,
                                        @RequestBody @Valid DepositRequest depositRequest) {
       depositService.depositMoney(userId, depositRequest);

       return new DepositResponse("Money was successfully added to user with id " + userId);
    }

}
