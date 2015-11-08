package org.apache.cmueller.camel.microservice.jaxrs;

import java.util.Collection;

import org.apache.cmueller.camel.microservice.model.Comment;

public interface CommentsResource {

    public Collection<Comment> getAll();

    public Comment create(Comment comment);

    public Comment get(String id);

    public Comment update(String id, Comment comment);

    public void delete(String id);
}