package com.trelloclone.web.api;

import com.trelloclone.domain.application.*;
import com.trelloclone.domain.common.security.CurrentUser;
import com.trelloclone.domain.model.board.Board;
import com.trelloclone.domain.model.board.BoardId;
import com.trelloclone.domain.model.card.Card;
import com.trelloclone.domain.model.cardlist.CardList;
import com.trelloclone.domain.model.team.Team;
import com.trelloclone.domain.model.user.SimpleUser;
import com.trelloclone.domain.model.user.User;
import com.trelloclone.domain.model.user.exception.UserNotFoundException;
import com.trelloclone.web.payload.AddBoardMemberPayload;
import com.trelloclone.web.payload.CreateBoardPayload;
import com.trelloclone.web.result.ApiResult;
import com.trelloclone.web.result.BoardResult;
import com.trelloclone.web.result.CreateBoardResult;
import com.trelloclone.web.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final TeamService teamService;
    private final CardListService cardListService;
    private final CardService cardService;
    private final UserService userService;

    @PostMapping("/api/boards")
    public ResponseEntity<ApiResult> createBoard(@RequestBody CreateBoardPayload payload,
                                                 @CurrentUser SimpleUser currentUser) {
        Board board = boardService.createBoard(payload.toCommand(currentUser.getUserId()));
        return CreateBoardResult.build(board);
    }

    @GetMapping("/api/boards/{boardId}")
    public ResponseEntity<ApiResult> getBoard(@PathVariable("boardId") long rawBoardId) {
        BoardId boardId = new BoardId(rawBoardId);
        Board board = boardService.findById(boardId);
        if (board == null) {
            return Result.notFound();
        }
        List<User> members = boardService.findMembers(boardId);

        Team team = null;
        if (!board.isPersonal()) {
            team = teamService.findById(board.getTeamId());
        }

        List<CardList> cardLists = cardListService.findByBoardId(boardId);
        List<Card> cards = cardService.findByBoardId(boardId);

        return BoardResult.build(team, board, members, cardLists, cards);
    }

    @PostMapping("/api/boards/{boardId}/members")
    public ResponseEntity<ApiResult> addMember(@PathVariable("boardId") long rawBoardId,
                                               @RequestBody AddBoardMemberPayload payload) {
        BoardId boardId = new BoardId(rawBoardId);
        Board board = boardService.findById(boardId);
        if (board == null) {
            return Result.notFound();
        }

        try {
            User member = boardService.addMember(boardId, payload.getUsernameOrEmailAddress());

            ApiResult apiResult = ApiResult.blank()
                    .add("id", member.getId().value())
                    .add("shortName", member.getInitials());
            return Result.ok(apiResult);
        } catch (UserNotFoundException e) {
            return Result.failure("No user found");
        }
    }
}
