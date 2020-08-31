package org.tables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class TagClass {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String url;
}
