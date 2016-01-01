package org.apache.cmueller.camel.microservice.dao;

import java.util.List;

import org.apache.cmueller.camel.microservice.model.Comment;

public interface CommentDao {

    public List<Comment> get();

    public Comment get(Long id);

    public Comment create(Comment buyer);

    public Comment update(Comment buyer);

    public Comment delete(Long id);
}
