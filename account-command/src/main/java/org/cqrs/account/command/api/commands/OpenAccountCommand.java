package org.cqrs.account.command.api.commands;

import lombok.Data;
import org.cqrs.account.common.dto.AccountType;
import org.cqrs.core.commands.BaseCommand;

@Data
public class OpenAccountCommand extends BaseCommand {

    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
