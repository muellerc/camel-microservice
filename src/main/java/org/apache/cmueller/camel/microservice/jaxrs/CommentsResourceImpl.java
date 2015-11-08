package org.apache.cmueller.camel.microservice.jaxrs;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.cdi.ContextName;
import org.apache.cmueller.camel.microservice.model.Comment;

/**
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
 */
@Path("comments")
public class CommentsResourceImpl implements CommentsResource {

    @Inject
    @ContextName("comments-context")
    private CamelContext context;

    @EndpointInject(uri = "direct:get-all-comments")
    private ProducerTemplate getAllComments;

    @EndpointInject(uri = "direct:get-comment")
    private ProducerTemplate getComment;

    @EndpointInject(uri = "direct:create-comment")
    private ProducerTemplate createComment;

    @EndpointInject(uri = "direct:update-comment")
    private ProducerTemplate updateComment;

    @EndpointInject(uri = "direct:delete-comment")
    private ProducerTemplate deleteComment;

    @SuppressWarnings("unchecked")
    @Override
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Comment> getAll() {
        return getAllComments.requestBody((Object) null, Collection.class);
    }

    @Override
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Comment get(@PathParam("id") String id) {
        return getComment.requestBody((Object) id, Comment.class);
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Comment create(Comment comment) {
        return createComment.requestBody(comment, Comment.class);
    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Comment update(@PathParam("id") String id, Comment comment) {
        comment.setId(id);
        return updateComment.requestBody(comment, Comment.class);
    }

    @Override
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        deleteComment.requestBody((Object) id);
    }
}