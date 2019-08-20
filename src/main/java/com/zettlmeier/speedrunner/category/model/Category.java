package com.zettlmeier.speedrunner.category.model;

import com.zettlmeier.speedrunner.entities.model.AbstractDomainObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends AbstractDomainObject {
    @Column(name = "name", nullable = false)
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        var other = (Category)o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(this.getName(), other.getName())
                .build();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(this.getName())
                .toHashCode();
    }
}
