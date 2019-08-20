package com.zettlmeier.speedrunner.gamecategory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zettlmeier.speedrunner.category.model.Category;
import com.zettlmeier.speedrunner.entities.model.AbstractDomainIdentifier;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class GameCategory extends AbstractDomainIdentifier {
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public GameCategory() { }

    public GameCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        var other = (GameCategory)o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(this.getCategory(), other.getCategory())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(this.getCategory())
                .toHashCode();
    }
}
