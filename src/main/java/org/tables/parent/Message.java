package org.tables.parent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * Provides Hibernate Mappings for table relation message.
 * Note, this is a mapped superclass, thus Hibernate cannot access the database tables.
 * Use the child classes to access the database entities instead.
 * Note, the Foreign keys for person_id and country_id are implemented in the child classes,
 * because they caused Hibernate to create unwanted database overwrites.
 *
 * @see org.tables.Post
 * @see org.tables.Comment
 */
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
}
