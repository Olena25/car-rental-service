package com.intellias.rental.service;

import com.intellias.rental.db.UserRepository;
import com.intellias.rental.db.WalletRepository;
import com.intellias.rental.dto.user.UserRequest;
import com.intellias.rental.dto.user.UserResponse;
import com.intellias.rental.exception.UserAlreadyRegisteredException;
import com.intellias.rental.exception.UserNotFoundException;
import com.intellias.rental.mapper.UserMapper;
import com.intellias.rental.model.User;
import com.intellias.rental.model.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private WalletRepository walletRepository;
    private UserMapper userMapper;

    public UserResponse findUserById(int userId) {
        return userRepository.findById(userId)
                .map(userMapper::mapToUserResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    public void registerUser(UserRequest userRequest) {
        userRepository.findUserByPassportNumber(userRequest.getPassportNumber())
                .ifPresent(user -> {
                    throw new UserAlreadyRegisteredException();
                });

        User user = userMapper.mapToUser(userRequest);
        user = userRepository.save(user);

        walletRepository.save(createWallet(user));
    }

    private Wallet createWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);

        return wallet;
    }


}
