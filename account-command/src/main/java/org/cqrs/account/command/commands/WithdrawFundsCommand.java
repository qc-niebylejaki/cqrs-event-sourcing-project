package org.cqrs.account.command.commands;

import lombok.Data;
import org.cqrs.core.commands.BaseCommand;

@Data
public class WithdrawFundsCommand extends BaseCommand {

    private double amount;
}
