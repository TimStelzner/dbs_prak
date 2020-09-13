package org.tables;

import lombok.Getter;
import lombok.Setter;
import org.tables.parent.Message;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
/*
@AttributeOverrides( {
        @AttributeOverride(name = "personId", column = @Column(name = "person_id")),
        @AttributeOverride(name = "countryId", column = @Column(name = "country_id"))
})


 */
@Getter
@Setter
public class Post extends Message {

    @Column(name = "image_file")
    private String imageFile;

    @OneToMany(mappedBy = "replyOfPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(targetEntity = Forum.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fs_post_forum"))
    private Forum forum;

    @ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "post_person_id_fkey"))
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "post_country_id_fkey"))
    private Country country;

}
