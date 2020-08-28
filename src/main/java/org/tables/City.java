package org.tables;

import org.tables.parent.Place;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class City extends Place {

    @ManyToOne(targetEntity = Country.class)
    @JoinColumn(foreignKey = @ForeignKey(name = "city_is_part_of_fkey"))
    private Long is_part_of;
}
