package org.cqrs.account.command.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cqrs.account.common.dto.BaseResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse extends BaseResponse {
    private String id;

    public AccountResponse(String message, String id) {
        super(message);
        this.id = id;
    }
}
