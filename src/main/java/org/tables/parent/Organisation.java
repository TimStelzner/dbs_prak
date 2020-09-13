package org.tables.parent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass @Getter @Setter
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private long id;

    @Column()
    private String name;
}
