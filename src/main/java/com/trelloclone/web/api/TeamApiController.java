package com.trelloclone.web.api;

import com.trelloclone.domain.application.TeamService;
import com.trelloclone.domain.common.security.CurrentUser;
import com.trelloclone.domain.model.team.Team;
import com.trelloclone.domain.model.user.SimpleUser;
import com.trelloclone.web.payload.CreateTeamPayload;
import com.trelloclone.web.result.ApiResult;
import com.trelloclone.web.result.CreateTeamResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class TeamApiController {

    private final TeamService teamService;

    @PostMapping("/api/teams")
    public ResponseEntity<ApiResult> createTeam(@RequestBody CreateTeamPayload payload,
                                                @CurrentUser SimpleUser currentUser) {
        Team team = teamService.createTeam(payload.toCommand(currentUser.getUserId()));
        return CreateTeamResult.build(team);
    }
}
