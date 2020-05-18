package com.trelloclone.domain.application.impl;

import com.trelloclone.domain.application.CardListService;
import com.trelloclone.domain.application.commands.AddCardListCommand;
import com.trelloclone.domain.application.commands.ChangeCardListPositionsCommand;
import com.trelloclone.domain.common.event.DomainEventPublisher;
import com.trelloclone.domain.model.board.BoardId;
import com.trelloclone.domain.model.cardlist.CardList;
import com.trelloclone.domain.model.cardlist.CardListRepository;
import com.trelloclone.domain.model.cardlist.event.CardListAddedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CardListServiceImpl implements CardListService {

    private CardListRepository cardListRepository;
    private DomainEventPublisher domainEventPublisher;

    public CardListServiceImpl(CardListRepository cardListRepository,
                               DomainEventPublisher domainEventPublisher) {
        this.cardListRepository = cardListRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public List<CardList> findByBoardId(BoardId boardId) {
        return cardListRepository.findByBoardId(boardId);
    }

    @Override
    public CardList addCardList(AddCardListCommand command) {
        CardList cardList = CardList.create(command.getBoardId(),
                command.getUserId(), command.getName(), command.getPosition());

        cardListRepository.save(cardList);
        domainEventPublisher.publish(new CardListAddedEvent(this, cardList));
        return cardList;
    }

    @Override
    public void changePositions(ChangeCardListPositionsCommand command) {
        cardListRepository.changePositions(command.getCardListPositions());
    }
}
