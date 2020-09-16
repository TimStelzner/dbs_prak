package org.tables.procedures;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Provides Hibernate Mappings for the stored procedure "my_function".
 * TODO Change name of stored procedure to something more readable.
 */
@Entity
@Getter
@Setter
@NamedStoredProcedureQuery(
        name = "myFunction",
        resultSetMappings = "CustomTable",
        procedureName = "my_function",
        parameters = {
                @StoredProcedureParameter(name = "id1", type = Long.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "id2", type = Long.class, mode = ParameterMode.IN)
        }
)
@SqlResultSetMapping(
        name = "CustomTable",
        entities = @EntityResult(entityClass = FamiliarityPath.class)
)
public class FamiliarityPath {
    @Id
    private String pathString;

    @Column
    private Integer depth;
}
