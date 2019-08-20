package com.zettlmeier.speedrunner.game.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zettlmeier.speedrunner.entities.model.AbstractDomainObject;
import com.zettlmeier.speedrunner.gamecategory.model.GameCategory;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "game")
public class Game extends AbstractDomainObject {
    @Column(name = "title", nullable = false)
    private String title;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "game_id")
    private List<GameCategory> gameCategories = new ArrayList<>();

    public String getTitle() { return title; }

    public void setTitle() { this.title = title; }

    public List<GameCategory> getGameCategories() {
        return Collections.unmodifiableList(this.gameCategories);
    }

    public void setGameCategories(List<GameCategory> gameCategories) {
        this.gameCategories.clear();
        this.gameCategories.addAll(gameCategories);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        var other = (Game)o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(this.getTitle(), other.getTitle())
                .build();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(this.getTitle())
                .toHashCode();
    }
}
