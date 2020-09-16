package org.application;

import lombok.extern.slf4j.Slf4j;
import org.Main;
import org.tables.*;
import org.tables.composite.PersonHasInterest;
import org.tables.composite.PersonStudiesAt;
import org.tables.composite.PersonWorksAt;
import org.tables.composite.PkpSymmetric;
import org.tables.procedures.FamiliarityPath;
import org.utilities.ConsoleUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Implements person related methods according to {@link PersonRelatedAPI}.
 */
@Slf4j
public class PersonRelatedImpl extends ConsoleUtils implements PersonRelatedAPI {
    private final EntityManager entityManager;

    public PersonRelatedImpl() {
        entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    /**
     * Closes class variables.
     */
    public void closePersonRelated() {
        entityManager.close();
    }

    /**
     * Finds all personal information for a given id.
     * Uses database table "person" and creates a prettified output.
     *
     * @param id
     * @return all the fields in a prettified String.
     */
    @Override
    public String getProfile(long id) {
        log.debug("--> getProfile().");

        // Setup variables
        StringBuilder profile = new StringBuilder();
        Person person = null;

        // Setup query
        String query = "SELECT c FROM Person c WHERE c.id = :id";
        TypedQuery<Person> typedQuery = entityManager.createQuery(query, Person.class);
        typedQuery.setParameter("id", id);

        // Run query. No need to validate, because getSingleResult throws No Result Exceptions.
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
                .append("gender = ")
                .append(person.getGender())
                .append(LINE_BREAK)
                .append("birthday = ")
                .append(person.getBirthday())
                .append(LINE_BREAK)
                .append("browser used = ")
                .append(person.getBrowser_used())
                .append(LINE_BREAK)
                .append("location ip = ")
                .append(person.getLocationIp())
                .append(LINE_BREAK)
                .append("city = ")
                .append(person.getCity().getName())
                .append(LINE_BREAK)
                .append("joined = ")
                .append(person.getCreationDate())
                .append(LINE_BREAK);

        // List all the spoken languages. Note, this will create a trailing comma.
        profile.append("speaks [");
        String[] languages = person.getSpeaks();
        for (String language : languages) {
            profile.append(language)
                    .append(",");
        }

        // Replace trailing comma with square bracket
        int lastCommaPosition = profile.lastIndexOf(",");
        profile.replace(lastCommaPosition, lastCommaPosition + 1, "]");
        profile.append(LINE_BREAK);

        // List all the user's emails.  Note, this will create a trailing comma.
        profile.append("Emails: [");
        String[] emails = person.getEmails();
        for (String email : emails) {
            profile.append(email)
                    .append(",");
        }
        // Replace trailing comma with square bracket
        lastCommaPosition = profile.lastIndexOf(",");
        profile.replace(lastCommaPosition, lastCommaPosition + 1, "]");
        profile.append(LINE_BREAK);

        log.debug("<-- getProfile().");
        return profile.toString();
    }

    /**
     * Gets all common interests of a given id with all his or her friends.
     * We define a common interest as a tag_id match from database table "person_has_interest".
     * Uses database table "pkp_symmetric".
     * Creates a prettified list with tag id and person for each common interest.
     * @param id
     * @return the prettified list as a String.
     */
    @Override
    public String getCommonInterestsOfMyFriends(long id) {
        log.debug("--> getCommonInterestsOfMyFriends(id = {})", id);

        // Setup variables
        StringBuilder interests = new StringBuilder();

        // Setup query. Assume the referenced person from id is called Bob.
        String query = "SELECT c FROM PkpSymmetric c WHERE c.id.personId1 = :bob";
        //TypedQuery<PkpSymmetric> typedQuery = entityManager.createQuery(query, PkpSymmetric.class);
        Query typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter("bob", id);

        // Run query. These are all the friends of Bob.
        List<PkpSymmetric> bobsFriends = typedQuery.getResultList();

        // Exit if Bob has no friends
        if (bobsFriends.isEmpty()) {
            System.out.println("Person does not exist or has no friends.");
            throw new NoResultException("Person does not exist or has no friends.");
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

        // Create output title
        interests.append(LINE_BREAK)
                .append(bob)
                .append("'s Common Interests With Friends")
                .append(LINE_BREAK);
        // Iterate over Bob's friends and finds all common interests.
        for (PkpSymmetric friendOfBob : bobsFriends) {
            Set<PersonHasInterest> stuffFriendLikes = friendOfBob.getPerson2().getLikes();
            // Build common interests
            for (PersonHasInterest interest : stuffFriendLikes) {
                Long tagId = interest.getTag().getId();
                if (bobsFavoriteTags.contains(tagId)) {
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
        log.debug("<-- getCommonInterestsOfMyFriends().");
        return interests.toString();
    }

    /**
     * Utility method that checks whether any results have been found for a given query.
     * @param result
     */
    private void validateQueryResults(List result) {
        if (result.isEmpty()) {
            System.out.println("No results were found for this query. Person does not exist or has no friends.");
            throw new NoResultException("No results were found for this query.");
        }
    }

    /**
     * Finds common friends of two given person ids.
     * For sake of simplicity, we refer to the first id as "Bob" and to the second id as "Alice".
     * Note, these do not represent the names of those persons from the database.
     * Uses database table "pkp_symmetric".
     * Creates a prettified list with person id and name for each common friend.
     * @param bob
     * @param alice
     * @return the prettified list of common friends as a String.
     */
    public String getCommonFriends(long bob, long alice) {
        log.debug("--> getCommonFriends(bob={}, alice={})", bob, alice);
        // Setup variables
        StringBuilder commonFriends = new StringBuilder();

        // Setup query. Find all friends of Bob and Alice mixed together.
        String query = "SELECT c FROM PkpSymmetric c WHERE c.id.personId1 = :bob OR c.id.personId1 = :alice";
        Query typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter("bob", bob);
        typedQuery.setParameter("alice", alice);

        // Run query.
        List<PkpSymmetric> friendsOfBoth = typedQuery.getResultList();

        // Validate query
        validateQueryResults(friendsOfBoth);

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

        // Validate that queried users have friends
        if (friendsOfBob.isEmpty()) {
            return "id = " + bob + " currently has no friends.";
        }
        if (friendsOfAlice.isEmpty()) {
            return "id = " + alice + " currently has no friends.";
        }

        // Log amount of friends
        int numberOfFriendsForBob = friendsOfBob.size();
        int numberOfFriendsForAlice = friendsOfAlice.size();
        log.info("id = {} has {} friends.", bob, numberOfFriendsForBob);
        log.info("id = {} has {} friends.", alice, numberOfFriendsForAlice);

        // Setup title for common friends response
        commonFriends.append(LINE_BREAK)
                .append("Common Friends:");

        // Find common friends of Bob and Alice
        int commonFriendsCount = 0;
        for (Person friendOfBob : friendsOfBob) {
            if (friendsOfAlice.contains(friendOfBob)) {
                commonFriendsCount++;
                String commonFriendId = insertRightPad(friendOfBob.getId().toString(), 18);
                String commonFriend = insertRightPad(friendOfBob.getName(), 18);
                commonFriends.append(LINE_BREAK)
                        .append("id = ")
                        .append(commonFriendId)
                        .append("name = ")
                        .append(commonFriend);
            }
        }

        // Validate that common friends exist. Print message accordingly.
        if (commonFriendsCount == 0) {
            commonFriends.append(LINE_BREAK)
                    .append("id = ")
                    .append(bob)
                    .append(" and id = ")
                    .append(alice)
                    .append(" currently have no common friends.")
                    .append(LINE_BREAK);
        }
        log.debug("<-- getCommonFriends().");
        return commonFriends.toString();
    }

    /**
     * Determines the person or persons with the most common interests with given id.
     * Uses database table "person_has_interest" via {@link PersonHasInterest}
     * For sake of simplicity, we refer to the id as "Bob".
     * We call people with common interests "Peers".
     * Queries all of Bob's favorite tags, we refer to these as "Favorites".
     * For each favorite, we find all peers and count their number of common interests.
     * Then we determine all peers that have the highest amount of common interests.
     * @param id the id of the person that is to be matched.
     * @return person or persons with the most common interests as a String.
     */
    @Override
    public String getPersonsWitMostCommonInterests(long id) throws NoSuchElementException {
        log.debug("--> getPersonsWitMostCommonInterests(id = {})", id);

        // Setup variables
        StringBuilder peers = new StringBuilder();

        // Setup query. Get all of Bob's interests
        String query = "SELECT c FROM PersonHasInterest c WHERE c.id.personId = :bob";
        Query typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter("bob", id);
        List<PersonHasInterest> favoritesOfBob = typedQuery.getResultList();
        validateQueryResults(favoritesOfBob);

        // Setup Map for Bob's peers. Uses an Integer as Map value to count # of Common Likes for each person.
        Map<Person, Integer> peerGroup = new HashMap<>();
        // Keep track of the highest score. Update this while iterating through each Tag.
        int highScore = 0;

        // Create the set of Bobs favorite stuff. Iterate over every Favorite Tag
        for (PersonHasInterest f : favoritesOfBob) {
            Tag favorite = f.getTag();

            // Find all the other people that also like this favorite.
            Set<PersonHasInterest> peopleWhoLikeFavorites = favorite.getLikes();
            // For each favorite iterate over the set of people that also like that favorite
            for (PersonHasInterest t : peopleWhoLikeFavorites) {
                Person peer = t.getPerson();

                // Add peer and create new likes list if that peer isn't already in the set
                boolean personIsInPeerGroup = peerGroup.containsKey(peer);
                boolean personIsBob = peer.getId() == id;

                // Make sure we don't add Bob to the list.
                if (!personIsBob) {
                    // Add peer if it isn't already in the peer group
                    if (!personIsInPeerGroup) {
                        peerGroup.put(peer, 0);
                    }
                    // Update the likes count for that peer
                    int currentScore = peerGroup.get(peer);
                    currentScore++;
                    peerGroup.put(peer, currentScore);

                    // Update high score if necessary
                    if (currentScore > highScore) {
                        highScore = currentScore;
                    }
                }
            }
        }
        // Create title for output
        peers.append(LINE_BREAK)
                .append("Persons that share most interest with ")
                .append(id)
                .append(LINE_BREAK);

        // Get all the persons who match the high score
        Set<Map.Entry<Person, Integer>> entries = peerGroup.entrySet();
        for (Map.Entry<Person, Integer> entry : entries) {
            if (entry.getValue().equals(highScore)) {
                Person peer = entry.getKey();
                String peerId = insertRightPad(peer.getId().toString(), 18);
                String peerName = insertRightPad(peer.getName(), 18);
                String peerDegree = insertRightPad(String.valueOf(highScore), 18);
                peers.append("id = ")
                        .append(peerId)
                        .append("name = ")
                        .append(peerName)
                        .append("# of common interests = ")
                        .append(peerDegree)
                        .append(LINE_BREAK);
            }
        }
        log.debug("<-- getPersonsWitMostCommonInterests().");
        return peers.toString();
    }

    /**
     * This task is ambiguous. The following interpretation applies to this method:
     * Will attempt to find a company where a friend is also working at,
     * located in the same country (since companies are not located in cities).
     * We define a current workplace of a person as the company id with the highest "workFrom" value in PersonWorksAt
     * Simultaneously, will also attempt to find a university where a friend is enrolled in (ignoring class year),
     * located in the same city (since universities are located in cities).
     * We define a current university as the university id with the highest "classYear" value in PersonStudiesAt.
     * Search will stop as soon as either a company or university is found.
     *
     * @param id
     * @return a job or uni recommendation if it exists as a String.
     */
    // TODO We could compare the year of work and study tables and conclude that a person is either a student,
    //  a worker or both and then exclude job/uni accordingly.
    @Override
    public String getJobRecommendation(long id) {
        log.debug("--> getJobRecommendation(id = {}).", id);

        // Setup variables
        StringBuilder jobRecommendation = new StringBuilder();
        boolean recommendationFound = false;

        // Setup query. Assume the referenced person from id is called Bob.
        String query = "SELECT c FROM PkpSymmetric c WHERE c.id.personId1 = :bob";
        Query typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter("bob", id);

        // Run Query
        List<PkpSymmetric> friendsOfBob = typedQuery.getResultList();

        // Exit if Bob has no friends
        if (friendsOfBob.isEmpty()) {
            return "Person does not exist or has no friends.";
        }

        // Get info on Bob
        Person bob = friendsOfBob.get(0).getPerson1();
        City cityOfBob = bob.getCity();
        Country countryOfBob = cityOfBob.getIsPartOf();
        log.debug("{} {} lives in {}, {}", bob.getName(), bob.getSurname(), cityOfBob.getName(), countryOfBob.getName());

        // Determine current workplace and/or university of Bob
        Set<PersonWorksAt> employersOfBob = bob.getJobs();
        Set<PersonStudiesAt> unisOfOBob = bob.getUniversities();
        Company currentEmployerOfBob = getCurrentEmployerFor(bob);
        University currentUniOfBob = getCurrentUniversityFor(bob);
        log.debug("Studied last at {}", currentUniOfBob.getName());

        // Setup title
        jobRecommendation.append("Job Recommendation for ")
                .append(bob.getName())
                .append(" ")
                .append(bob.getSurname())
                .append(LINE_BREAK);

        // Iterate over Bob's friends, attempt to find Uni or Company match. Stop immediately on a match.
        for (PkpSymmetric f : friendsOfBob) {
            Person friend = f.getPerson2();
            log.debug("{} is a friend of {}", friend.getId(), bob.getId());

            // Determine if friend is currently working somewhere which applies for rules of recommendation
            Company currentEmployerOfFriend = getCurrentEmployerFor(friend);
            // If friend has no jobs, skip to next friend.
            if (currentEmployerOfFriend == null) {
                continue;
            }
            log.debug("current Employer of friend is {} located in {}", currentEmployerOfFriend.getName(), currentEmployerOfFriend.getCountry().getName());
            // Setup rules of recommendation
            boolean employerOfFriendIsLocatedWhereBobLives = currentEmployerOfFriend.getCountry().equals(countryOfBob);
            boolean bobHasWorkHistory = !employersOfBob.isEmpty();

            if (employerOfFriendIsLocatedWhereBobLives) {
                boolean friendHasADifferentJob = true;
                // If Bob has a work history, ensure that his friend isn't working for the same company already.
                if (bobHasWorkHistory) {
                    friendHasADifferentJob = !currentEmployerOfBob.equals(currentEmployerOfFriend);
                }
                if (friendHasADifferentJob) {
                    log.debug("Found a Job match: Company = {} in {} and {} also lives in {}, {}",
                            currentEmployerOfFriend.getName(),
                            currentEmployerOfFriend.getCountry().getName(),
                            bob.getName(),
                            cityOfBob.getName(),
                            countryOfBob.getName());
                    jobRecommendation.append("Company = ")
                            .append(currentEmployerOfFriend.getName())
                            .append("\t id = ")
                            .append(currentEmployerOfFriend.getId());
                    recommendationFound = true;
                    break;
                }
            }

            // Determine if friend is currently studying somewhere which applies for rules of recommendation
            University currentUniOfFriend = getCurrentUniversityFor(friend);
            if (currentUniOfFriend == null) {
                continue;
            }

            // Setup rules of recommendation
            boolean uniOfFriendIsWhereBobLives = currentUniOfFriend.getCity().equals(cityOfBob);
            boolean bobHasStudyHistory = !unisOfOBob.isEmpty();

            if (uniOfFriendIsWhereBobLives) {
                boolean friendStudiesAtDifferentUni = true;
                // If Bob has a study history, ensure that his friend is not enrolled in the same uni.
                if (bobHasStudyHistory) {
                    friendStudiesAtDifferentUni = !currentUniOfBob.equals(currentUniOfFriend);
                }
                if (friendStudiesAtDifferentUni) {
                    log.debug("Found a Uni match: Uni = {} in {} and {} also lives in {}, {}",
                            currentUniOfFriend.getName(),
                            currentUniOfFriend.getCity().getName(),
                            bob.getName(),
                            cityOfBob.getName(),
                            countryOfBob.getName());
                    jobRecommendation.append("Uni  = ")
                            .append(currentUniOfFriend.getName())
                            .append("\t id = ")
                            .append(currentUniOfFriend.getId());
                    recommendationFound = true;
                    break;
                }
            }
        }
        if (!recommendationFound) {
            jobRecommendation.append("No job recommendation can be made at this point.");
        }

        log.debug("<-- getJobRecommendation().");
        return jobRecommendation.toString();
    }

    /**
     * Finds current employer for a given person.
     * We define current employer as the company with the highest workFrom value in {@link PersonWorksAt}
     * @param person The given person to be queried.
     * @return current University, null if none exist.
     */
    // TODO To compare years, we should return PersonWorksAt object rather than company.
    private Company getCurrentEmployerFor(Person person) {
        log.debug("--> getCurrentJobFor().");
        Company currentEmployer = null;
        Set<PersonWorksAt> jobHistory = person.getJobs();
        boolean personHasJobHistory = !jobHistory.isEmpty();

        if (personHasJobHistory) {
            int latestYearOfEmployment = 0;
            // Iterate through job history to determine current job
            for (PersonWorksAt j : jobHistory) {
                int year = j.getWorkFrom();
                if (year > latestYearOfEmployment) {
                    latestYearOfEmployment = year;
                    currentEmployer = j.getCompany();
                }
            }
            log.debug("{} Currently working at {}", person.getId(), currentEmployer.getName());
        }
        log.debug("<-- getCurrentJobFor().");
        return currentEmployer;
    }

    /**
     * Finds current university for a given person.
     * We define current university as the uni with the highest classYear value in {@link PersonStudiesAt}
     *
     * @param person The given person to be queried.
     * @return current University, null if none exist.
     */
    // TODO To compare years, we should return PersonStudiesAt object rather than company.
    private University getCurrentUniversityFor(Person person) {
        log.debug("--> getCurrentUniversityFor().");

        University currentUni = null;
        Set<PersonStudiesAt> studyHistory = person.getUniversities();
        boolean personHasStudyHistory = !studyHistory.isEmpty();

        if (personHasStudyHistory) {
            int latestYearOfEnrollment = 0;
            for (PersonStudiesAt s : studyHistory) {
                Integer classYear = s.getClassYear();
                if (classYear > latestYearOfEnrollment) {
                    latestYearOfEnrollment = classYear;
                    currentUni = s.getUniversity();
                }
            }
            log.debug("{} currently studying at {}", person.getId(), currentUni.getName());
        }
        log.debug("<-- getCurrentUniversityFor().");
        return currentUni;
    }

    /**
     * Finds the shortest path of familiarity between two given ids.
     * We define a path of familiarity as the sequence of befriended people.
     * where each two successive people are friends.
     * Uses stored procedure {@link FamiliarityPath}.
     *
     * @param id
     * @param id2
     * @return all shortest paths of similarity as a String.
     */
    @Override
    public String getShortestFriendshipPath(long id, long id2) {
        log.debug("--> getShortestFriendshipPath().");
        // Setup variables
        StringBuilder shortestPath = new StringBuilder();

        // Defined stored procedure
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("myFunction");

        // set input parameter
        query.setParameter("id1", id);
        query.setParameter("id2", id2);

        // call the stored procedure and get the result
        query.execute();
        List<FamiliarityPath> resultList = query.getResultList();

        // Setup title for output
        shortestPath.append(LINE_BREAK)
                .append("Shortest paths from ")
                .append(id)
                .append(" to ")
                .append(id2)
                .append(LINE_BREAK);

        // Check if query has returned any results.
        if (resultList.isEmpty()) {
            shortestPath.append("Persons either do not know each other or do not exist.");
        } else {
            // Add each familiarity path to the output.
            for (FamiliarityPath r : resultList) {
                shortestPath.append(r.getPathString())
                        .append(LINE_BREAK);
            }
        }
        log.debug("<-- getShortestFriendshipPath().");
        return shortestPath.toString();
    }
}
