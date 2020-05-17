package com.trelloclone.infra.repository;

import com.trelloclone.domain.model.board.BoardMember;
import com.trelloclone.domain.model.board.BoardMemberRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class HibernateBoardMemberRepository extends HibernateSupport<BoardMember> implements BoardMemberRepository {

  HibernateBoardMemberRepository(EntityManager entityManager) {
    super(entityManager);
  }
}
