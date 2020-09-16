package org.tables.parent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Provides Hibernate Mappings for table relation "organisation".
 */
@MappedSuperclass @Getter @Setter
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private long id;

    @Column()
    private String name;
}
