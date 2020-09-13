package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Company;
import org.tables.Person;
import org.tables.composite.keys.PersonWorksAtKey;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PersonWorksAt {
    @EmbeddedId
    private PersonWorksAtKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private Company company;

    @Column
    private int workFrom;
}
