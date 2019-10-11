package com.yaison;

import org.dom4j.tree.BackedList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootConfiguration
@ComponentScan("com.yaison")
public class ContactInserter {


    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(ContactInserter.class);

        ContactService srv = ctx.getBean(ContactService.class);

        int BATCH_SIZE = 10;
        int count = 100;

        int batch = count / BATCH_SIZE;

        Generator g = new Generator();

        int saved = 0;
        for(int i =0; i < batch; i++){
            List<Contact> batchList = g.generate(BATCH_SIZE);

            srv.save(batchList);
            saved += batchList.size();

            System.out.printf("Saved: %,10d\n", saved);

        }
    }

}
