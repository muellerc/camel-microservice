package org.apache.cmueller.camel.microservice.dao;

import org.apache.cmueller.camel.microservice.model.Comment;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class CommentDaoImpl implements CommentDao {

    @PersistenceContext(unitName = "prod")
    EntityManager em;

    @Override
    public List<Comment> get() {
        return em.createQuery("SELECT c from Comment c ORDER BY c.id ASC", Comment.class).getResultList();
    }

    @Override
    public Comment get(Long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public Comment create(Comment comment) {
        this.em.persist(comment);

        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        return this.em.merge(comment);
    }

    @Override
    public Comment delete(Long id) {
        Comment comment = get(id);
        this.em.remove(comment);
        return comment;
    }
}