package org.cqrs.account.command.commands;

import lombok.Data;
import org.cqrs.core.commands.BaseCommand;

@Data
public class DepositFundsCommand extends BaseCommand {

    private double amount;
}
