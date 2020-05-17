package com.trelloclone.domain.application.impl;

import com.trelloclone.domain.application.TeamService;
import com.trelloclone.domain.application.commands.CreateTeamCommand;
import com.trelloclone.domain.common.event.DomainEventPublisher;
import com.trelloclone.domain.model.team.Team;
import com.trelloclone.domain.model.team.TeamRepository;
import com.trelloclone.domain.model.team.event.TeamCreatedEvent;
import com.trelloclone.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamServiceImple implements TeamService {
    private final TeamRepository teamRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public List<Team> findTeamsByUserId(UserId userId) {
        return teamRepository.findTeamsByUserId(userId);
    }

    @Override
    public Team createTeam(CreateTeamCommand command) {
        Team team = Team.create(command.getName(), command.getUserId());
        teamRepository.save(team);
        domainEventPublisher.publish(new TeamCreatedEvent(this, team));
        return team;
    }
}
