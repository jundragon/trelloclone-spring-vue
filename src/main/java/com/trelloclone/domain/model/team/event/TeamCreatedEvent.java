package com.trelloclone.domain.model.team.event;

import com.trelloclone.domain.common.event.DomainEvent;
import com.trelloclone.domain.model.team.Team;
import lombok.Getter;

@Getter
public class TeamCreatedEvent extends DomainEvent {

    private Team team;

    public TeamCreatedEvent(Object source, Team team) {
        super(source);
        this.team = team;
    }
}
