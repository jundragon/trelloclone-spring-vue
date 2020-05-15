package com.trelloclone.infra.repository;

import org.hibernate.Session;

import javax.persistence.EntityManager;

public abstract class HibernateSupport {

    final EntityManager entityManager;

    HibernateSupport(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    Session getSession() {
        return entityManager.unwrap(Session.class);
    }
}
