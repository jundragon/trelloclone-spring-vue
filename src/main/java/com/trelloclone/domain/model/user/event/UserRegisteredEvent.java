package com.trelloclone.domain.model.user.event;

import com.trelloclone.domain.common.event.DomainEvent;
import com.trelloclone.domain.model.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class UserRegisteredEvent extends DomainEvent {

    private User user;

    public UserRegisteredEvent(User user) {
        super(user);
        Assert.notNull(user, "Parameter `user` must not be null");
        this.user = user;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRegisteredEvent that = (UserRegisteredEvent) o;
        return that.user.equals(this.user);
    }

    public int hashCode() {
        return this.user.hashCode();
    }

    public String toString() {
        return "UserRegisteredEvent{" +
                "user='" + user + '\'' +
                "timestamp='" + getTimestamp() + '\'' +
                '}';
    }
}
