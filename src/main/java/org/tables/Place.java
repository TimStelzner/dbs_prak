package org.tables;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Implements place table.
 */
@Entity
@Slf4j
@Data
public class Place implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @Column(nullable = true)
    private Integer is_part_of;
}
