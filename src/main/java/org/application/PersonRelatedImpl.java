package org.application;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.Main;
import org.tables.Person;
import org.tables.composite.PersonHasInterest;
import org.tables.composite.PkpSymmetric;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Data
public class PersonRelatedImpl implements PersonRelatedAPI {

    @Override
    public String getProfile(long id) {
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
    public String getCommonInterestsOfMyFriends(long id) {
        log.debug("--> getCommonInterestsOfMyFriends(id = {})", id);

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        StringBuilder interests = new StringBuilder();
        PkpSymmetric person = null;
        PersonHasInterest likes = null;


        // TODO Not working
        // Setup query

        String query = "SELECT c FROM PkpSymmetric c WHERE c.id.personId1 = :bob";
        //TypedQuery<PkpSymmetric> typedQuery = entityManager.createQuery(query, PkpSymmetric.class);
        Query typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter("bob", id);

        // Run query
        List<PkpSymmetric> bobsFriends = typedQuery.getResultList();

        // Find out all the stuff that given id likes
        Set<PersonHasInterest> stuffBobLikes = bobsFriends.get(0).getPerson1().getLikes();
        Set<Long> bobsFavoriteTags = new HashSet<>();

        for (PersonHasInterest s : stuffBobLikes) {
            Long tagId = s.getTag().getId();
            bobsFavoriteTags.add(tagId);
            log.debug("Adding {} to Bobs favorite tags.", tagId);
        }

        System.out.println("friends of Chen");
        for (PkpSymmetric friendOfBob : bobsFriends) {
            System.out.println(friendOfBob.getId().getPersonId1() + " | " + friendOfBob.getId().getPersonId2());

            Set<PersonHasInterest> stuffFriendLikes = friendOfBob.getPerson2().getLikes();

            for (PersonHasInterest interest : stuffFriendLikes) {
                //Tag tag = interest.getTag();
                //System.out.println(interest.getPerson().getId() + " likes " + tag.getId());
                Long tagId = interest.getTag().getId();
                if (bobsFavoriteTags.contains(tagId)) {
                    log.debug("tagId = {} is something Bob likes too", tagId);
                }
                if (stuffBobLikes.contains(interest)) {
                    System.out.println("tag_id = " + interest.getTag().getId() + " name = " + interest.getPerson().getName());
                }
            }


        }


        /*
        // Build common interests
        for (PersonHasInterest p : resultList) {
            interests.append(p.getPerson().getName())
                    .append(" likes ")
                    .append(p.getTag().getName())
                    .append(LINE_BREAK);
        }


         */

        entityManager.close();


        log.debug("<-- getCommonInterestsOfMyFriends().");
        return interests.toString();
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
