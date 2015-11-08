package org.apache.cmueller.camel.microservice.camel;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.cmueller.camel.microservice.dao.CommentDao;

@ApplicationScoped
@Startup
@ContextName("comments-context")
public class CommentsRouteBuilder extends RouteBuilder {

    @Inject
    private CommentDao dao;

    @Override
    public void configure() throws Exception {
        from("direct:get-all-comments").routeId("get-all-comments")
            .bean(dao, "get()");

        from("direct:get-comment").routeId("get-comment")
            .bean(dao, "get(${body})");

        from("direct:create-comment").routeId("create-comment")
            .bean(dao, "create(${body})");

        from("direct:update-comment").routeId("update-comment")
            .bean(dao, "update(${body})");

        from("direct:delete-comment").routeId("delete-comment")
            .bean(dao, "delete(${body})");
    }
}