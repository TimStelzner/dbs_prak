package org.tables.parent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Implements place table.
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

    @Column()
    private Long is_part_of;
}
