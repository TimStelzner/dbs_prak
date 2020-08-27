package org.tables;

import lombok.Getter;
import lombok.Setter;
import org.tables.parent.Place;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
public class Country extends Place {

    @ManyToOne(targetEntity = Continent.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "is_part_of", foreignKey = @ForeignKey(name = "country_is_part_of_fkey"))
    private Long is_part_of;

    @OneToMany(mappedBy = "country_id")
    private Set<Company> companies;
}
