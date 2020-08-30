package org.tables.composite.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class PersonKnowsPersonKey implements Serializable {
    @Column
    private Long personId1;

    @Column
    private Long personId2;

}
