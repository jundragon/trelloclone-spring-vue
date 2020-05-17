package com.trelloclone.web.api;

import com.trelloclone.domain.application.BoardService;
import com.trelloclone.domain.application.TeamService;
import com.trelloclone.domain.common.security.CurrentUser;
import com.trelloclone.domain.model.board.Board;
import com.trelloclone.domain.model.team.Team;
import com.trelloclone.domain.model.user.SimpleUser;
import com.trelloclone.web.result.ApiResult;
import com.trelloclone.web.result.MyDataResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MeApiController {

    private final TeamService teamService;
    private final BoardService boardService;

    @GetMapping("/api/me")
    public ResponseEntity<ApiResult> getMyData(@CurrentUser SimpleUser currentUser) {
        List<Team> teams = teamService.findTeamsByUserId(currentUser.getUserId());
        List<Board> boards = boardService.findBoardsByMembership(currentUser.getUserId());
        return MyDataResult.build(currentUser, teams, boards);
    }
}
