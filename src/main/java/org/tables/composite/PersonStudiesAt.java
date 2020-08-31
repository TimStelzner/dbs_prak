package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Person;
import org.tables.University;
import org.tables.composite.keys.PersonStudiesAtKey;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PersonStudiesAt {
    @EmbeddedId
    private PersonStudiesAtKey id;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @MapsId("universityId")
    @JoinColumn(name = "university_id")
    private University university;

    @Column
    private Integer classYear;
}
