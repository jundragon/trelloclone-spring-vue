package com.trelloclone.domain.model.board.event;

import com.trelloclone.domain.common.event.DomainEvent;
import com.trelloclone.domain.model.board.Board;
import lombok.Getter;

@Getter
public class BoardCreatedEvent extends DomainEvent {

    private Board board;

    public BoardCreatedEvent(Object source, Board board) {
        super(source);
        this.board = board;
    }
}
