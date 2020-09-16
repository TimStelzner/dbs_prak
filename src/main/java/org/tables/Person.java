package org.tables;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.tables.composite.*;
import org.tables.parent.BaseEntity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides Hibernate Mappings for table relation "person".
 */
@Entity
@Getter
@Setter
public class Person extends BaseEntity {

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "person_city_ip_fkey"))
    private City city;

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

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]")
    private String[] emails;

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]", nullable = false)
    private String[] speaks;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> post = new HashSet<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Forum> forums = new HashSet<>();

    @OneToMany(mappedBy = "person1", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PkpSymmetric> knows = new HashSet<>();

    @OneToMany(mappedBy = "person2", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PkpSymmetric> known = new HashSet<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonHasInterest> likes = new HashSet<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonWorksAt> jobs = new HashSet<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonStudiesAt> universities = new HashSet<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonLikesComment> likesComments = new HashSet<>();

}
