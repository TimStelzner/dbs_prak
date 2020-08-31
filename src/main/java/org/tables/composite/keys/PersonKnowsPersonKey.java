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
public class PersonKnowsPersonKey implements Serializable {
    @Column
    private Long personId1;

    @Column
    private Long personId2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonKnowsPersonKey)) return false;
        PersonKnowsPersonKey that = (PersonKnowsPersonKey) o;
        return getPersonId1().equals(that.getPersonId1()) &&
                getPersonId2().equals(that.getPersonId2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId1(), getPersonId2());
    }
}
