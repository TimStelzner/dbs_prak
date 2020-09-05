package org.application;

import java.util.List;

public interface PersonRelatedAPI {
    String LINE_BREAK = "\n";
    // TODO Use System.getProperty("line.separator") instead

    String getProfile(long id);

    String getCommonInterestsOfMyFriends(long id);

    List<String> getCommonFriends(String id);

    List<String> getPersonsWitMostCommonInterests(String id);

    List<String> getJobRecommendation(String id);

    List<String> getShortestFriendshipPath(String id);
}
