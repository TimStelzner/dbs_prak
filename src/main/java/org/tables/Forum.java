package org.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "person_id", nullable = false)
    private Long personId;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "forum_has_member",
            joinColumns = {@JoinColumn(name = "forum_id")},
            inverseJoinColumns = {@JoinColumn(name = "person_id")})
    private Set<Person> persons = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "forum_has_tag",
            joinColumns = {@JoinColumn(name = "forum_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();
}
