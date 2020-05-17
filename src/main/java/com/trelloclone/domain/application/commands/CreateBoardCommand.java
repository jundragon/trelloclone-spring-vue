package com.trelloclone.domain.application.commands;

import com.trelloclone.domain.model.team.TeamId;
import com.trelloclone.domain.model.user.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateBoardCommand {

    private final UserId userId;
    private final String name;
    private final String description;
    private final TeamId teamId;
}
