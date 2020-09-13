package org.tables.composite.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Provides Hibernate Mappings for Composite Primary Keys, concerning {@link org.tables.composite.ForumHasTag}.
 *
 * @see org.tables.composite.ForumHasTag
 */
@Embeddable
@Getter
@Setter
public class ForumHasTagKey implements Serializable {
    @Column(nullable = false)
    private Long tagId;

    @Column(nullable = false)
    private Long forumId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForumHasTagKey)) return false;
        ForumHasTagKey that = (ForumHasTagKey) o;
        return getTagId().equals(that.getTagId()) &&
                getForumId().equals(that.getForumId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTagId(), getForumId());
    }
}
