package com.intellias.rental.service;

import com.intellias.rental.db.DepositCodeRepository;
import com.intellias.rental.db.TransactionRepository;
import com.intellias.rental.db.UserRepository;
import com.intellias.rental.db.WalletRepository;
import com.intellias.rental.dto.deposit.DepositRequest;
import com.intellias.rental.enums.TransactionType;
import com.intellias.rental.exception.DepositCodeNotFoundException;
import com.intellias.rental.exception.DepositLimitExceedException;
import com.intellias.rental.exception.UserNotFoundException;
import com.intellias.rental.model.DepositCode;
import com.intellias.rental.model.Transaction;
import com.intellias.rental.model.User;
import com.intellias.rental.model.Wallet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepositService {

    @Value("${car-rental.deposit-limit}")
    private Integer depositLimit;

    private final DepositCodeRepository depositCodeRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public void depositMoney(int userId, DepositRequest depositRequest) {
        if (depositRequest.getAmount() > depositLimit) {
            throw new DepositLimitExceedException("Limit for deposit is " + depositLimit);
        }
        log.info("Trying to deposit for user with id {} using {}", userId, depositRequest);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        DepositCode depositCode = depositCodeRepository.findByUserAndCode(user, depositRequest.getDepositCode())
                .orElseThrow(DepositCodeNotFoundException::new);

        log.info("Found deposit code {}", depositCode.getCode());

        Wallet wallet = user.getWallet();
        wallet.setAmount(depositRequest.getAmount());

        createNewTransaction(depositRequest.getAmount(), wallet, TransactionType.DEPOSIT);

        walletRepository.save(wallet);

        depositCode.setCode(UUID.randomUUID());
        depositCodeRepository.save(depositCode);
    }

    public void createNewTransaction(int amount, Wallet wallet, TransactionType type) {
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .wallet(wallet)
                .type(type)
                .build();

        log.info("Saving transaction to wallet with id {}", wallet.getId());

        transactionRepository.save(transaction);
    }

}
