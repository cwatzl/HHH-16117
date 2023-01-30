package org.hibernate.bugs.hhh16117;

import jakarta.persistence.*;
import org.hibernate.bugs.hhh16117.model.Parent;
import org.hibernate.bugs.hhh16117.model.Child;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class TestHHH16117 {

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @AfterEach
    void destroy() {
        entityManagerFactory.close();
    }

    @Test
    void hhh16117Test() {

        doInTransaction(entityManager -> {
            var child = new Child();
            child.setName("key");
            var entity = new Parent(UUID.randomUUID().toString());
            entity.getData().addChild(child);

            entityManager.persist(entity);

            var found = findByQuery(entityManager, entity.getId());

            assertThat(found.getId()).isEqualTo(entity.getId());
        });
    }

    private Parent findByQuery(EntityManager entityManager, String id) {
        return entityManager.createQuery(
                "select e from Parent e where e.id = :id", Parent.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    private void doInTransaction(Consumer<EntityManager> action) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            action.accept(entityManager);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

}
