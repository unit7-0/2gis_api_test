package com.unit7.gis.api.rest;

import com.google.common.collect.Sets;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by breezzo on 15.11.15.
 */
public class RestApplication extends Application {

    private static final Set<Class<?>> CLASSES = Sets.newHashSet(RestAreaService.class);

    @Override
    public Set<Class<?>> getClasses() {
        return CLASSES;
    }

    @Override
    public Set<Object> getSingletons() {

        Set<Object> singletons = new HashSet<>();

        ObjectMapper mapper = new ObjectMapper();
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();

        provider.setMapper(mapper);
        singletons.add(provider);

        return singletons;
    }
}
