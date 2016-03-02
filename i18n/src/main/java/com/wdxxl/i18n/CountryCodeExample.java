package com.wdxxl.i18n;

import java.util.List;
import java.util.Locale;

import com.neovisionaries.i18n.CountryCode;

public class CountryCodeExample {

    public static void main(String[] args) {
        getByCode_JP();
        findByName_JP();
        getByLocale_CHINA();
        getByCode_CHINA();

    }

    public static void getByCode_JP() {
        CountryCode cc = CountryCode.getByCodeIgnoreCase("Jp");
        System.out.println("Country name = " + cc.getName()); // "Japan"
        System.out.println("ISO 3166-1 alpha-2 code = " + cc.getAlpha2()); // "JP"
        System.out.println("ISO 3166-1 alpha-3 code = " + cc.getAlpha3()); // "JPN"
        System.out.println("ISO 3166-1 numeric code = " + cc.getNumeric()); // 392
    }

    public static void findByName_JP() {
        List<CountryCode> ccs = CountryCode.findByName("Japan");
        for (CountryCode cc : ccs) {
            System.out.println("Country name = " + cc.getName()); // "Japan"
            System.out.println("ISO 3166-1 alpha-2 code = " + cc.getAlpha2()); // "JP"
            System.out.println("ISO 3166-1 alpha-3 code = " + cc.getAlpha3()); // "JPN"
            System.out.println("ISO 3166-1 numeric code = " + cc.getNumeric()); // 392
        }
    }

    public static void getByLocale_CHINA() {
        CountryCode cc = CountryCode.getByLocale(Locale.CHINA);
        System.out.println("Country name = " + cc.getName()); // "China"
        System.out.println("ISO 3166-1 alpha-2 code = " + cc.getAlpha2()); // "CN"
        System.out.println("ISO 3166-1 alpha-3 code = " + cc.getAlpha3()); // "CHN"
        System.out.println("ISO 3166-1 numeric code = " + cc.getNumeric()); // 156
        System.out.println("Locale = " + cc.toLocale());// zh_CN
    }

    public static void getByCode_CHINA() {
        CountryCode cc = CountryCode.getByCode(156);
        System.out.println("Country name = " + cc.getName()); // "China"
        System.out.println("ISO 3166-1 alpha-2 code = " + cc.getAlpha2()); // "CN"
        System.out.println("ISO 3166-1 alpha-3 code = " + cc.getAlpha3()); // "CHN"
        System.out.println("ISO 3166-1 numeric code = " + cc.getNumeric()); // 156
        System.out.println("Locale = " + cc.toLocale());// zh_CN
    }
}
