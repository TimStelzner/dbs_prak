package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Tag;
import org.tables.TagClass;
import org.tables.composite.keys.TagClassIsSubclassOfKey;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TagClassIsSubclassOf {
    @EmbeddedId
    private TagClassIsSubclassOfKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("childId")
    @JoinColumn(name = "child_id")
    private Tag childTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("parentId")
    @JoinColumn(name = "parent_id")
    private TagClass parentTag;
}
