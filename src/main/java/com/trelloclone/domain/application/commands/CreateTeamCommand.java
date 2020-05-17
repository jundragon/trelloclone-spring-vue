package com.trelloclone.domain.application.commands;

import com.trelloclone.domain.model.user.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateTeamCommand {

    private final UserId userId;
    private final String name;
}
