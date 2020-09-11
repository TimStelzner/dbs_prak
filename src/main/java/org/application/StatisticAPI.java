package org.application;

public interface StatisticAPI {
    String LINE_BREAK = "\n";

    String getTagClassHierarchy(long tagId);

    String getPopularComments(long likes);

    String getMostPostingCountry();
}
