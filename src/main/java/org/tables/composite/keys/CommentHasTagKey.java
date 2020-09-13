package org.tables.composite.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Provides Hibernate Mappings for Composite Primary Keys, concerning {@link org.tables.composite.CommentHasTag}.
 *
 * @see org.tables.composite.CommentHasTag
 */
@Embeddable
@Getter
@Setter
public class CommentHasTagKey implements Serializable {
    @Column(nullable = false)
    private Long commentId;

    @Column(nullable = false)
    private Long tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentHasTagKey)) return false;
        CommentHasTagKey that = (CommentHasTagKey) o;
        return getCommentId().equals(that.getCommentId()) &&
                getTagId().equals(that.getTagId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommentId(), getTagId());
    }
}
