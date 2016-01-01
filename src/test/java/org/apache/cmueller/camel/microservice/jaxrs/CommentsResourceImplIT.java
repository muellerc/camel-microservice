package org.apache.cmueller.camel.microservice.jaxrs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.apache.cmueller.camel.microservice.camel.CommentsRouteBuilder;
import org.apache.cmueller.camel.microservice.dao.CommentDao;
import org.apache.cmueller.camel.microservice.dao.CommentDaoImpl;
import org.apache.cmueller.camel.microservice.model.Comment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CommentsResourceImplIT {

    @Deployment(testable = false)
    public static Archive<WebArchive> deploy() throws Exception {
        PomEquippedResolveStage resolver = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create(WebArchive.class, "camel-microservice.war")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsLibraries(resolver.resolve("org.apache.camel:camel-cdi").withTransitivity().asFile())
            .addClasses(Comment.class, CommentsRouteBuilder.class, CommentDao.class, CommentDaoImpl.class, ApplicationConfig.class, CommentsResource.class, CommentsResourceImpl.class);
    }

    private Client client;

    @ArquillianResource
    private URL baseUrl;

    @Before
    public void setup() throws Exception {
        client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() {
        if (client != null) {
            client.close();
        }
    }

    @Test
    @InSequence(1)
    public void create() throws Exception {
        Comment comment1 = new Comment();
        comment1.setText("This is the first comment");

        Comment comment = client.target(new URL(baseUrl, "v1/comments").toString())
            .request()
            .post(Entity.entity(comment1, MediaType.APPLICATION_JSON), Comment.class);

        assertNotNull(comment.getId());
        assertEquals("This is the first comment", comment.getText());

        //

        Comment comment2 = new Comment();
        comment2.setText("This is my second comment");

        comment = client.target(new URL(baseUrl, "v1/comments").toString())
            .request()
            .post(Entity.entity(comment2, MediaType.APPLICATION_JSON), Comment.class);

        assertNotNull(comment.getId());
        assertEquals("This is my second comment", comment.getText());
    }

    @SuppressWarnings("unchecked")
    @Test
    @InSequence(2)
    public void getAll() throws Exception {
        List<Comment> comments = client.target(new URL(baseUrl, "v1/comments").toString())
            .request()
            .get(new GenericType<List<Comment>>(){});

        assertEquals(2, comments.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    @InSequence(3)
    public void get() throws Exception {
        List<Comment> comments = client.target(new URL(baseUrl, "v1/comments").toString())
            .request()
            .get(new GenericType<List<Comment>>(){});
        Long id = comments.get(0).getId();
        String text = comments.get(0).getText();

        Comment comment = client.target(new URL(baseUrl, "v1/comments").toString())
            .path("{id}")
            .resolveTemplate("id", id)
            .request()
            .get(Comment.class);
        

        assertEquals(id, comment.getId());
        assertEquals(text, comment.getText());
    }

    @SuppressWarnings("unchecked")
    @Test
    @InSequence(4)
    public void update() throws Exception {
        List<Comment> comments = client.target(new URL(baseUrl, "v1/comments").toString())
            .request()
            .get(new GenericType<List<Comment>>(){});

        assertEquals(2, comments.size());

        Long id = comments.get(0).getId();

        Comment comment = new Comment();
        comment.setId(id);
        comment.setText("This is my third comment");

        comment = client.target(new URL(baseUrl, "v1/comments").toString())
            .path("{id}")
            .resolveTemplate("id", id)
            .request()
            .put(Entity.entity(comment, MediaType.APPLICATION_JSON), Comment.class);
        

        assertEquals(id, comment.getId());
        assertEquals("This is my third comment", comment.getText());

        comments = client.target(new URL(baseUrl, "v1/comments").toString())
                .request()
                .get(new GenericType<List<Comment>>(){});

            assertEquals(2, comments.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    @InSequence(5)
    public void delete() throws Exception {
        List<Comment> comments = client.target(new URL(baseUrl, "v1/comments").toString())
            .request()
            .get(new GenericType<List<Comment>>(){});

        assertEquals(2, comments.size());

        client.target(new URL(baseUrl, "v1/comments").toString())
            .path("{id}")
            .resolveTemplate("id", comments.get(0).getId())
            .request()
            .delete();

        comments = client.target(new URL(baseUrl, "v1/comments").toString())
            .request()
            .get(new GenericType<List<Comment>>(){});

            assertEquals(1, comments.size());
    }
}