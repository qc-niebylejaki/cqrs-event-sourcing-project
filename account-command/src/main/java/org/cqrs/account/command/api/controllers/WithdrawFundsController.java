package org.cqrs.account.command.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.account.command.api.commands.WithdrawFundsCommand;
import org.cqrs.account.command.api.dto.OpenAccountResponse;
import org.cqrs.account.common.dto.BaseResponse;
import org.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/withdraw-funds")
@RequiredArgsConstructor
public class WithdrawFundsController {

    private final CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable(value = "id") String id,
                                                    @RequestBody WithdrawFundsCommand command) {
        try {
            log.info("[Controller] Withdraw funds command: {}", command.toString());
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(
                    new OpenAccountResponse("Withdraw funds request completed successfully!", id),
                    HttpStatus.OK
            );
        } catch (IllegalStateException e) {
            log.warn("Client made a bad request: {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing request to withdraw funds from bank account for id = {0}", id);
            log.error(e.getMessage());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
