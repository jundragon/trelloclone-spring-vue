package com.trelloclone.web.payload;

import com.trelloclone.domain.application.commands.ChangeCardListPositionsCommand;
import com.trelloclone.domain.model.board.BoardId;
import com.trelloclone.domain.model.cardlist.CardListPosition;

import java.util.List;

public class ChangeCardListPositionsPayload {

    private long boardId;
    private List<CardListPosition> cardListPositions;

    public ChangeCardListPositionsCommand toCommand() {
        return new ChangeCardListPositionsCommand(new BoardId(boardId), cardListPositions);
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public void setCardListPositions(List<CardListPosition> cardListPositions) {
        this.cardListPositions = cardListPositions;
    }
}
