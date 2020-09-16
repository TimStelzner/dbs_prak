package org.tables;

import lombok.Getter;
import lombok.Setter;
import org.tables.composite.PersonStudiesAt;
import org.tables.parent.Organisation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides Hibernate Mappings for table relation "university".
 */
@Entity
@Getter
@Setter
public class University extends Organisation {

    @ManyToOne(targetEntity = City.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "university_city_fkey"), nullable = false)
    private City city;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonStudiesAt> students = new HashSet<>();
}
