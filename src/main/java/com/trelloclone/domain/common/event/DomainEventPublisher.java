package com.trelloclone.domain.common.event;

public interface DomainEventPublisher {

    /**
     * Publish a domain event
     */
    void publish(DomainEvent event);
}
