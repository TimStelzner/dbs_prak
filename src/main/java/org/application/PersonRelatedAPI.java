package org.application;

import java.util.List;

public interface PersonRelatedAPI {
    String getProfile(long id);

    List<String> getCommonInterestsOfMyFriends(String id);

    List<String> getCommonFriends(String id);

    List<String> getPersonsWitMostCommonInterests(String id);

    List<String> getJobRecommendation(String id);

    List<String> getShortestFriendshipPath(String id);
}
