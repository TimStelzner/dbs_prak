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
        String name = person.getName();

        profile.append("id = ")
                .append(person.getId())
                .append(LINE_BREAK)
                .append("created on = ")
                .append(person.getCreationDate())
                .append(LINE_BREAK)
                .append("surname = ")
                .append(person.getSurname())
                .append(LINE_BREAK)
                .append("name = ")
                .append(person.getName())
                .append(LINE_BREAK)
                .append("gender =")
                .append(person.getGender())
                .append(LINE_BREAK)
                .append("birthday =")
                .append(person.getBirthday())
                .append(LINE_BREAK)
                .append("browser used =")
                .append(person.getBrowser_used())
                .append(LINE_BREAK)
                .append("location ip =")
                .append(person.getLocationIp())
                .append(LINE_BREAK)
                .append("city =")
                .append(person.getCity().getName())
                .append(LINE_BREAK)
                .append("joined = ")
                .append(person.getCreationDate())
                .append(LINE_BREAK);

        // List all the spoken languages
        profile.append("speaks [");
        String[] languages = person.getSpeaks();
        for (String language : languages) {
            profile.append(language)
                    .append(",");
        }
        // Replace last comma with square bracket
        int lastCommaPosition = profile.lastIndexOf(",");
        profile.replace(lastCommaPosition, lastCommaPosition + 1, "]");
        profile.append(LINE_BREAK);

        // List all the user's emails
        profile.append("Emails: [");
        String[] emails = person.getEmails();
        for (String email : emails) {
            profile.append(email)
                    .append(",");
        }
        // Replace last comma with square bracket
        lastCommaPosition = profile.lastIndexOf(",");
        profile.replace(lastCommaPosition, lastCommaPosition + 1, "]");
        profile.append(LINE_BREAK);


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
