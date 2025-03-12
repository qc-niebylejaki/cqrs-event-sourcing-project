package org.cqrs.account.query.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.cqrs.account.common.dto.AccountType;
import org.cqrs.core.domain.BaseEntity;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount extends BaseEntity {

    @Id
    private String id;
    private String accountHolder;
    private LocalDate creationDate;
    private AccountType accountType;
    private double balance;
}
