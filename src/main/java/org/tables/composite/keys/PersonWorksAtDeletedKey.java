package org.tables.composite.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Provides Hibernate Mappings for Composite Primary Keys, concerning {@link org.tables.composite.PersonWorksAtDeleted}.
 *
 * @see org.tables.composite.PersonWorksAtDeleted
 */
@Embeddable
@Getter
@Setter
public class PersonWorksAtDeletedKey implements Serializable {
    @Column(nullable = false)
    private Long personId;

    @Column(nullable = false)
    private Long companyId;

    @Column(nullable = false)
    private Timestamp deleteDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonWorksAtDeletedKey)) return false;
        PersonWorksAtDeletedKey that = (PersonWorksAtDeletedKey) o;
        return getPersonId().equals(that.getPersonId()) &&
                getCompanyId().equals(that.getCompanyId()) &&
                getDeleteDate().equals(that.getDeleteDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getCompanyId(), getDeleteDate());
    }
}
