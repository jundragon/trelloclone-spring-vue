package com.trelloclone.domain.model.cardlist.event;

import com.trelloclone.domain.common.event.DomainEvent;
import com.trelloclone.domain.model.cardlist.CardList;

public class CardListAddedEvent extends DomainEvent {

    private CardList cardList;

    public CardListAddedEvent(Object source, CardList cardList) {
        super(source);
        this.cardList = cardList;
    }

    public CardList getCardList() {
        return cardList;
    }
}
