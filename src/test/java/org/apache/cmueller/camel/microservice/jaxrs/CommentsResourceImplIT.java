package org.apache.cmueller.camel.microservice.jaxrs;

import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.cmueller.camel.microservice.dao.CommentDao;
import org.apache.cmueller.camel.microservice.model.Comment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CommentsResourceImplIT {

    @Deployment(testable = false)
    public static Archive<WebArchive> deploy() throws Exception {
        return ShrinkWrap.create(WebArchive.class, "camel-microservices.war")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            /*.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").
                resolve("commons-lang:commons-lang").withoutTransitivity().asFile())*/
            .addPackage(CommentDao.class.getPackage())
            .addPackage(ApplicationConfig.class.getPackage())
            .addPackage(Comment.class.getPackage());
    }

    private Client client;
    private WebTarget target;;

    @ArquillianResource
    private URL baseUrl;

    @Before
    public void setup() throws Exception {
        client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(baseUrl, "v1/comments").toExternalForm()));
        target.register(Comment.class);
    }

    @After
    public void tearDown() {
        if (client != null) {
            client.close();
        }
    }

    @Test
    @InSequence(1)
    public void initialize() throws Exception {
        Comment comment = new Comment();
        comment.setText("This is the first comment");

        comment = target.request().post(Entity.entity(comment, MediaType.APPLICATION_JSON), Comment.class);

        assertNotNull(comment.getId());
    }
}