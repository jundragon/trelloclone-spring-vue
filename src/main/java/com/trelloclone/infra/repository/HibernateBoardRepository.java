package com.trelloclone.infra.repository;

import com.trelloclone.domain.model.board.Board;
import com.trelloclone.domain.model.board.BoardRepository;
import com.trelloclone.domain.model.user.UserId;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class HibernateBoardRepository extends HibernateSupport<Board> implements BoardRepository {

  HibernateBoardRepository(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public List<Board> findBoardsByMembership(UserId userId) {
    String sql = "SELECT b.* FROM board b LEFT JOIN board_member bm ON b.id = bm.board_id WHERE bm.user_id = :userId";
    NativeQuery<Board> query = getSession().createNativeQuery(sql, Board.class);
    query.setParameter("userId", userId.value());
    return query.list();
  }

}
