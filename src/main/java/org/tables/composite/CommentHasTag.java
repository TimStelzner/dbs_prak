package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Comment;
import org.tables.Tag;
import org.tables.composite.keys.CommentHasTagKey;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class CommentHasTag {
    @EmbeddedId
    private CommentHasTagKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("commentId")
    @JoinColumn(name = "comment_id", foreignKey = @ForeignKey(name = "comment_has_tag_comment_fkey"))
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "cpmment_has_tag_tag_fkey"))
    private Tag tag;
}
