package org;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Main class for testing connecting with our database.
 */
@Slf4j
public class Main {
    // Note, that name must match persistence-unit name in persistence.xml
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("dbs_prak");

    public static void main(String[] args) {
        log.debug("--> Main().");

        System.out.println("Hello World!");
        log.debug("Hello Logger!");




        ENTITY_MANAGER_FACTORY.close();
        log.debug("<-- Main().");
    }

    public static void addPlace(int id, String name, String url, String type, int is_part_of) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

    }
}
