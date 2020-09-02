package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Post;
import org.tables.Tag;
import org.tables.composite.keys.PostHasTagKey;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PostHasTag {
    @EmbeddedId
    private PostHasTagKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
