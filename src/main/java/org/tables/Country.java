package org.tables;

import lombok.Getter;
import lombok.Setter;
import org.tables.parent.Place;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
public class Country extends Place {
    @ManyToOne(targetEntity = Continent.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "is_part_of", foreignKey = @ForeignKey(name = "country_is_part_of_fkey"))
    private Continent isPartOf;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Company> companies;

    @OneToMany(mappedBy = "isPartOf", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<City> cities;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();
}
