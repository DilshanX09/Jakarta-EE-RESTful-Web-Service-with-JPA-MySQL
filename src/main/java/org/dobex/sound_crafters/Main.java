package org.dobex.sound_crafters;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.dobex.sound_crafters.configuration.ApplicationConfiguration;
import org.glassfish.jersey.servlet.ServletContainer;

public class Main {

    private static final int SERVER_PORT = 5000;
    private static final String CONTEXT_PATH = "/sound-crafters";

    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(SERVER_PORT);
        tomcat.getConnector();
        Context context = tomcat.addContext(CONTEXT_PATH, null);
        Tomcat.addServlet(context, "JerseyServlet", new ServletContainer(new ApplicationConfiguration()));
        context.addServletMappingDecoded("/api/v1/*", "JerseyServlet");

        FilterDef corsFilterDef = new FilterDef();
        corsFilterDef.setFilterName("CorsFilter");
        corsFilterDef.setFilterClass("org.dobex.sound_crafters.filters.CorsFilter");
        context.addFilterDef(corsFilterDef);

        FilterMap corsFilterMap = new FilterMap();
        corsFilterMap.setFilterName("CorsFilter");
        corsFilterMap.addURLPattern("/*");
        context.addFilterMap(corsFilterMap);

        try {
            tomcat.start();
            System.out.println("http://localhost:" + SERVER_PORT + CONTEXT_PATH);
        } catch (LifecycleException e) {
            throw new RuntimeException("Tomcat server startup failed: " + e.getMessage());
        }
    }
}
