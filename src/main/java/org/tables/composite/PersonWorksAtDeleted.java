package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Company;
import org.tables.Person;
import org.tables.composite.keys.PersonWorksAtDeletedKey;

import javax.persistence.*;

/**
 * Provides Hibernate Mappings for table relation person_works_at_deleted.
 */
@Entity
@Getter
@Setter
public class PersonWorksAtDeleted {
    @EmbeddedId
    private PersonWorksAtDeletedKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private Company company;

    // name required because the database has a misspelled variable name
    @Column(name = "work_form")
    private Integer workFrom;
}
