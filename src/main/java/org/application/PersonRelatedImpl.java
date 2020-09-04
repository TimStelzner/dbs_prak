package org.application;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.Main;
import org.tables.Person;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
@Data
public class PersonRelatedImpl implements PersonRelatedAPI {

    @Override
    public String getProfile(long id) {
        // TODO Return type should probably be an Optional.
        log.debug("--> getProfile().");

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        StringBuilder profile = new StringBuilder();
        Person person = null;

        // Setup query
        String query = "SELECT c FROM Person c WHERE c.id = :id";
        TypedQuery<Person> typedQuery = entityManager.createQuery(query, Person.class);
        typedQuery.setParameter("id", id);

        // Run query
        person = typedQuery.getSingleResult();
        profile.append("id = " + person.getId() + "\n");
        profile.append("created on = " + person.getCreationDate() + "\n");
        profile.append("surname = " + person.getSurname() + "\n");

        entityManager.close();
        log.debug("<-- getProfile().");

        return profile.toString();
    }

    @Override
    public List<String> getCommonInterestsOfMyFriends(String id) {
        return null;
    }

    @Override
    public List<String> getCommonFriends(String id) {
        return null;
    }

    @Override
    public List<String> getPersonsWitMostCommonInterests(String id) {
        return null;
    }

    @Override
    public List<String> getJobRecommendation(String id) {
        return null;
    }

    @Override
    public List<String> getShortestFriendshipPath(String id) {
        return null;
    }
}
