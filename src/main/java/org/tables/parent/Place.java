package org.tables.parent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Provides Hibernate Mappings for table relation "place".
 * Note, this is a mapped superclass, thus Hibernate cannot access the database tables.
 * Use the child classes to access the database entities instead.
 *
 * @see org.tables.Company
 * @see org.tables.University
 */
@Getter
@Setter
@MappedSuperclass
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column()
    private String name;

    @Column()
    private String url;

    @Column()
    private String type;
}
