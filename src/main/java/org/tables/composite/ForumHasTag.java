package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Forum;
import org.tables.Tag;
import org.tables.composite.keys.ForumHasTagKey;

import javax.persistence.*;

/**
 * Provides Hibernate Mappings for table relation forum_has_tag.
 */
@Entity
@Getter
@Setter
public class ForumHasTag {
    @EmbeddedId
    private ForumHasTagKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("forumId")
    @JoinColumn(name = "forum_id")
    private Forum forum;
}
