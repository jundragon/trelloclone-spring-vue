package com.trelloclone.domain.application.impl;

import com.trelloclone.domain.application.BoardService;
import com.trelloclone.domain.application.commands.CreateBoardCommand;
import com.trelloclone.domain.common.event.DomainEventPublisher;
import com.trelloclone.domain.model.board.Board;
import com.trelloclone.domain.model.board.BoardManagement;
import com.trelloclone.domain.model.board.BoardRepository;
import com.trelloclone.domain.model.board.event.BoardCreatedEvent;
import com.trelloclone.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardManagement boardManagement;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public List<Board> findBoardsByMembership(UserId userId) {
        return boardRepository.findBoardsByMembership(userId);
    }

    @Override
    public Board createBoard(CreateBoardCommand command) {
        Board board = boardManagement.createBoard(command.getUserId(), command.getName(),
                command.getDescription(), command.getTeamId());
        domainEventPublisher.publish(new BoardCreatedEvent(this, board));
        return board;
    }
}
