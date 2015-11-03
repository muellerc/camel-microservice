package org.apache.cmueller.camel.microservice.dao;

import java.util.Collection;

import org.apache.cmueller.camel.microservice.model.Comment;

public interface CommentDao {

    public Collection<Comment> get();

    public Comment get(String id);

    public Comment create(Comment buyer);

    public Comment update(Comment buyer);

    public Comment delete(String id);
}
