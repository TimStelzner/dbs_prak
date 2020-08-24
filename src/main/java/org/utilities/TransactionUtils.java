package org.utilities;

import lombok.extern.slf4j.Slf4j;
import org.Main;
import org.tables.Person;
import org.tables.University;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

@Slf4j
public class TransactionUtils {

    public static void getUniversity(long id) {
        log.debug("--> getUniversity().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        University university = null;

        // Setup query
        String query = "SELECT c FROM University c WHERE c.id = :id";
        TypedQuery<University> typedQuery = entityManager.createQuery(query, University.class);
        typedQuery.setParameter("id", id);

        // Run query
        try {
            university = typedQuery.getSingleResult();

            log.debug("id = {} \t name = {} \t type = {} \t is part of = {}",
                    university.getId(),
                    university.getName(),
                    university.getCity_id());
        } catch (PersistenceException e) {
            log.error("Database persistence error.", e);
        } catch (Exception e) {
            log.error("Something went wrong here.", e);
        } finally {
            entityManager.close();
            log.debug("<-- getUniversity().");
        }
    }

    public static void getPerson(long id) {
        log.debug("--> getPerson().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        Person person = null;

        // Setup query
        String query = "SELECT c FROM Person c WHERE c.id = :id";
        TypedQuery<Person> typedQuery = entityManager.createQuery(query, Person.class);
        typedQuery.setParameter("id", id);

        // Run query
        try {
            person = typedQuery.getSingleResult();

            log.debug("id = {} \t surname = {} \t name = {}",
                    person.getId(),
                    person.getSurname(),
                    person.getName());
            String[] emails = person.getEmails();
            String listOfEmails = "";
            for (String email : emails) {
                listOfEmails = listOfEmails + "[" + email + "]";
            }
            log.debug("listOfEmails = {}", listOfEmails);
        } catch (PersistenceException e) {
            log.error("Database persistence error.", e);
        } catch (Exception e) {
            log.error("Something went wrong here.", e);
        } finally {
            entityManager.close();
            log.debug("<-- getPerson().");
        }
    }

}
