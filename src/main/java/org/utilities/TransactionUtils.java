package org.utilities;

import lombok.extern.slf4j.Slf4j;
import org.Main;
import org.tables.City;
import org.tables.Forum;
import org.tables.Person;
import org.tables.University;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

@Slf4j
public class TransactionUtils {

    public static void getUniversity(long id) throws PersistenceException {
        log.debug("--> getUniversity().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        University university = null;

        // Setup query
        String query = "SELECT c FROM University c WHERE c.id = :id";
        TypedQuery<University> typedQuery = entityManager.createQuery(query, University.class);
        typedQuery.setParameter("id", id);

        // Run query
        university = typedQuery.getSingleResult();

        log.debug("id = {} \t name = {} \t type = {} \t is part of = {}",
                university.getId(),
                university.getName(),
                university.getCity_id());

        entityManager.close();
        log.debug("<-- getUniversity().");
    }

    public static void getPerson(long id) throws PersistenceException {
        log.debug("--> getPerson().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        Person person = null;

        // Setup query
        String query = "SELECT c FROM Person c WHERE c.id = :id";
        TypedQuery<Person> typedQuery = entityManager.createQuery(query, Person.class);
        typedQuery.setParameter("id", id);

        // Run query
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

        entityManager.close();
        log.debug("<-- getPerson().");
    }

    public static void getCity(long id) throws PersistenceException {
        log.debug("--> getCity().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        City city = null;

        // Setup query
        String query = "SELECT c FROM City c WHERE c.id = :id";
        TypedQuery<City> typedQuery = entityManager.createQuery(query, City.class);
        typedQuery.setParameter("id", id);

        // Run query
        city = typedQuery.getSingleResult();

        log.debug("id = {} \t name = {} \t type = {} \t is part of = {}",
                city.getId(),
                city.getName(),
                city.getType(),
                city.getIs_part_of());

        entityManager.close();
        log.debug("<-- getCity().");
    }

    public static void getForum(long id) throws PersistenceException {
        log.debug("--> getForum().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        Forum forum = null;

        // Setup query
        String query = "SELECT c FROM Forum c WHERE c.id = :id";
        TypedQuery<Forum> typedQuery = entityManager.createQuery(query, Forum.class);
        typedQuery.setParameter("id", id);

        // Run query
        forum = typedQuery.getSingleResult();

        log.debug("id = {} \t title = {} \t created on = {} \t Moderator = {}",
                forum.getId(),
                forum.getTitle(),
                forum.getCreation_date(),
                forum.getPerson_id());

        entityManager.close();
        log.debug("<-- getForum().");
    }

}
