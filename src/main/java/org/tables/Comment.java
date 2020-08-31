package org.tables;

import lombok.Getter;
import lombok.Setter;
import org.tables.parent.Message;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


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

    @OneToMany(mappedBy = "replyOfComment")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "comment_has_tag",
            joinColumns = {@JoinColumn(name = "comment_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags;

    @ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "comment_person_id_fkey"))
    private Person person;

    @ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "comment_country_id_fkey"))
    private Country country;


}