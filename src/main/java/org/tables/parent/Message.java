package org.tables.parent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column
    private String content;

    @Column
    private Long length;

    @Column(name = "browser_used")
    private String browserUsed;

    @Column(name = "location_ip")
    private String locationIp;
/*
    @ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "message_person_id_fkey"))
    private Long personId;

    @ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", foreignKey = @ForeignKey(name = "message_country_id_fkey"))
    private Long countryId;


 */
}
