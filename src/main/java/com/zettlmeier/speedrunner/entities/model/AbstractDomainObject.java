package com.zettlmeier.speedrunner.entities.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

@MappedSuperclass
public abstract class AbstractDomainObject extends AbstractDomainIdentifier {
    @JsonIgnore
    @Column(name = "creation_date", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    protected Instant creationDate;

    @JsonIgnore
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    protected Instant lastUpdate;

    @JsonIgnore
    @Column(name = "inactive_data", nullable = true, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant inactiveDate;

    public Instant getCreationDate() { return creationDate; }

    public void setCreationDate(Instant creationDate) { this.creationDate = creationDate; }

    public Instant getLastUpdate() { return lastUpdate; }

    public void setLastUpdate(Instant lastUpdate) { this.lastUpdate = lastUpdate; }

    public Instant getInactiveDate() { return inactiveDate; }

    public void setInactiveDate(Instant inactiveDate) { this.inactiveDate = inactiveDate; }

    @PrePersist
    public void prePersist() {
        this.setCreationDate(Instant.now());
        this.setLastUpdate(Instant.now());
    }

    @PreUpdate
    public void preUpdate() {
        this.setLastUpdate(Instant.now());
    }

    public void updateFrom(AbstractDomainObject that) {
        BeanUtils.copyProperties(that, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractDomainObject that = (AbstractDomainObject)o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(creationDate, this.creationDate)
                .append(lastUpdate, this.lastUpdate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37) // Always pull examples offline for random numbers
                .appendSuper(super.hashCode())
                .append(creationDate)
                .append(lastUpdate)
                .toHashCode();
    }
}
