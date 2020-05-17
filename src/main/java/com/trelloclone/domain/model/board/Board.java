package com.trelloclone.domain.model.board;

import com.trelloclone.domain.common.model.AbstractBaseEntity;
import com.trelloclone.domain.model.team.TeamId;
import com.trelloclone.domain.model.user.UserId;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "board")
@Getter
public class Board extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BoardId id;

    @Column(name = "name", nullable = false, length = 128, unique = true)
    private String name;

    @Column(name = "description", nullable = false, length = 256, unique = true)
    private String description;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "archived")
    private boolean archived;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    /**
     * Create new board
     */
    public static Board create(UserId userId, String name, String description, TeamId teamId) {
        Board board = new Board();
        board.userId = userId.value();
        board.name = name;
        board.description = description;
        board.teamId = teamId.isValid() ? teamId.value() : null;
        board.archived = false;
        board.createdDate = LocalDateTime.now();
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board = (Board) o;
        return userId == board.userId &&
                teamId == board.teamId &&
                Objects.equals(name, board.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userId, teamId);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", teamId=" + teamId +
                ", archived=" + archived +
                ", createdDate=" + createdDate +
                '}';
    }
}
