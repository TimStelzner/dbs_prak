package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Person;
import org.tables.Post;
import org.tables.composite.keys.PersonLikesPostKey;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
public class PersonLikesPost {
    @EmbeddedId
    private PersonLikesPostKey id;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    @Column
    private Date creationDate;
}
