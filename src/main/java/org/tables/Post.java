package org.tables;

import org.tables.parent.Message;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Post extends Message {

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "reply_of_post")
    private Set<Comment> comments;
}
