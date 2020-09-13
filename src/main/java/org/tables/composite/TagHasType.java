package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Tag;
import org.tables.TagClass;
import org.tables.composite.keys.TagHasTypeKey;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class TagHasType {
    @EmbeddedId
    private TagHasTypeKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagClassId")
    @JoinColumn(name = "tagclass_id")
    private TagClass tagClass;
}
