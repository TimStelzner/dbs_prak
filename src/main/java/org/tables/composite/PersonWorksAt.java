package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Company;
import org.tables.Person;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PersonWorksAt {

    @EmbeddedId
    private WorksAtKey id;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private Company company;

}
