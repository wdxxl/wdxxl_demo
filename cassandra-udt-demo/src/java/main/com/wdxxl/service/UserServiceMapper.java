package com.wdxxl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.wdxxl.model.Address;
import com.wdxxl.model.Fullname;
import com.wdxxl.model.Users;

public class UserServiceMapper {
    private static Cluster cluster;
    private static Session session;

    public static void main(String[] args) {
        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect("wdxxl");

        Mapper<Users> usersMapper = new MappingManager(session).mapper(Users.class);

        // Select
        Users user = usersMapper.get(UUID.fromString("62c36092-82a1-3a00-93d1-46196ee77204"));
        System.out.println(user.toString());

        // insert
        Fullname fullname = new Fullname("king", "wang");
        Fullname rep1 = new Fullname("direct", "reports1");
        Fullname rep2 = new Fullname("direct", "reports2");
        Set<Fullname> directs = new HashSet<>();
        directs.add(rep1);
        directs.add(rep2);
        List<String> phones = new ArrayList<>();
        phones.add("123");
        phones.add("234");
        Address address = new Address("street", "city", 315324, phones);
        Map<String, Address> homeAddress = new HashMap<>();
        homeAddress.put("home", address);
        UUID uuid = UUID.randomUUID();
        Users newUser = new Users(uuid, fullname, directs, homeAddress);
        usersMapper.save(newUser);

        // select
        Users user2 = usersMapper.get(uuid);
        System.out.println(user2.toString());

        // delete
        usersMapper.delete(user2);

        cluster.close();
    }
}
