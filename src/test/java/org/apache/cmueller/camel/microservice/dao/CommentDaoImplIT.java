package org.apache.cmueller.camel.microservice.dao;

import org.apache.cmueller.camel.microservice.model.Comment;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by cmueller on 31/12/15.
 */
public class CommentDaoImplIT {

    private static CommentDaoImpl dao;
    private static EntityManager em;
    private static EntityTransaction tx;

    @BeforeClass
    public static void setUpClass() {
        em = Persistence.createEntityManagerFactory("it").createEntityManager();
        tx = em.getTransaction();

        dao = new CommentDaoImpl();
        dao.em = em;
    }

    @Before
    public void setUp() {
        this.tx.begin();
    }

    @After
    public void tearDown() {
        this.tx.rollback();
    }

    @Test
    public void create() {
        Comment comment = dao.create(new Comment("first comment"));

        assertNotNull(comment.getId());
        assertEquals(1, count());
    }

    @Test
    public void update() {
        Comment comment = dao.create(new Comment("first comment"));
        comment.setText("first comment updated");

        dao.update(comment);

        assertNotNull(comment.getId());
        assertEquals(1, count());
    }

    @Test
    public void get() {
        Comment comment = dao.create(new Comment("first comment"));

        comment = dao.get(comment.getId());

        assertNotNull(comment.getId());
        assertEquals("first comment", comment.getText());
        assertEquals(1, count());
    }

    @Test
    public void getAll() {
        dao.create(new Comment("first comment"));
        dao.create(new Comment("second comment"));

        List<Comment> comments = dao.get();

        assertEquals(2, comments.size());
    }

    @Test
    public void delete() {
        Comment comment = dao.create(new Comment("first comment"));

        dao.delete(comment.getId());

        assertNotNull(comment.getId());
        assertEquals(0, count());
    }

    private long count() {
        return em.createQuery("SELECT COUNT(c) from Comment c", Long.class).getSingleResult();
    }
}
