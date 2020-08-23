package org.tables;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * Implements place table.
 */
@Slf4j
@Getter
@Setter
@MappedSuperclass
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private int id;

    @Column()
    private String name;

    @Column()
    private String url;

    @Column()
    private String type;

    @Column()
    private Long is_part_of;
}
