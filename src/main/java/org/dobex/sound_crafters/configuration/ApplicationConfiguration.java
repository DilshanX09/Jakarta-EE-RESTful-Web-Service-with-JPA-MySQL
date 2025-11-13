package org.dobex.sound_crafters.configuration;

import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationConfiguration extends ResourceConfig {
    public ApplicationConfiguration(){
        packages("org.dobex.sound_crafters.controller");
    }
}
