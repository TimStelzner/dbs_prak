package org.tables;

import lombok.Getter;
import lombok.Setter;
import org.tables.parent.Message;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
public class Comment extends Message {

    @ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "comment_reply_of_post_fkey"))
    private Long reply_of_post;

    @ManyToOne(targetEntity = Comment.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "comment_reply_of_comment_fkey"))
    private Long reply_of_comment;

    @OneToMany(mappedBy = "reply_of_comment")
    private Set<Comment> comments;

}