package org.hibernate.bugs.hhh16117.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class EmbeddedData {
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Child> children = new ArrayList<>();

    public List<Child> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void addChild(Child child) {
        this.children.add(child);
    }
}
