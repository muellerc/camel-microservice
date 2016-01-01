package org.apache.cmueller.camel.microservice.dao;

import org.apache.cmueller.camel.microservice.model.Comment;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by cmueller on 31/12/15.
 */
public class CommentDaoImplTest {

    private CommentDaoImpl dao;

    @Before
    public void init() {
        this.dao = new CommentDaoImpl();
        this.dao.em = mock(EntityManager.class);
    }

    @Test
    public void create() {
        Comment expected = new Comment(1L, "first comment");

        Comment comment = dao.create(expected);

        assertSame(expected, comment);
        verify(this.dao.em).persist(argThat(new BaseMatcher<Comment>() {

            @Override
            public boolean matches(Object item) {
                if (!(item instanceof Comment)) {
                    return false;
                }

                return expected.equals((Comment) item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Should be: " + expected);
            }
        }));
    }

    @Test
    public void get() {
        Comment expected = new Comment(1L, "first comment");
        when(this.dao.em.find(Comment.class, 1L)).thenReturn(expected);

        Comment comment = dao.get(1L);

        assertSame(expected, comment);
    }

    @Test
    public void getAll() {
        TypedQuery query = mock(TypedQuery.class);
        List<Comment> expected = Arrays.asList(new Comment(1L, "first comment"), new Comment(2L, "second comment"));
        when(this.dao.em.createQuery("SELECT c from Comment c ORDER BY c.id ASC", Comment.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(expected);

        List<Comment> comments = dao.get();

        assertSame(expected, comments);
    }

    @Test
    public void delete() {
        Comment expected = new Comment(1L, "first comment");
        when(this.dao.em.find(Comment.class, 1L)).thenReturn(expected);

        Comment comment = dao.delete(1L);
        assertSame(expected, comment);
        verify(this.dao.em).remove(argThat(new BaseMatcher<Comment>() {

            @Override
            public boolean matches(Object item) {
                if (!(item instanceof Comment)) {
                    return false;
                }

                return expected.equals((Comment) item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Should be: " + expected);
            }
        }));
    }

    @Test
    public void update() {
        Comment expected = new Comment(1L, "first comment");
        when(this.dao.em.merge(expected)).thenReturn(expected);

        Comment comment = dao.update(expected);

        assertSame(expected, comment);
    }
}
