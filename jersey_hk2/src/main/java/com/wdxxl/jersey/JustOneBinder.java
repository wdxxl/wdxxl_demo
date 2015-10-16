package com.wdxxl.jersey;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class JustOneBinder extends AbstractBinder {
    @Override
    protected void configure() {
        // bind(new JustOne().to(JustOne.class); //the same result as below.
        bind(JustOne.class).to(JustOne.class).in(Singleton.class);
    }
}