package org.tables.parent;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
})

/**
 * Simulates arrays as primitive types for database.
 * Classes with arrays need to extend this class.
 * Use @Type(type = "string-array") to implement the array type.
 */
@MappedSuperclass @Getter @Setter
public class BaseEntity {
    @Id
    private Long id;
}
