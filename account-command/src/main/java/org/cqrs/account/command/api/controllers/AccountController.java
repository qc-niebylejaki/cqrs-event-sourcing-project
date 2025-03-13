package org.cqrs.account.command.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.account.command.api.commands.*;
import org.cqrs.account.command.api.dto.AccountResponse;
import org.cqrs.account.common.dto.BaseResponse;
import org.cqrs.core.commands.BaseCommand;
import org.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class AccountController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping("/account")
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        String id = UUID.randomUUID().toString();
        command.setId(id);
        var message = "Bank account creation request completed successfully";
        return handleCommand(command, message, HttpStatus.CREATED);
    }

    @PutMapping("/account/{id}/deposit")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable String id, @RequestBody DepositFundsCommand command) {
        command.setId(id);
        var message = "Deposit funds request completed successfully";
        return handleCommand(command, message, HttpStatus.OK);
    }

    @PutMapping("/account/{id}/withdraw")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable String id, @RequestBody WithdrawFundsCommand command) {
        command.setId(id);
        var message = "Withdraw funds request completed successfully";
        return handleCommand(command, message, HttpStatus.OK);
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable String id) {
        var message = "Account closed request completed successfully";
        return handleCommand(new CloseAccountCommand(id), message, HttpStatus.OK
        );
    }

    @PostMapping("/restore")
    public ResponseEntity<BaseResponse> restoreReadDb(@RequestBody RestoreReadDbCommand command) {
        var message = "Restore read DB request completed successfully";
        return handleCommand(command, message, HttpStatus.OK);
    }

    private ResponseEntity<BaseResponse> handleCommand(BaseCommand command, String responseMessage, HttpStatus status) {
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new AccountResponse(responseMessage, command.getId()), status);
        } catch (IllegalStateException e) {
            log.warn("Client made a bad request: {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            String errorMessage = (command.getId() != null)
                    ? MessageFormat.format("Error while processing request for account id = {0}", command.getId())
                    : "Error while processing request";
            log.error(errorMessage, e);
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
