package org.cqrs.account.command.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.account.command.api.commands.OpenAccountCommand;
import org.cqrs.account.command.api.dto.OpenAccountResponse;
import org.cqrs.account.common.dto.BaseResponse;
import org.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/open-bank-account")
@RequiredArgsConstructor
public class OpenAccountController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(
                    new OpenAccountResponse("Bank account creation request completed successfully", id),
                    HttpStatus.CREATED
            );
        } catch (IllegalStateException e) {
            log.warn("Client made a bad request: {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing request to open a new bank account for id = {0}", id);
            log.error(e.getMessage());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
