package org.tables;

import lombok.Getter;
import lombok.Setter;
import org.tables.composite.TagClassIsSubclassOf;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class TagClass {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String url;

    // TODO parents and children are switched around. I dont get it.
    @OneToMany(mappedBy = "parentTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TagClassIsSubclassOf> parents = new HashSet<>();

    @OneToMany(mappedBy = "childTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TagClassIsSubclassOf> children = new HashSet<>();
}
