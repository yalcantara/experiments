package com.yaison;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ContactService {


    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(List<Contact> contacts){

        for(Contact c:contacts){
            em.persist(c);
        }

        em.flush();
    }
}
