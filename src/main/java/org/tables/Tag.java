package org.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String url;

    @ManyToMany(mappedBy = "tags")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    private Set<Forum> forums = new HashSet<>();
}
