package org.application;

public interface PersonRelatedAPI {
    String LINE_BREAK = "\n";

    String getProfile(long id);

    String getCommonInterestsOfMyFriends(long id);

    String getCommonFriends(long id1, long id2);

    String getPersonsWitMostCommonInterests(long id);

    String getJobRecommendation(long id);

    String getShortestFriendshipPath(long id, long id2);
}
