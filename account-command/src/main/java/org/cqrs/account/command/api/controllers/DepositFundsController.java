package org.cqrs.account.command.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.account.command.api.commands.DepositFundsCommand;
import org.cqrs.account.command.api.dto.OpenAccountResponse;
import org.cqrs.account.common.dto.BaseResponse;
import org.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/deposit-funds")
@RequiredArgsConstructor
public class DepositFundsController {

    private final CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id,
                                                     @RequestBody DepositFundsCommand command) {
        try {
            log.info("[Controller] Deposit funds command: {}", command.toString());
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(
                    new OpenAccountResponse("Deposit funds request completed successfully!", id),
                    HttpStatus.OK
            );
        } catch (IllegalStateException e) {
            log.warn("Client made a bad request: {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing request to deposit funds to bank account for id = {0}", id);
            log.error(e.getMessage());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
