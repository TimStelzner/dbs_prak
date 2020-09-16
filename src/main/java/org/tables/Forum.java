package org.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides Hibernate Mappings for table relation "forum".
 */
@Entity
@Getter
@Setter
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column
    private String title;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "forum_person_id_fkey"))
    private Person person;

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();
}
