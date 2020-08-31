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
public class TagClassIsSubclassOfKey implements Serializable {

    @Column(nullable = false)
    private Long childId;

    @Column(nullable = false)
    private Long parentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagClassIsSubclassOfKey)) return false;
        TagClassIsSubclassOfKey that = (TagClassIsSubclassOfKey) o;
        return getChildId().equals(that.getChildId()) &&
                getParentId().equals(that.getParentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChildId(), getParentId());
    }
}
