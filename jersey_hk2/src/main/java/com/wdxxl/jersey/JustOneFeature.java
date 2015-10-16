package com.wdxxl.jersey;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

public class JustOneFeature implements Feature {

    @Override
    public boolean configure(final FeatureContext context) {
        context.register(new JustOneBinder());
        return true;
    }
}