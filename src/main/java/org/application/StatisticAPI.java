package org.application;

public interface StatisticAPI {
    String LINE_BREAK = "\n";

    String getTagClassHierarchy(long tagId);

    String getPopularComments(int likes);

    String getMostPostingCountry();
}
