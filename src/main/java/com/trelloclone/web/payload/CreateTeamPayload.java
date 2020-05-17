package com.trelloclone.web.payload;

import com.trelloclone.domain.application.commands.CreateTeamCommand;
import com.trelloclone.domain.model.user.UserId;
import lombok.Setter;

@Setter
public class CreateTeamPayload {

    private String name;

    public CreateTeamCommand toCommand(UserId userId) {
        return new CreateTeamCommand(userId, name);
    }
}
