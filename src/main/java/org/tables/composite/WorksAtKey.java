package org.tables.composite;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class WorksAtKey implements Serializable {

    @Column
    private Long personId;

    @Column
    private Long companyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorksAtKey)) return false;
        WorksAtKey that = (WorksAtKey) o;
        return getPersonId().equals(that.getPersonId()) &&
                getCompanyId().equals(that.getCompanyId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getCompanyId());
    }
}
