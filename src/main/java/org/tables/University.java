package org.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
public class University extends Organisation {

    @ManyToOne(targetEntity = City.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "university_city_fkey"), nullable = false)
    private City city;
}
