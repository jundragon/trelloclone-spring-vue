package com.trelloclone.web.result;

import com.trelloclone.domain.model.board.Board;
import com.trelloclone.domain.model.team.Team;
import com.trelloclone.domain.model.user.SimpleUser;
import com.trelloclone.domain.model.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDataResult {

    public static ResponseEntity<ApiResult> build(User user, List<Team> teams, List<Board> boards) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", user.getFirstName() + " " + user.getLastName());

        List<TeamResult> teamResults = new ArrayList<>();
        for (Team team : teams) {
            teamResults.add(new TeamResult(team));
        }

        List<BoardResult> boardResults = new ArrayList<>();
        for (Board board : boards) {
            boardResults.add(new BoardResult(board));
        }

        ApiResult apiResult = ApiResult.blank()
                .add("user", userData)
                .add("teams", teamResults)
                .add("boards", boardResults);

        return Result.ok(apiResult);
    }

    @Getter
    private static class TeamResult {
        private long id;
        private String name;

        TeamResult(Team team) {
            this.id = team.getId();
            this.name = team.getName();
        }
    }

    @Getter
    private static class BoardResult {
        private long id;
        private String name;
        private String description;
        private long teamId;

        BoardResult(Board board) {
            this.id = board.getId().value();
            this.name = board.getName();
            this.description = board.getDescription();
            this.teamId = board.getTeamId().value();
        }
    }
}
