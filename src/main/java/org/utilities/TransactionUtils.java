package org.utilities;

import lombok.extern.slf4j.Slf4j;
import org.Main;
import org.tables.*;
import org.tables.composite.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
public class TransactionUtils {

    public static void selectAll(String table) throws PersistenceException {
        log.debug("--> select({}).", table);

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        // Class cls = Class.forName(table);
        // log.debug("cls = {}", cls);

        StringBuilder query = new StringBuilder("from ");
        query.append(table);

        Query typedQuery = entityManager.createQuery(query.toString());
        List<?> resultList = typedQuery.getResultList();
        // String capitalize = WordUtils.capitalize(table);
        // log.debug("capitalize = {}", capitalize);

        switch (table) {
            case "City":
                printCities((List<City>) resultList);
                break;
            case "Country":
                printCountries((List<Country>) resultList);
                break;
            case "Comment":
                printComments((List<Comment>) resultList);
                break;
            case "University":
                printUniversities((List<University>) resultList);
                break;
            case "Person":
                printPersons((List<Person>) resultList);
                break;
            case "Forum":
                printForums((List<Forum>) resultList);
                break;
            case "Post":
                printPost((List<Post>) resultList);
                break;
            case "PersonWorksAt":
                printWorksAt((List<PersonWorksAt>) resultList);
            case "PersonHasInterest":
                printHasInterest((List<PersonHasInterest>) resultList);
            case "PersonKnowsPerson":
                printPersonKnows((List<PersonKnowsPerson>) resultList);
            case SqlUtils.P_LIKES_COMMENT:
                printPersonLikesComment((List<PersonLikesComment>) resultList);
                break;
            case SqlUtils.P_LIKES_POST:
                printPersonLikesPost((List<PersonLikesPost>) resultList);
                break;
            case SqlUtils.P_STUDIES_AT:
                printPersonStudiesAt((List<PersonStudiesAt>) resultList);
        }
    }

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
                university.getCity());

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
                company.getCountry()
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
                city.getIsPartOf());

        entityManager.close();
        log.debug("<-- getCity().");
    }

    public static void getPost(long id) throws PersistenceException {
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

        log.debug("id = {} \t content = {} \t person = {} \t country = {} \t forum = {}",
                post.getId(),
                post.getContent(),
                post.getPerson(),
                post.getCountry(),
                post.getForum());
        entityManager.close();
        log.debug("<-- getPost().");
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
                forum.getCreationDate(),
                forum.getPersonId());

        entityManager.close();
        log.debug("<-- getForum().");
    }

    /*
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

     */

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
                comment.getCreationDate(),
                comment.getContent(),
                comment.getReplyOfPost(),
                comment.getReplyOfComment()
        );

        entityManager.close();
        log.debug("<-- getComment().");
    }

    /*
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
                post.getCreationDate(),
                post.getContent()
        );

        entityManager.close();
        log.debug("<-- getPost().");
    }

     */

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

    private static void printCities(List<City> cities) {
        log.debug("--> printCities().");
        for (City city : cities) {
            log.info("name = {} \t id = {} \t type = {} \t part of = {}",
                    city.getName(),
                    city.getId(),
                    city.getType(),
                    city.getIsPartOf());
        }
        log.debug("<-- printCities().");
    }


    private static void printCountries(List<Country> countries) {
        log.debug("--> printCountries().");
        for (Country country : countries) {
            log.info("name = {} \t id = {} \t type = {} \t part of = {}",
                    country.getName(),
                    country.getId(),
                    country.getType(),
                    country.getIsPartOf());
        }
        log.debug("<-- printCountries().");
    }


    private static void printComments(List<Comment> comments) {
        log.debug("--> printCountries().");
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            log.info("id = {} \t content = {} \t reply of post = {} \t reply of comment = {}",
                    comment.getId(),
                    comment.getContent(),
                    comment.getReplyOfPost(),
                    comment.getReplyOfComment()
            );
            if (i > 9) {
                break;
            }
        }
        log.debug("<-- printCountries().");
    }

    private static void printUniversities(List<University> universities) {
        log.debug("--> printUniversities().");
        for (University uni : universities) {
            log.info("id = {} \t name = {} \t city id = {}",
                    uni.getId(),
                    uni.getName(),
                    uni.getCity());
        }
        log.debug("<-- printUniversities().");
    }

    private static void printPersons(List<Person> persons) {
        log.debug("--> printPersons().");

        for (Person person : persons) {
            log.info("name = {} \t gender = {} \t bday = {} \t joined = {} \t from = {}",
                    person.getName(),
                    person.getGender(),
                    person.getBirthday(),
                    person.getCreationDate(),
                    person.getCityId());
        }
        log.debug("<-- printPersons().");
    }

    private static void printForums(List<Forum> forums) {
        log.debug("--> printForums().");

        for (Forum forum : forums) {
            log.info("id = {} \t title = {} \t created = {} \t Moderator = {}",
                    forum.getId(),
                    forum.getTitle(),
                    forum.getCreationDate(),
                    forum.getPersonId());
        }
        log.debug("<-- printForums().");
    }

    private static void printPost(List<Post> posts) {
        log.debug("--> printPost().");
        int counter = 1;
        for (Post post : posts) {
            log.info("id = {} \t content = {} \t image = {} \t person = {} \t forum = {} \t country = {}",
                    post.getId(),
                    post.getContent(),
                    post.getImageFile(),
                    post.getPerson().getId(),
                    post.getForum().getId(),
                    post.getCountry().getId());
            counter++;
            if (counter > 20) {
                break;
            }
        }
        log.debug("<-- printPost().");
    }

    private static void printWorksAt(List<PersonWorksAt> persons) {
        log.debug("--> printWorksAt().");
        for (PersonWorksAt person : persons) {
            log.info("id = {} \t name = {} \t conpany id = {} \t company = {}",
                    person.getPerson().getId(),
                    person.getPerson().getName(),
                    person.getCompany().getId(),
                    person.getCompany().getName());
        }
        log.debug("<-- printWorksAt().");
    }

    private static void printHasInterest(List<PersonHasInterest> persons) {
        log.debug("--> printHasInterest().");
        int counter = 1;
        for (PersonHasInterest person : persons) {
            log.info("id = {} \t name = {} \t tag id = {} \t tag = {}",
                    person.getPerson().getId(),
                    person.getPerson().getName(),
                    person.getTag().getId(),
                    person.getTag().getName());
            counter++;
            if (counter > 15) {
                break;
            }
        }
        log.debug("<-- printHasInterest().");
    }

    private static void printPersonKnows(List<PersonKnowsPerson> persons) {
        log.debug("--> printPersonKnows().");
        int counter = 1;
        for (PersonKnowsPerson person : persons) {
            log.info("id = {} \t name = {} \t knows id = {} \t name = {}",
                    person.getPerson1().getId(),
                    person.getPerson1().getName(),
                    person.getPerson2().getId(),
                    person.getPerson2().getName());
            counter++;
            if (counter > 15) {
                break;
            }
        }
        log.debug("<-- printPersonKnows().");
    }

    private static void printPersonLikesComment(List<PersonLikesComment> persons) {
        log.debug("--> printLikesComment().");
        int counter = 1;
        for (PersonLikesComment person : persons) {
            log.info("id = {} \t name = {} \t knows comment = {} \t content = {}",
                    person.getPerson().getId(),
                    person.getPerson().getName(),
                    person.getComment().getId(),
                    person.getComment().getContent());
            counter++;
            if (counter > 15) {
                break;
            }
        }
        log.debug("<-- printLikesComment().");
    }

    private static void printPersonLikesPost(List<PersonLikesPost> persons) {
        log.debug("--> printLikesPost().");
        int counter = 1;
        for (PersonLikesPost person : persons) {
            log.info("id = {} \t name = {} \t knows post = {} \t content = {}",
                    person.getPerson().getId(),
                    person.getPerson().getName(),
                    person.getPost().getId(),
                    person.getPost().getContent());
            counter++;
            if (counter > 15) {
                break;
            }
        }
        log.debug("<-- printLikesPost().");
    }

    private static void printPersonStudiesAt(List<PersonStudiesAt> persons) {
        log.debug("--> printLikesPost().");
        int counter = 1;
        for (PersonStudiesAt person : persons) {
            log.info("id = {} \t name = {} \t uni id = {} \t name = {} \t class year = {}",
                    person.getPerson().getId(),
                    person.getPerson().getName(),
                    person.getUniversity().getId(),
                    person.getUniversity().getName(),
                    person.getClassYear());
            counter++;
            if (counter > 15) {
                break;
            }
        }
        log.debug("<-- printLikesPost().");
    }


}
