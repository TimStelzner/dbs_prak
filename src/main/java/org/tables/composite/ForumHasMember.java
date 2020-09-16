package org.tables.composite;

import lombok.Getter;
import lombok.Setter;
import org.tables.Forum;
import org.tables.Person;
import org.tables.composite.keys.ForumHasMemberKey;

import javax.persistence.*;
import java.sql.Date;

/**
 * Provides Hibernate Mappings for table relation forum_has_member.
 */
@Entity
@Getter
@Setter
public class ForumHasMember {
    @EmbeddedId
    private ForumHasMemberKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("forumId")
    @JoinColumn(name = "forum_id")
    private Forum forum;

    @Column
    private Date joinDate;
}
