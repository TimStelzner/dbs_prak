package org.tables.composite.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Provides Hibernate Mappings for Composite Primary Keys, concerning {@link org.tables.composite.PostHasTag}.
 *
 * @see org.tables.composite.PostHasTag
 */
@Embeddable
@Setter
@Getter
public class PostHasTagKey implements Serializable {
    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostHasTagKey)) return false;
        PostHasTagKey that = (PostHasTagKey) o;
        return getPostId().equals(that.getPostId()) &&
                getTagId().equals(that.getTagId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId(), getTagId());
    }
}
