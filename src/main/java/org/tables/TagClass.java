package org.tables;

import lombok.Getter;
import lombok.Setter;
import org.tables.composite.TagClassIsSubclassOf;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides Hibernate Mappings for table relation "tag_class".
 */
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

    @OneToMany(mappedBy = "parentTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TagClassIsSubclassOf> parentOf = new HashSet<>();

    @OneToMany(mappedBy = "childTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TagClassIsSubclassOf> childOf = new HashSet<>();

}
