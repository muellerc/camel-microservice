package org.apache.cmueller.camel.microservice.jaxrs;

import java.util.Collection;

import org.apache.cmueller.camel.microservice.model.Comment;

public interface CommentsResource {

    public Comment create(Comment buyer);

    public Comment get(String id);

    public Comment update(String id, Comment buyer);

    public void delete(String id);

    public Collection<Comment> get();
}