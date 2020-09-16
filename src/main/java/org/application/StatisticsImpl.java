package org.application;

import lombok.extern.slf4j.Slf4j;
import org.Main;
import org.tables.Comment;
import org.tables.Country;
import org.tables.TagClass;
import org.tables.composite.TagClassIsSubclassOf;
import org.utilities.ConsoleUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

/**
 * Implements statistics related methods according to {@link StatisticAPI}.
 */
@Slf4j
public class StatisticsImpl extends ConsoleUtils implements StatisticAPI {

    private final EntityManager entityManager;

    public StatisticsImpl() {
        entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    /**
     * Closes class variables.
     */
    public void closeStatistics() {
        entityManager.close();
    }

    /**
     * Finds all the children classes of a given tag class id.
     * Creates a tree based taxonomy and prettifies the output as a hierarchy.
     *
     * @param tagId
     * @return the prettified tag class hierarchy as a String.
     */
    @Override
    public String getTagClassHierarchy(long tagId) {
        log.debug("--> getTagClassHierarchy(tagId = {})", tagId);
        StringBuilder hierarchy = new StringBuilder();

        // Setup query. Assume the referenced person from id is called Bob.
        String query = "SELECT c FROM TagClassIsSubclassOf c WHERE c.id.parentId = :id";
        Query typedQuery = entityManager.createQuery(query, TagClassIsSubclassOf.class);
        typedQuery.setParameter("id", tagId);

        // Run query
        List<TagClassIsSubclassOf> resultList = typedQuery.getResultList();

        // Check query result
        if (resultList.isEmpty()) {
            return "Tag class does not exist or has no children.";
        }

        // Setup variables for result
        TagClass root = resultList.get(0).getParentTag();
        Map<String, TagClass> tagClassTreemap = new LinkedHashMap<>();

        // Recursively build the tag hierarchy.
        buildTagHierarchy(root, "1", tagClassTreemap);

        // Build output
        Set<Map.Entry<String, TagClass>> entrySet = tagClassTreemap.entrySet();
        for (Map.Entry<String, TagClass> e : entrySet) {
            String key = insertRightPad(e.getKey(), 12);
            String tagClassName = insertRightPad(e.getValue().getName(), 25);
            hierarchy.append(LINE_BREAK)
                    .append(key)
                    .append(tagClassName);
        }
        log.debug("<-- getTagClassHierarchy(tagId = {})", tagId);
        return hierarchy.toString();
    }

    /**
     * Utility method for {@link StatisticsImpl#getTagClassHierarchy(long)}.
     * Recursively iterates over all children of given tag class.
     * During each step of recursion, upates the given hierarchy and keeps track of depth of hierarchy.
     *
     * @param node      the node to be checked for children.
     * @param depth     The current depth.
     * @param hierarchy the hierarchy to be updated.
     */
    private void buildTagHierarchy(TagClass node, String depth, Map<String, TagClass> hierarchy) {
        log.debug("--> buildTagHierarchy(node = {}, {})", node.getId(), node.getName());
        Set<TagClassIsSubclassOf> children = node.getParentOf();
        log.debug("children size = {}", children.size());

        // Update hierarchy
        hierarchy.put(depth, node);
        // Check for children of given node. Start recursion for each child.
        boolean nodeHasChildren = !children.isEmpty();
        if (nodeHasChildren) {
            int counter = 1;
            for (TagClassIsSubclassOf c : children) {
                String newDepth = depth + "." + counter;
                buildTagHierarchy(c.getChildTag(), newDepth, hierarchy);
                counter++;
            }
        }
        log.debug("<-- buildTagHierarchy");
    }

    /**
     * Finds all comments that have more likes than the given parameter.
     * Queries database via {@link Comment}.
     *
     * @param likes
     * @return
     */
    @Override
    public String getPopularComments(int likes) {
        log.debug("--> getPopularComments().");
        StringBuilder popularComments = new StringBuilder();

        // Setup query. Assume the referenced person from id is called Bob.
        String query = "SELECT c FROM Comment c WHERE c.likedBy.size > :minimumLikes";
        Query typedQuery = entityManager.createQuery(query, Comment.class);
        typedQuery.setParameter("minimumLikes", likes);

        // Run query
        List<Comment> resultList = typedQuery.getResultList();

        popularComments.append(LINE_BREAK)
                .append("Comments with more than ")
                .append(likes)
                .append(" likes:");

        // Build Comments
        for (Comment comment : resultList) {
            String id = insertRightPad(comment.getId().toString(), 18);
            String name = comment.getPerson().getName() + " ";
            String surname = comment.getPerson().getSurname();
            String fullName = insertRightPad(name + surname, 18);
            popularComments.append(LINE_BREAK)
                    .append("id = ")
                    .append(id)
                    .append("Name = ")
                    .append(fullName);
        }
        log.debug("<-- getPopularComments().");
        return popularComments.toString();
    }

    /**
     * Finds the country with most Posts and Comments combined.
     *
     * @return
     * @see org.tables.Post
     * @see Comment
     */
    @Override
    public String getMostPostingCountry() {
        log.debug("--> getMostPostingCountry().");
        StringBuilder mostMessages = new StringBuilder();

        // Setup query. Assume the referenced person from id is called Bob.
        String query = "SELECT c FROM Country c";
        Query typedQuery = entityManager.createQuery(query, Country.class);

        // Run query
        List<Country> resultList = typedQuery.getResultList();
        Stack<Country> countries = new Stack<>();
        int maximum = 0;
        for (Country country : resultList) {
            int numberOfComments = country.getComments().size();
            int numberOfPosts = country.getPosts().size();
            int numberOfMessages = numberOfComments + numberOfPosts;
            if (numberOfMessages > maximum) {
                maximum = numberOfMessages;
                countries.push(country);
                log.debug("{} added to list of potential candidates", country.getName());
            }
        }

        // Build String
        Country mostPostingCountry = countries.pop();
        mostMessages.append(LINE_BREAK)
                .append("Most posting country: ")
                .append(mostPostingCountry.getName())
                .append(" with ")
                .append(mostPostingCountry.getPosts().size())
                .append(" # of posts and ")
                .append(mostPostingCountry.getComments().size())
                .append(" # of comments.")
                .append(LINE_BREAK);
        log.debug("<-- getMostPostingCountry().");
        return mostMessages.toString();
    }
}
