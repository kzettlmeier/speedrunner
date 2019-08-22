package com.zettlmeier.speedrunner.entities.model;

import org.junit.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractDomainObjectTest {
    public static class ConcreteDomainObject extends AbstractDomainObject {

    }

    @Test
    public void getCreationDateTest() {
        AbstractDomainObject domainObject = new AbstractDomainObject() {
        };

        Instant instant = Instant.now();
        domainObject.setCreationDate(instant);
        assertThat(domainObject.getCreationDate()).isBetween(instant.minusSeconds(1L), instant.plusSeconds(1L));
    }

    @Test
    public void getLastUpdateTest() {
        AbstractDomainObject domainObject = new AbstractDomainObject() {
        };

        Instant instant = Instant.now();
        domainObject.setLastUpdate(instant);
        assertThat(domainObject.getLastUpdate()).isBetween(instant.minusSeconds(1L), instant.plusSeconds(1L));
    }

    @Test
    public void preUpdateTest() {
        AbstractDomainObject domainObject = new AbstractDomainObject() {
        };

        domainObject.preUpdate();

        assertThat(domainObject.getLastUpdate()).isNotNull();
        assertThat(domainObject.getCreationDate()).isNull();
    }

    @Test
    public void prePersist() {
        AbstractDomainObject domainObject = new AbstractDomainObject() {
        };

        domainObject.prePersist();

        assertThat(domainObject.getLastUpdate()).isNotNull();
        assertThat(domainObject.getCreationDate()).isNotNull();
    }

    @Test
    public void equalsTest() {
        var id = UUID.randomUUID();
        AbstractDomainObject domainObject = new ConcreteDomainObject();
        domainObject.setId(id);
        domainObject.prePersist();

        AbstractDomainObject domainObject2 = new ConcreteDomainObject();
        domainObject2.prePersist();

        AbstractDomainObject domainObject3 = new ConcreteDomainObject();
        domainObject3.setId(id);
        domainObject3.setLastUpdate(domainObject.getLastUpdate());
        domainObject3.setCreationDate(domainObject.getCreationDate());

        assertThat(domainObject.equals(domainObject2)).isFalse();
        assertThat(domainObject.equals(domainObject3)).isTrue();
    }

    @Test
    public void updateFromTest() {
        AbstractDomainObject domainObject = new AbstractDomainObject() {
        };
        domainObject.setId(UUID.randomUUID());
        Instant inactive = Instant.now();
        domainObject.setInactiveDate(inactive);
        domainObject.prePersist();

        AbstractDomainObject domainObject2 = new AbstractDomainObject() {
        };
        domainObject2.updateFrom(domainObject);

        assertThat(domainObject2.getId()).isNotNull();
        assertThat(domainObject2.getCreationDate()).isNotNull();
        assertThat(domainObject2.getLastUpdate()).isNotNull();
        assertThat(domainObject2.getInactiveDate()).isBetween(inactive.minusSeconds(1L), inactive.plusSeconds(1L));
    }

    @Test
    public void hashCodeTest() {
        AbstractDomainObject domainObject = new AbstractDomainObject() {
        };
        domainObject.setId(UUID.randomUUID());
        domainObject.prePersist();

        AbstractDomainObject domainObject2 = new AbstractDomainObject() {
        };
        domainObject2.setId(UUID.randomUUID());
        domainObject2.prePersist();

        assertThat(domainObject.hashCode()).isNotEqualTo(domainObject2.hashCode());
    }
}
