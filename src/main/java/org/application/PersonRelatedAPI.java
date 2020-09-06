package org.application;

import java.util.List;

public interface PersonRelatedAPI {
    String LINE_BREAK = "\n";
    String TAB = "\t";
    // TODO Use System.getProperty("line.separator") instead

    String getProfile(long id);

    String getCommonInterestsOfMyFriends(long id);

    String getCommonFriends(long id1, long id2);

    String getPersonsWitMostCommonInterests(long id);

    List<String> getJobRecommendation(String id);

    List<String> getShortestFriendshipPath(String id);
}
