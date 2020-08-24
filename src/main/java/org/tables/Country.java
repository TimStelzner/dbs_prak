package org.tables;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Country extends Place {

    @SuppressWarnings({"JpaDataSourceORMInspection", "JpaAttributeTypeInspection"})
    @ManyToOne(targetEntity = Continent.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "is_part_of", referencedColumnName = "id")
    private Long is_part_of;

}
