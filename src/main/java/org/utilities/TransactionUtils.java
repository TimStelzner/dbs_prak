package org.utilities;

import lombok.extern.slf4j.Slf4j;
import org.Main;
import org.apache.commons.text.WordUtils;
import org.tables.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    public static void getCompany(long id) throws PersistenceException {
        log.debug("--> getCompany().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        Company company = null;

        // Setup query
        String query = "SELECT c FROM Company c WHERE c.id = :id";
        TypedQuery<Company> typedQuery = entityManager.createQuery(query, Company.class);
        typedQuery.setParameter("id", id);

        // Run query
        company = typedQuery.getSingleResult();

        log.debug("id = {} \t name = {} \t type = {} \t country_id = {}",
                company.getId(),
                company.getName(),
                company.getCountry_id()
        );

        entityManager.close();
        log.debug("<-- getCompany().");
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

    public static void getPersonIsMemberOfForums(long id) throws PersistenceException {
        log.debug("--> getPersonIsMemberOfForums().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        Person person = null;

        // Setup query
        String query = "SELECT c FROM Person c WHERE c.id = :id";
        TypedQuery<Person> typedQuery = entityManager.createQuery(query, Person.class);
        typedQuery.setParameter("id", id);

        // Run query
        person = typedQuery.getSingleResult();
        Set<Forum> forums = person.getForums();
        Iterator<Forum> iterator = forums.iterator();

        log.debug("id = {} \t name = {} is members of these forums:", person.getId(), person.getName());
        while (iterator.hasNext()) {
            Forum next = iterator.next();
            log.debug("id = [{}] \t name = [{}] ", next.getId(), next.getTitle());
        }

        entityManager.close();
        log.debug("<-- getPersonIsMemberOfForums().");
    }

    public static void getComment(Long id) throws PersistenceException {
        log.debug("--> getComment().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        Comment comment = null;

        // Setup query
        String query = "SELECT c FROM Comment c WHERE c.id = :id";
        TypedQuery<Comment> typedQuery = entityManager.createQuery(query, Comment.class);
        typedQuery.setParameter("id", id);

        // Run query
        comment = typedQuery.getSingleResult();

        log.debug("id = {} \t creation date = {} \t content = {} \t reply of post = {} \t reply of comment = {}",
                comment.getId(),
                comment.getCreation_date(),
                comment.getContent(),
                comment.getReply_of_post(),
                comment.getReply_of_comment()
        );

        entityManager.close();
        log.debug("<-- getComment().");
    }

    public static void getPost(Long id) throws PersistenceException {
        log.debug("--> getPost().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        Post post = null;

        // Setup query
        String query = "SELECT c FROM Post c WHERE c.id = :id";
        TypedQuery<Post> typedQuery = entityManager.createQuery(query, Post.class);
        typedQuery.setParameter("id", id);

        // Run query
        post = typedQuery.getSingleResult();

        log.debug("id = {} \t creation date = {} \t content = {} ",
                post.getId(),
                post.getCreation_date(),
                post.getContent()
        );

        entityManager.close();
        log.debug("<-- getPost().");
    }

    public static void getTag(Long id) throws PersistenceException {
        log.debug("--> getTag().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        Tag tag = null;

        // Setup query
        String query = "SELECT c FROM Tag c WHERE c.id = :id";
        TypedQuery<Tag> typedQuery = entityManager.createQuery(query, Tag.class);
        typedQuery.setParameter("id", id);

        // Run query
        tag = typedQuery.getSingleResult();

        log.debug("id = {} \t name = {} \t url = {} ",
                tag.getId(),
                tag.getName(),
                tag.getUrl()
        );

        entityManager.close();
        log.debug("<-- getTag().");
    }

    public static void getCommentHasTag(Long id) throws PersistenceException {
        log.debug("--> getTag().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        Tag tag = null;

        // Setup query
        String query = "SELECT c FROM Tag c WHERE c.id = :id";
        TypedQuery<Tag> typedQuery = entityManager.createQuery(query, Tag.class);
        typedQuery.setParameter("id", id);

        // Run query
        tag = typedQuery.getSingleResult();

        log.debug("id = {} \t name = {} \t url = {} ",
                tag.getId(),
                tag.getName(),
                tag.getUrl()
        );

        entityManager.close();
        log.debug("<-- getTag().");
    }

    public static void selectAll(String table) throws PersistenceException, ClassNotFoundException {
        log.debug("--> select({}).", table);

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        // Class cls = Class.forName(table);
        // log.debug("cls = {}", cls);

        StringBuilder query = new StringBuilder("from ");
        query.append(table);

        Query typedQuery = entityManager.createQuery(query.toString());
        List<?> resultList = typedQuery.getResultList();
        String capitalize = WordUtils.capitalize(table);
        log.debug("capitalize = {}", capitalize);

        switch (table) {
            case "City":
                printCities((List<City>) resultList);
                break;
        }


    }

    private static void printCities(List<City> cities) {
        log.debug("--> printCities().");
        for (City city : cities) {
            log.debug("name = {} \t id = {} \t type = {} \t part of = {}",
                    city.getName(),
                    city.getId(),
                    city.getType(),
                    city.getIs_part_of()
            );
        }
        log.debug("<-- printCities().");
    }


}
