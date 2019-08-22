package com.zettlmeier.speedrunner.speedrun.model;

import com.zettlmeier.speedrunner.entities.model.AbstractDomainObject;
import com.zettlmeier.speedrunner.game.model.Game;
import com.zettlmeier.speedrunner.user.model.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "speedrun")
public class Speedrun extends AbstractDomainObject {
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // Duration in seconds
    @Column(name = "duration", nullable = false)
    private int duration;

    public Game getGame() { return this.game; }

    public void setGame(Game game) { this.game = game; }

    public User getUser() { return this.user; }

    public void setUser(User user) { this.user = user; }

    public float getDuration() { return this.duration; }

    public void setDuration(int duration) { this.duration = duration; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        var other = (Speedrun)o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(this.getGame(), other.getGame())
                .append(this.getUser(), other.getUser())
                .append(this.getDuration(), other.getDuration())
                .build();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(this.getGame())
                .append(this.getUser())
                .append(this.getDuration())
                .toHashCode();
    }
}
