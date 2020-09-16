package org.tables;

import lombok.Getter;
import lombok.Setter;
import org.tables.composite.PersonWorksAt;
import org.tables.parent.Organisation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides Hibernate Mappings for table relation "company".
 */
@Entity
@Getter
@Setter
public class Company extends Organisation {

    @ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "company_country_fkey"))
    private Country country;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonWorksAt> employees = new HashSet<>();
}
