package com.zettlmeier.speedrunner.user.model;

import com.zettlmeier.speedrunner.entities.model.AbstractDomainObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends AbstractDomainObject {
    @Column(name = "userName", nullable = false)
    private String userName;

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        var other = (User)o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(this.getUserName(), other.getUserName())
                .build();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(this.getUserName())
                .toHashCode();
    }
}
