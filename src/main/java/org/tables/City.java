package org.tables;

import org.tables.parent.Place;

import javax.persistence.*;

@Entity
public class City extends Place {

    @ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "is_part_of", foreignKey = @ForeignKey(name = "comment_reply_of_comment_fkey"))
    private Long is_part_of;
}
