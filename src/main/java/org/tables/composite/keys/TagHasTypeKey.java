package org.tables.composite.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Provides Hibernate Mappings for Composite Primary Keys, concerning {@link org.tables.composite.TagHasType}.
 *
 * @see org.tables.composite.TagHasType
 */
@Embeddable
@Getter
@Setter
public class TagHasTypeKey implements Serializable {
    @Column
    private Long tagId;

    // name tag required because snake case was used incorrectly in database.
    @Column(name = "tag_class_id")
    private Long tagClassId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagHasTypeKey)) return false;
        TagHasTypeKey that = (TagHasTypeKey) o;
        return getTagId().equals(that.getTagId()) &&
                getTagClassId().equals(that.getTagClassId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTagId(), getTagClassId());
    }
}
