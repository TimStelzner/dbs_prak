package org.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity @Getter @Setter
public class University extends Organisation {

    @ManyToOne(targetEntity = City.class, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    @Getter
    private Long city_id;

}
