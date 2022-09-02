package com.intellias.rental.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
@Entity(name = "deposit_code")
public class DepositCode {
    @Id
    private int id;
    @OneToOne
    private User user;
    @Type(type = "uuid-char")
    private UUID code;
}
