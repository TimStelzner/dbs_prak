package org.tables.composite;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.tables.Person;
import org.tables.composite.keys.PersonKnowsPersonKey;

import javax.persistence.*;

@Entity
@Table(name = "pkp_symmetric")
@Subselect("select * from pkp_symmetric")
@Immutable
@Getter
@Setter
public class PkpSymmetric {
    @EmbeddedId
    @Column(updatable = false, nullable = false)
    private PersonKnowsPersonKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId1")
    @JoinColumn(name = "person_id1")
    private Person person1;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId2")
    @JoinColumn(name = "person_id2")
    private Person person2;

}
