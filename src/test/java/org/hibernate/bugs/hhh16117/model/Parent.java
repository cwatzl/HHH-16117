package org.hibernate.bugs.hhh16117.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Parent {
    @Id
    private String id;

    @Embedded
    private final EmbeddedData data = new EmbeddedData();


    public Parent() {
    }

    public Parent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public EmbeddedData getData() {
        return data;
    }
}
