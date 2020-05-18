package com.trelloclone.domain.model.card.event;

import com.trelloclone.domain.common.event.DomainEvent;
import com.trelloclone.domain.model.card.Card;

public class CardAddedEvent extends DomainEvent {

    private Card card;

    public CardAddedEvent(Object source, Card card) {
        super(source);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
