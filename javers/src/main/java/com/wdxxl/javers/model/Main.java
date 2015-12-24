package com.wdxxl.javers.model;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;

public class Main {

    public static void main(String[] args) {
        Javers javers = JaversBuilder.javers().build();

        Person tommyOld = new Person("tommy", "Tommy Smart", "aa");
        Person tommyNew = new Person("tommy", "Tommy C. Smart", "a1Aa");

        Diff diff = javers.compare(tommyOld, tommyNew);
        System.out.println(diff);
    }
}
