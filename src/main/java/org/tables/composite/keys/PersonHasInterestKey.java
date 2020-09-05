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
public class PersonHasInterestKey implements Serializable {

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "tag_id")
    private Long tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonHasInterestKey)) return false;
        PersonHasInterestKey that = (PersonHasInterestKey) o;
        return personId.equals(that.personId) &&
                tagId.equals(that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, tagId);
    }
}
