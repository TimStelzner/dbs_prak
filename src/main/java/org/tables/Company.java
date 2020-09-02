package org.tables;

import lombok.Getter;
import lombok.Setter;
import org.tables.parent.Organisation;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Company extends Organisation {

    @ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "company_country_fkey"))
    private Country country;
}
