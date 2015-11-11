package com.wdxxl.service;

import java.util.Map;
import java.util.Set;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.UDTMapper;
import com.wdxxl.model.Address;
import com.wdxxl.model.Fullname;

public class UserServiceCql {
    private static Cluster cluster;
    private static Session session;

    public static void main(String[] args) {
        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect("wdxxl");

        UDTMapper<Address> addressMapper = new MappingManager(session).udtMapper(Address.class);
        UDTMapper<Fullname> fullnameMapper = new MappingManager(session).udtMapper(Fullname.class);

        ResultSet results =
                session.execute("SELECT * FROM wdxxl.users "
                        + "WHERE id =62c36092-82a1-3a00-93d1-46196ee77204;");
        for (Row row : results) {
            System.out.println(row.getUUID("id"));

            UDTValue name = row.getUDTValue("name");
            System.out.println(name);

            Fullname fname = fullnameMapper.fromUDT(name);
            System.out.println(fname.toString());

            Set<UDTValue> fullname = row.getSet("direct_reports", UDTValue.class);
            System.out.println(fullname);

            Map<String, UDTValue> addresses = row.getMap("addresses", String.class, UDTValue.class);
            System.out.println(addresses);

            for (String key : addresses.keySet()) {
                Address address = addressMapper.fromUDT(addresses.get(key));
                System.out.println(address.toString());
            }
        }

        cluster.close();
    }
}
