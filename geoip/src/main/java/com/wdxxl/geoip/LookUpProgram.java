package com.wdxxl.geoip;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class LookUpProgram {

    public static void main(String... args) throws UnknownHostException {

        long ipAddress = new BigInteger(InetAddress.getByName("72.229.28.185").getAddress()).longValue();

        System.out.println("By String IP address: \n" +
                GeoIPv4.getLocation("72.229.28.185"));

        System.out.println("By long IP address: \n" +
                GeoIPv4.getLocation(ipAddress));

        System.out.println("By InetAddress IP address: \n"
                + GeoIPv4.getLocation(InetAddress.getByName("72.229.28.185")));

        System.out.println("By String IP address: \n" + GeoIPv4.getLocation("54.230.248.27"));
        // GeoLocation{countryCode='US', countryName='United States', postalCode='98144',
        // city='Seattle', region='WA', areaCode=206, dmaCode=819, metroCode=819,
        // latitude=47.583893, longitude=-122.2995}

    }
}
