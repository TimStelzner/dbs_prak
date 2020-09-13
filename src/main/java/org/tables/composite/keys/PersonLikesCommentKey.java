package org.tables.composite.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Provides Hibernate Mappings for Composite Primary Keys, concerning {@link org.tables.composite.PersonLikesComment}.
 *
 * @see org.tables.composite.PersonLikesComment
 */
@Embeddable
@Setter
@Getter
public class PersonLikesCommentKey implements Serializable {
    @Column(nullable = false)
    private Long personId;

    @Column(nullable = false)
    private Long commentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonLikesCommentKey)) return false;
        PersonLikesCommentKey that = (PersonLikesCommentKey) o;
        return getPersonId().equals(that.getPersonId()) &&
                getCommentId().equals(that.getCommentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getCommentId());
    }
}
