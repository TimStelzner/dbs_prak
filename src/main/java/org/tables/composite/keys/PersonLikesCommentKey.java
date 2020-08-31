package org.tables.composite.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Setter
@Getter
public class PersonLikesCommentKey implements Serializable {
    @Column
    private Long personId;

    @Column
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
