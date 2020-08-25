package org.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column
    private String title;

    @Column
    private Date creation_date;

    @Column(nullable = false)
    private Long person_id;

}
