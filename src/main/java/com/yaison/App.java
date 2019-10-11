package com.yaison;

import java.time.*;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        LocalDateTime d1 = LocalDateTime.now();
        Date d2 = new Date();

        OffsetDateTime d3 = d1.atOffset(ZoneOffset.UTC);
        System.out.println(d3.toInstant().toEpochMilli());
        System.out.println(d2.getTime());
    }
}
