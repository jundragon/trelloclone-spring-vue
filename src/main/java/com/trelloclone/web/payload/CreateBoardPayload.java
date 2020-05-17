package com.trelloclone.web.payload;

import com.trelloclone.domain.application.commands.CreateBoardCommand;
import com.trelloclone.domain.model.team.TeamId;
import com.trelloclone.domain.model.user.UserId;
import lombok.Setter;

@Setter
public class CreateBoardPayload {

    private String name;
    private String description;
    private long teamId;

    public CreateBoardCommand toCommand(UserId userId) {
        return new CreateBoardCommand(userId, name, description, new TeamId(teamId));
    }
}
