package org;

import lombok.extern.slf4j.Slf4j;
import org.tables.Place;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for testing connecting with our database.
 */
@Slf4j
public class Main {
    // Note, that name must match persistence-unit name in persistence.xml
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("dbs");

    public static void main(String[] args) {
        log.debug("--> Main().");

        /*
        try {
            Class.forName("org.postgresql.Driver");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbs");
            emf.close();
        } catch (ClassNotFoundException e) {
            log.error("Class not found.", e);
        }

         */
        getPlaces();


        ENTITY_MANAGER_FACTORY.close();
        log.debug("<-- Main().");
    }

    public static void addPlace(int id, String name, String url, String type, int is_part_of) {
        log.debug("--> addPlace().");

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            // Begin transaction
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            // Create new row using given parameters
            Place place = new Place();
            place.setId(id);
            place.setName(name);
            place.setUrl(url);
            place.setType(type);
            place.setIs_part_of(is_part_of);

            // Save the object
            entityManager.persist(place);
            entityTransaction.commit();

        } catch (Exception e) {
            // Roll back changes
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            log.error("Something went wrong here.", e);

        } finally {
            log.debug("<-- addPlace().");
            entityManager.close();
        }
    }

    public static void getPlace(int id) {
        log.debug("--> getPlace().");

        // Setup variables
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Place place = null;

        // Setup query
        String query = "SELECT c FROM Place c WHERE c.id = :id";
        TypedQuery<Place> typedQuery = entityManager.createQuery(query, Place.class);
        typedQuery.setParameter("id", id);

        // Run query
        try {
            place = typedQuery.getSingleResult();
            log.debug("id = {} \t name = {} \t type = {} \t is part of = {}",
                    place.getId(),
                    place.getName(),
                    place.getType(),
                    place.getIs_part_of());
        } catch (PersistenceException e) {
            log.error("Database persistence error.", e);
        } catch (Exception e) {
            log.error("Something went wrong here.", e);
        } finally {
            entityManager.close();
            log.debug("<-- getPlace().");
        }
    }

    public static void getPlaces() {
        log.debug("--> getPlaces().");

        // Setup variables
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Place> places = new ArrayList<>();

        // Setup query
        String query = "SELECT c FROM Place c WHERE c.id IS NOT NULL";
        //String query = "FROM Place";
        TypedQuery<Place> typedQuery = entityManager.createQuery(query, Place.class);

        // Run query
        try {
            places = typedQuery.getResultList();
            for (Place p : places) {
                log.debug("id = {} \t name = {} \t type = {} \t is part of = {}",
                        p.getId(),
                        p.getName(),
                        p.getType(),
                        p.getIs_part_of());
            }

        } catch (PersistenceException e) {
            log.error("Database persistence error.", e);
        } catch (Exception e) {
            log.error("Something went wrong here.", e);
        } finally {
            entityManager.close();
            log.debug("<-- getPlaces().");
        }
    }

    public static void changeName(int id, String name) {
        log.debug("--> changeName().");

        // Setup variables
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction entityTransaction = null;
        Place place = null;

        try {
            // Begin transaction
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            // Find the given id
            place = entityManager.find(Place.class, id);
            // TODO we should verify that id was found

            // Set the new name
            place.setName(name);

            // Save the object
            entityManager.persist(place);
            entityTransaction.commit();

        } catch (Exception e) {
            // Roll back changes
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            log.error("Something went wrong here.", e);

        } finally {
            log.debug("<-- changeName().");
            entityManager.close();
        }
    }

    public static void deletePlace(int id) {
        log.debug("--> deletePlace().");

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction entityTransaction = null;
        Place place = null;

        try {
            // Begin transaction
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            // Find id that is to be deleted
            place = entityManager.find(Place.class, id);
            // TODO we should verify that id was found

            // Delete place
            entityManager.remove(place);

            // Save the object
            entityManager.persist(place);
            entityTransaction.commit();

        } catch (Exception e) {
            // Roll back changes
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            log.error("Something went wrong here.", e);

        } finally {
            log.debug("<-- deletePlace().");
            entityManager.close();
        }
    }

}
