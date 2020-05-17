package com.trelloclone.domain.model.board;

import com.trelloclone.domain.common.model.AbstractBaseEntity;
import com.trelloclone.domain.model.user.UserId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "board_member")
public class BoardMember extends AbstractBaseEntity {

    @EmbeddedId
    private BoardMemberId id;

    public static BoardMember create(BoardId boardId, UserId userId) {
        BoardMember boardMember = new BoardMember();
        boardMember.id = new BoardMemberId(boardId, userId);
        return boardMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardMember)) return false;
        BoardMember that = (BoardMember) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BoardMember{" +
                "id=" + id +
                '}';
    }

    @Embeddable
    public static class BoardMemberId implements Serializable {

        @Column(name = "board_id")
        private long boardId;

        @Column(name = "user_id")
        private long userId;

        public BoardMemberId() {
        }

        private BoardMemberId(BoardId boardId, UserId userId) {
            this.boardId = boardId.value();
            this.userId = userId.value();
        }

        public BoardId getBoardId() {
            return new BoardId(boardId);
        }

        public UserId getUserId() {
            return new UserId(userId);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BoardMemberId)) return false;
            BoardMemberId that = (BoardMemberId) o;
            return boardId == that.boardId &&
                    userId == that.userId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(boardId, userId);
        }

        @Override
        public String toString() {
            return "BoardMemberId{" +
                    "boardId=" + boardId +
                    ", userId=" + userId +
                    '}';
        }
    }
}
