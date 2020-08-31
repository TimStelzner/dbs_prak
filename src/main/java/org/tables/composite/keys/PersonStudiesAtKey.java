package org.tables.composite.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class PersonStudiesAtKey implements Serializable {
    @Column(unique = true, nullable = false)
    private Long personId;

    @Column(unique = true, nullable = false)
    private Long universityId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonStudiesAtKey)) return false;
        PersonStudiesAtKey that = (PersonStudiesAtKey) o;
        return getPersonId().equals(that.getPersonId()) &&
                getUniversityId().equals(that.getUniversityId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getUniversityId());
    }
}
