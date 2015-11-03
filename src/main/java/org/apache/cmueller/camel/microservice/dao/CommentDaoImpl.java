package org.apache.cmueller.camel.microservice.dao;

import javax.inject.Singleton;

import org.apache.cmueller.camel.microservice.model.Comment;

import java.util.*;

@Singleton
public class CommentDaoImpl implements CommentDao {

    private Map<String, Comment> comments = new HashMap<>();

    @Override
    public Collection<Comment> get() {
        return comments.values();
    }

    @Override
    public Comment get(String id) {
        return comments.get(id);
    }

    @Override
    public Comment create(Comment comment) {
        comment.setId(UUID.randomUUID().toString());

        comments.put(comment.getId(), comment);

        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        comments.put(comment.getId(), comment);

        return comment;
    }

    @Override
    public Comment delete(String id) {
        return comments.remove(id);
    }
}