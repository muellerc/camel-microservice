package org.apache.cmueller.camel.microservice.jaxrs;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.cmueller.camel.microservice.dao.CommentDao;
import org.apache.cmueller.camel.microservice.model.Comment;

import java.util.Collection;
import java.util.UUID;

/**
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
 */
@Path("comments")
public class CommentsResourceImpl implements CommentsResource {

    @Inject
    private CommentDao dao;

    @Context
    private UriInfo uriInfo;

    @Override
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Comment> get() {
        return dao.get();
    }

    @Override
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Comment get(@PathParam("id") String id) {
        Comment comment = dao.get(id);

        if (comment == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return comment;
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Comment create(Comment buyer) {
        buyer.setId(UUID.randomUUID().toString());
        return dao.create(buyer);
    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Comment update(@PathParam("id") String id, Comment buyer) {
        return dao.update(buyer);
    }

    @Override
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        dao.delete(id);
    }
}