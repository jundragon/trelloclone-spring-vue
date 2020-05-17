package com.trelloclone.domain.model.team;

import com.trelloclone.domain.common.model.AbstractBaseEntity;
import com.trelloclone.domain.model.user.UserId;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "team")
@Getter
public class Team extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "archived")
    private boolean archived;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    /**
     * Create new team
     */
    public static Team create(String name, UserId creatorId) {
        Team team = new Team();
        team.name = name;
        team.archived = false;
        team.userId = creatorId.value();
        team.createdDate = LocalDateTime.now();
        return team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return userId == team.userId &&
                Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userId);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", archived=" + archived +
                ", createdDate=" + createdDate +
                '}';
    }
}
