package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Person;
import org.tables.Tag;
import org.tables.composite.keys.PersonHasInterestKey;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PersonHasInterest {
    @EmbeddedId
    private PersonHasInterestKey id;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
