package org.tables;

import lombok.Getter;
import lombok.Setter;
import org.tables.composite.PersonLikesComment;
import org.tables.parent.Message;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides Hibernate Mappings for table relation "comment".
 */
@Entity
@Getter
@Setter
public class Comment extends Message {

    @ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_of_post", foreignKey = @ForeignKey(name = "comment_reply_of_post_fkey"))
    private Post replyOfPost;

    @ManyToOne(targetEntity = Comment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_of_comment", foreignKey = @ForeignKey(name = "comment_reply_of_comment_fkey"))
    private Comment replyOfComment;

    @ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "comment_person_id_fkey"))
    private Person person;

    @ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "comment_country_id_fkey"))
    private Country country;

    @OneToMany(mappedBy = "replyOfComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonLikesComment> likedBy = new HashSet<>();

}