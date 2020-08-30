package org.tables;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.tables.parent.BaseEntity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Person extends BaseEntity {

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column()
    private String surname;

    @Column
    private String name;

    @Column
    private String gender;

    @Column
    private Date birthday;

    @Column(name = "browser_used")
    private String browser_used;

    @Column(name = "location_ip")
    private String locationIp;

    @Column(name = "city_id")
    private Integer cityId;

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]")
    private String[] emails;

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]", nullable = false)
    private String[] speaks;

    @ManyToMany(mappedBy = "persons")
    private Set<Forum> forums = new HashSet<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> post = new HashSet<>();
}
