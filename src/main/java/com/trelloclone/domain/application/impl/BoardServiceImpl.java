package com.trelloclone.domain.application.impl;

import com.trelloclone.domain.application.BoardService;
import com.trelloclone.domain.application.commands.CreateBoardCommand;
import com.trelloclone.domain.common.event.DomainEventPublisher;
import com.trelloclone.domain.model.board.*;
import com.trelloclone.domain.model.board.event.BoardCreatedEvent;
import com.trelloclone.domain.model.board.event.BoardMemberAddedEvent;
import com.trelloclone.domain.model.user.User;
import com.trelloclone.domain.model.user.UserFinder;
import com.trelloclone.domain.model.user.UserId;
import com.trelloclone.domain.model.user.exception.UserNotFoundException;
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
    private final BoardMemberRepository boardMemberRepository;
    private final UserFinder userFinder;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public List<Board> findBoardsByMembership(UserId userId) {
        return boardRepository.findBoardsByMembership(userId);
    }

    @Override
    public Board findById(BoardId boardId) {
        return boardRepository.findById(boardId);
    }

    @Override
    public List<User> findMembers(BoardId boardId) {
        return boardMemberRepository.findMembers(boardId);
    }

    @Override
    public Board createBoard(CreateBoardCommand command) {
        Board board = boardManagement.createBoard(command.getUserId(), command.getName(),
                command.getDescription(), command.getTeamId());
        domainEventPublisher.publish(new BoardCreatedEvent(this, board));
        return board;
    }

    @Override
    public User addMember(BoardId boardId, String usernameOrEmailAddress) throws UserNotFoundException {
        User user = userFinder.find(usernameOrEmailAddress);
        boardMemberRepository.add(boardId, user.getId());
        domainEventPublisher.publish(new BoardMemberAddedEvent(this, boardId, user));
        return user;
    }
}
