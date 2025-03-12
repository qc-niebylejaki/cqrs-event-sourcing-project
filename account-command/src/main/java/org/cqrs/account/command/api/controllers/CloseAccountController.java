package org.cqrs.account.command.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.account.command.api.commands.CloseAccountCommand;
import org.cqrs.account.command.api.dto.OpenAccountResponse;
import org.cqrs.account.common.dto.BaseResponse;
import org.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/close-account")
@RequiredArgsConstructor
public class CloseAccountController {

    private final CommandDispatcher commandDispatcher;

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(value = "id") String id) {
        try {
            log.info("[Controller] Close account command for account id: {}", id);
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(
                    new OpenAccountResponse("Close account request completed successfully!", id),
                    HttpStatus.OK
            );
        } catch (IllegalStateException e) {
            log.warn("Client made a bad request: {}", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing request to close bank account for id = {0}", id);
            log.error(e.getMessage());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
