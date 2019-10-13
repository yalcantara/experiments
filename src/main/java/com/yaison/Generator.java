package com.yaison;


import com.thedeanda.lorem.LoremIpsum;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Generator {

    public static final int MAX_AGE = 70;

    public static void main(String[] args) {

        Generator g = new Generator();


        int BATCH_SIZE = 10;
        int count = 100;

        int batch = count / BATCH_SIZE;

        for(int i =0; i < batch; i++){
            List<Contact> batchList = g.generate(BATCH_SIZE);

        }



    }


    private final LoremIpsum lorem;

    private final Random rand;
    private final AtomicLong idGennerator;

    public Generator(){
        lorem = new LoremIpsum();
        rand = new Random();
        idGennerator = new AtomicLong();
    }

    public List<Contact> generate(int count){
        List<Contact> list = new ArrayList<>();
        for(int i =0; i < count; i++){
            list.add(generate());
        }

        return list;
    }

    public Contact generate(){


        String fname;
        
        
        Character gender = (rand.nextBoolean())? 'M' : 'F';

        if(gender == 'M'){
            fname = lorem.getFirstNameMale();
        }else{
            fname = lorem.getFirstNameFemale();
        }

        String lname = lorem.getLastName();
        String email = lorem.getEmail();
        String phone = lorem.getPhone();

        String state = lorem.getStateFull();
        String city = lorem.getCity();
        String country = lorem.getCountry();
        String zip = lorem.getZipCode();



        long id = idGennerator.incrementAndGet();


        LocalDate birthDate = generateBirthDate();

        String addr = city + ", " + state +", " + country +", " + zip + ".";

        Contact c = new Contact();
        c.setId(id);
        c.setFirstName(fname);
        c.setLastName(lname);
        c.setGender(gender);
        c.setBirthDate(birthDate);
        c.setEmail(email);
        c.setPhone(phone);
        c.setAddress(addr);

        return c;
    }

    private LocalDate generateBirthDate(){
        OffsetDateTime end = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime start = end.minus(MAX_AGE, ChronoUnit.YEARS);

        long startMilli = start.toInstant().toEpochMilli();
        long endMilli = end.toInstant().toEpochMilli();

        long durationTime = endMilli - startMilli;
        long randMilli = (long)(rand.nextDouble() * durationTime);



        long dateMilli = startMilli + randMilli;

        Instant dateInstant = Instant.ofEpochMilli(dateMilli);

        LocalDate birthDate = OffsetDateTime.ofInstant(dateInstant, ZoneOffset.UTC).toLocalDate();


        return birthDate;
    }
}
