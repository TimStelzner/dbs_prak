package org.tables;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.tables.parent.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity @Getter @Setter
public class Person extends BaseEntity {

    @Column()
    private Timestamp creation_date;

    @Column()
    private String surname;

    @Column
    private String name;

    @Column
    private String gender;

    @Column
    private Date date;

    @Column
    private String browser_used;

    @Column
    private String location_ip;

    @Column
    private Integer city_id;

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]")
    private String[] emails;

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]", nullable = false)
    private String[] speaks;

    @ManyToMany(mappedBy = "persons")
    private Set<Forum> forums = new HashSet<>();

}
