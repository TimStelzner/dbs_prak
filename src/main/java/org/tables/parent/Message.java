package org.tables.parent;

import lombok.Getter;
import lombok.Setter;
import org.tables.Country;
import org.tables.Person;

import javax.persistence.*;
import java.sql.Timestamp;


@MappedSuperclass
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column
    private Timestamp creation_date;

    @Column
    private String content;

    @Column
    private Long length;

    @Column
    private String browser_used;

    @Column
    private String location_ip;

    @ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "comment_person_id_fkey"))
    private Long person_id;

    @ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "comment_country_fkey"))
    private Long country_id;
}
