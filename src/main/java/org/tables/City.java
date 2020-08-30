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
public class City extends Place {

    @ManyToOne(targetEntity = Country.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "city_is_part_of_fkey"))
    private Country isPartOf;

    @OneToMany(mappedBy = "city")
    private Set<University> universities = new HashSet<>();
}
