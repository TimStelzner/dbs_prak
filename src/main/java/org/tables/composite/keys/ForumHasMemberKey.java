package org.tables.composite.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Provides Hibernate Mappings for Composite Primary Keys, concerning {@link org.tables.composite.ForumHasMember}.
 *
 * @see org.tables.composite.ForumHasMember
 */
@Embeddable
@Getter
@Setter
public class ForumHasMemberKey implements Serializable {

    @Column(nullable = false)
    private Long personId;

    @Column(nullable = false)
    private Long forumId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForumHasMemberKey)) return false;
        ForumHasMemberKey that = (ForumHasMemberKey) o;
        return getPersonId().equals(that.getPersonId()) &&
                getForumId().equals(that.getForumId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getForumId());
    }
}
