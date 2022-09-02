package com.intellias.rental.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@Entity(name = "wallet")
public class Wallet {
    @Id
    private int id;
    private int amount;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;
}
