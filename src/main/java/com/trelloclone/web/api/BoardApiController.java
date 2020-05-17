package com.trelloclone.web.api;

import com.trelloclone.domain.application.BoardService;
import com.trelloclone.domain.common.security.CurrentUser;
import com.trelloclone.domain.model.board.Board;
import com.trelloclone.domain.model.user.SimpleUser;
import com.trelloclone.web.payload.CreateBoardPayload;
import com.trelloclone.web.result.ApiResult;
import com.trelloclone.web.result.CreateBoardResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/api/boards")
    public ResponseEntity<ApiResult> createBoard(@RequestBody CreateBoardPayload payload,
                                                 @CurrentUser SimpleUser currentUser) {
        Board board = boardService.createBoard(payload.toCommand(currentUser.getUserId()));
        return CreateBoardResult.build(board);
    }
}
