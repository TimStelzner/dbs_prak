package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Comment;
import org.tables.Person;
import org.tables.composite.keys.PersonLikesCommentKey;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
public class PersonLikesComment {
    @EmbeddedId
    private PersonLikesCommentKey id;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @MapsId("commentId")
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column
    private Date creationDate;
}
