package org.tables.composite.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Provides Hibernate Mappings for Composite Primary Keys, concerning {@link org.tables.composite.PersonLikesPost}.
 *
 * @see org.tables.composite.PersonLikesPost
 */
@Embeddable
@Getter
@Setter
public class PersonLikesPostKey implements Serializable {
    @Column(nullable = false)
    private Long personId;

    @Column(nullable = false)
    private Long postId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonLikesPostKey)) return false;
        PersonLikesPostKey that = (PersonLikesPostKey) o;
        return getPersonId().equals(that.getPersonId()) &&
                getPostId().equals(that.getPostId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getPostId());
    }
}
