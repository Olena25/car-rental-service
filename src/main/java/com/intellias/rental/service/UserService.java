package com.intellias.rental.service;

import com.intellias.rental.db.UserRepository;
import com.intellias.rental.db.WalletRepository;
import com.intellias.rental.dto.AgeResponse;
import com.intellias.rental.dto.user.UserRequest;
import com.intellias.rental.dto.user.UserResponse;
import com.intellias.rental.exception.EmailIsAlreadyExistException;
import com.intellias.rental.exception.UserAgeNotFoundException;
import com.intellias.rental.exception.UserAlreadyRegisteredException;
import com.intellias.rental.exception.UserNotFoundException;
import com.intellias.rental.mapper.UserMapper;
import com.intellias.rental.model.User;
import com.intellias.rental.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private WalletRepository walletRepository;
    private UserMapper userMapper;
    private AgeApiService ageApiService;
    private MailApiService mailApiService;

    public UserResponse findUserById(int userId) {
        return userRepository.findById(userId)
                .map(userMapper::mapToUserResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserResponse registerUser(UserRequest userRequest) {
        userRepository.findUserByPassportNumber(userRequest.getPassportNumber())
                .ifPresent(user -> {
                    throw new UserAlreadyRegisteredException();
                });

        if (userRepository.countByEmail(userRequest.getEmail()) > 0) {
            log.info("Email {} is already registered", userRequest.getEmail());
            throw new EmailIsAlreadyExistException();
        }

        User user = userMapper.mapToUser(userRequest);

        if (user.getDob() == null) {
            log.info("For user with first name {} dob was not presented", user.getFirstName());
            AgeResponse ageResponse = ageApiService.getUserAge(userRequest.getFirstName());

            if (ageResponse == null || ageResponse.getAge() == null) {
                throw new UserAgeNotFoundException();
            }

            user.setAge(ageResponse.getAge());
        } else {
            int age = Period.between(user.getDob(), LocalDate.now()).getYears();
            user.setAge(age);
        }

        userRepository.save(user);
        mailApiService.sendEmailToConfirm(user.getId());

        userRepository.flush();

        walletRepository.save(createWallet(user));

        return userMapper.mapToUserResponse(user);
    }

    private Wallet createWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);

        return wallet;
    }


}
