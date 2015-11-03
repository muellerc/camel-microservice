package org.apache.cmueller.camel.microservice.jaxrs;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("v1")
public class ApplicationConfig extends Application {
    // add application specific configuration here, like filters, ...
}
