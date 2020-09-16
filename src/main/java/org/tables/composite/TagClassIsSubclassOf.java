package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.TagClass;
import org.tables.composite.keys.TagClassIsSubclassOfKey;

import javax.persistence.*;

/**
 * Provides Hibernate Mappings for table relation tag_class_is_subclass_of.
 */
@Entity
@Getter
@Setter
public class TagClassIsSubclassOf {
    @EmbeddedId
    private TagClassIsSubclassOfKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("childId")
    @JoinColumn(name = "child_id")
    private TagClass childTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("parentId")
    @JoinColumn(name = "parent_id")
    private TagClass parentTag;
}
