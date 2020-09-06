package org.application;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.Main;
import org.tables.Person;
import org.tables.Tag;
import org.tables.composite.PersonHasInterest;
import org.tables.composite.PkpSymmetric;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

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

        // Build result
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

        // Setup query. Assume the referenced person from id is called Bob.
        String query = "SELECT c FROM PkpSymmetric c WHERE c.id.personId1 = :bob";
        //TypedQuery<PkpSymmetric> typedQuery = entityManager.createQuery(query, PkpSymmetric.class);
        Query typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter("bob", id);

        // Run query. These are all friends of Bob.
        List<PkpSymmetric> bobsFriends = typedQuery.getResultList();

        // Exit if Bob has no friends
        if (bobsFriends.isEmpty()) {
            return "Person does not exist or has no friends.";
        }

        String bob = bobsFriends.get(0).getPerson1().getName();

        // Determine all the Tags that Bob likes
        Set<PersonHasInterest> stuffBobLikes = bobsFriends.get(0).getPerson1().getLikes();
        Set<Long> bobsFavoriteTags = new HashSet<>();
        for (PersonHasInterest s : stuffBobLikes) {
            Long tagId = s.getTag().getId();
            bobsFavoriteTags.add(tagId);
            log.debug("Adding {} to Bobs favorite tags.", tagId);
        }

        interests.append(LINE_BREAK)
                .append(bob)
                .append("'s Common Interests With Friends")
                .append(LINE_BREAK);
        //System.out.println("friends of " + bob);
        for (PkpSymmetric friendOfBob : bobsFriends) {
            //System.out.println(friendOfBob.getId().getPersonId1() + " | " + friendOfBob.getId().getPersonId2());

            Set<PersonHasInterest> stuffFriendLikes = friendOfBob.getPerson2().getLikes();

            // Build common interests

            for (PersonHasInterest interest : stuffFriendLikes) {
                //Tag tag = interest.getTag();
                //System.out.println(interest.getPerson().getId() + " likes " + tag.getId());
                Long tagId = interest.getTag().getId();
                if (bobsFavoriteTags.contains(tagId)) {
                    //log.debug("tagId = {} is something Bob likes too", tagId);
                    //System.out.println("tag_id = " + interest.getTag().getId() + " name = " + interest.getPerson().getName());
                    // Get Id and name and prettify the output
                    String printId = insertRightPad(interest.getTag().getId().toString(), 12);
                    String printName = insertRightPad(interest.getPerson().getName(), 12);

                    interests.append("tag_id = ")
                            .append(printId)
                            .append("name = ")
                            .append(printName)
                            .append(LINE_BREAK);
                }
            }
        }

        entityManager.close();
        log.debug("<-- getCommonInterestsOfMyFriends().");
        return interests.toString();
    }


    /**
     * Finds common friends of two given person ids.
     * For sake of simplicity, we call the first id "Bob" and the second id "Alice".
     * Note, these do not represent the names of those persons from the database.
     * TODO The transaction currently throws no error when invalid ids are entered?!
     *
     * @param bob
     * @param alice
     * @return
     */
    public String getCommonFriends(long bob, long alice) {
        log.debug("--> getCommonFriends(bob={}, alice={})", bob, alice);
        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        StringBuilder commonFriends = new StringBuilder();

        // Setup query. Find all friends of Bob and Alice mixed together.
        String query = "SELECT c FROM PkpSymmetric c WHERE c.id.personId1 = :bob OR c.id.personId1 = :alice";
        Query typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter("bob", bob);
        typedQuery.setParameter("alice", alice);

        // Run query.
        List<PkpSymmetric> friendsOfBoth = typedQuery.getResultList();

        // Separate friends of Bob and Alice into two sets
        Set<Person> friendsOfBob = new HashSet<>();
        Set<Person> friendsOfAlice = new HashSet<>();

        for (PkpSymmetric f : friendsOfBoth) {
            Long personId1 = f.getPerson1().getId();
            Person friend = f.getPerson2();
            if (personId1 == bob) {
                friendsOfBob.add(friend);
                log.debug(friend.getName() + " is a friend of bob.");
            } else {
                friendsOfAlice.add(friend);
                log.debug(friend.getName() + " is a friend of alice.");
            }
        }
        log.info("id = {} has {} friends.", bob, friendsOfBob.size());
        log.info("id = {} has {} friends.", alice, friendsOfAlice.size());

        // Find common friends of Bob and Alice
        commonFriends.append("Common Friends:")
                .append(LINE_BREAK);

        for (Person p : friendsOfBob) {
            if (friendsOfAlice.contains(p)) {
                String commonFriendId = insertRightPad(p.getId().toString(), 18);
                String commonFriend = insertRightPad(p.getName(), 18);
                //System.out.println(commonFriend + " is a common friend.");
                commonFriends.append("id = ")
                        .append(commonFriendId)
                        .append("name = ")
                        .append(commonFriend)
                        .append(LINE_BREAK);
            }
        }

        log.debug("<-- getCommonFriends().");
        return commonFriends.toString();
    }

    /**
     * Determines the person that has the most common interests with given id.
     * We need to query person_has_interest.
     * We call the queried person Bob.
     * We call people with common interests Bob's peers.
     * TODO We have no way of determining multiple peers if there should be more than one person with the same amount of common interest.
     *
     * @param id
     * @return
     */
    @Override
    public String getPersonsWitMostCommonInterests(long id) {
        log.debug("--> getPersonsWitMostCommonInterests(id = {})", id);

        // Setup variables
        EntityManager entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        StringBuilder mostCommonInterests = new StringBuilder();

        String query = "SELECT c FROM PersonHasInterest c WHERE c.id.personId = :bob";
        Query typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter("bob", id);
        List<PersonHasInterest> resultList = typedQuery.getResultList();

        //Set<Tag> bobLikes = new HashSet<>();
        //Set<Person> peers = new HashSet<>();
        Map<Person, Integer> commonInterestsOfPeers = new HashMap<>();
        TreeMap<Integer, Person> highscores = new TreeMap<>();


        // Create the set of Bobs favorite stuff
        for (PersonHasInterest p : resultList) {
            Tag tag = p.getTag();
            //bobLikes.add(tag);

            // Find the other people that like the same stuff as Bob
            Set<PersonHasInterest> tagLikedByOthers = tag.getLikes();
            for (PersonHasInterest t : tagLikedByOthers) {
                Person person = t.getPerson();
                //peers.add(person);
                // Add person and create new likes list if that person isn't already in the set
                boolean personIsInPeerGroup = commonInterestsOfPeers.containsKey(person);
                boolean personIsBob = person.getId() != id;

                if (!personIsBob) {
                    if (!personIsInPeerGroup) {
                        commonInterestsOfPeers.put(person, 0);
                    }
                    // Update the likes count for that person
                    int currentScore = commonInterestsOfPeers.get(person);
                    currentScore++;
                    commonInterestsOfPeers.put(person, currentScore);
                    highscores.put(currentScore, person);
                }
            }
        }
        Integer highscore = highscores.lastKey();
        Person bestPeer = highscores.get(highscore);
        log.info("best peer is id = {}, name = {}", bestPeer.getId(), bestPeer.getName());
        log.info("{} has {} common interests with {}.", bestPeer.getName(), highscore, id);

        // Figure out who has the most common interests with Bob. Beware, Bob is also in that list


        // Create set of stuff that Bob likes
        /*
        if(!resultList.isEmpty()) {
            PersonHasInterest firstResult = resultList.get(0);
            Set<PersonHasInterest> bobLikes = firstResult.getPerson().getLikes();
            Set<PersonHasInterest> likedByOthers = firstResult.getTag().getLikes();
        } else {
            return "Person does not exist or doesn't like anything."
        }

         */



        /*
        // Setup Outer Query: Get all the favorite tags of everyone.
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(PersonHasInterest.class);
        Root root = criteriaQuery.from(PersonHasInterest.class);

        // Setup Inner Query: Get all of Bobs favorite tags
        Subquery subquery = criteriaQuery.subquery(Tag.class);
        Root subRoot = subquery.from(PersonHasInterest.class);


         */

        log.debug("<-- getPersonsWitMostCommonInterests().");
        return "Hello World";
    }

    @Override
    public List<String> getJobRecommendation(String id) {
        return null;
    }

    @Override
    public List<String> getShortestFriendshipPath(String id) {
        return null;
    }

    /**
     * Provides white space padding for a long list to help left-align its columns.
     * Takes a string and pads the right side with white space characters
     * until it reaches the given final length.
     *
     * @param inStr       the string that needs to be padded.
     * @param finalLength The final length of the padded string.
     * @return the padded string.
     */
    private String insertRightPad(String inStr, int finalLength) {
        return (inStr + "                          "
        ).substring(0, finalLength);
    }
}
