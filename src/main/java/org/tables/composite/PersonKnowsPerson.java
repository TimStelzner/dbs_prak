package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Person;
import org.tables.composite.keys.PersonKnowsPersonKey;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
public class PersonKnowsPerson {
    @EmbeddedId
    private PersonKnowsPersonKey id;

    @ManyToOne
    @MapsId("personId1")
    @JoinColumn(name = "person_id1")
    private Person person1;

    @ManyToOne
    @MapsId("personId2")
    @JoinColumn(name = "person_id2")
    private Person person2;

    @Column
    private Date creationDate;

}
