package com.zettlmeier.speedrunner.entities.model;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractDomainIdentifierTest {
    public static class ConcreteDomainIdentifier extends AbstractDomainIdentifier {

    }

    @Test
    public void getIdTest() {
        AbstractDomainIdentifier domainIdentifier = new AbstractDomainIdentifier() {};
        var id = UUID.randomUUID();
        domainIdentifier.setId(id);
        assertThat(domainIdentifier.getId()).isEqualTo(id);
    }

    @Test
    public void equalsTest() {
        var id = UUID.randomUUID();
        AbstractDomainIdentifier domainIdentifier = new ConcreteDomainIdentifier();
        domainIdentifier.setId(id);

        AbstractDomainIdentifier domainIdentifier2 = new ConcreteDomainIdentifier();
        domainIdentifier2.setId(UUID.randomUUID());

        AbstractDomainIdentifier domainIdentifier3 = new ConcreteDomainIdentifier();
        domainIdentifier3.setId(id);

        assertThat(domainIdentifier.equals(domainIdentifier2)).isFalse();
        assertThat(domainIdentifier.equals(domainIdentifier3)).isTrue();
    }

    @Test
    public void hashCodeTest() {
        AbstractDomainIdentifier domainIdentifier = new ConcreteDomainIdentifier();
        domainIdentifier.setId(UUID.randomUUID());

        AbstractDomainIdentifier domainIdentifier2 = new ConcreteDomainIdentifier();
        domainIdentifier2.setId(UUID.randomUUID());

        assertThat(domainIdentifier.hashCode()).isNotEqualTo(domainIdentifier2.hashCode());
    }
}
