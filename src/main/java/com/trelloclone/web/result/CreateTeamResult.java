package com.trelloclone.web.result;

import com.trelloclone.domain.model.team.Team;
import org.springframework.http.ResponseEntity;

public class CreateTeamResult {

    public static ResponseEntity<ApiResult> build(Team team) {
        ApiResult apiResult = ApiResult.blank()
                .add("id", team.getId())
                .add("name", team.getName());
        return Result.ok(apiResult);
    }
}
