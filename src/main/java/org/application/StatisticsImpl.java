package org.application;

import lombok.extern.slf4j.Slf4j;
import org.Main;
import org.tables.Comment;
import org.tables.Country;
import org.tables.TagClass;
import org.tables.composite.TagClassIsSubclassOf;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Slf4j
public class StatisticsImpl extends ConsoleUtils implements StatisticAPI {

    private final EntityManager entityManager;

    public StatisticsImpl() {
        entityManager = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public void closeStatistics() {
        entityManager.close();
    }

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

        if (resultList.isEmpty()) {
            return "Tag class does not exist or has no children.";
        }
        TagClass root = resultList.get(0).getParentTag();
        Map<String, TagClass> tagClassTreemap = new LinkedHashMap<>();
        buildTagHierarchy(root, "1", tagClassTreemap);

        Set<Map.Entry<String, TagClass>> entrySet = tagClassTreemap.entrySet();
        for (Map.Entry<String, TagClass> e : entrySet) {
            String key = insertRightPad(e.getKey(), 12);
            String tagClassName = insertRightPad(e.getValue().getName(), 25);
            hierarchy.append(LINE_BREAK)
                    .append(key)
                    .append(tagClassName);
        }

        /*
        int counter = 1;
        //log.info(counter + " : " + tagId);
        for (TagClassIsSubclassOf r : resultList) {
            // TODO recursively iterate through resultList
            TagClass childTag = r.getChildTag();
            //log.info(counter + " : " + childTag.getId());
            buildTagHierarchy(childTag, String.valueOf(counter), tagClassTreemap);
            counter++;
        }

         */


        log.debug("<-- getTagClassHierarchy(tagId = {})", tagId);
        return hierarchy.toString();
    }

    private void buildTagHierarchy(TagClass node, String depth, Map<String, TagClass> hierarchy) {
        log.debug("--> buildTagHierarchy(node = {}, {})", node.getId(), node.getName());
        //Set<TagClass> children = extractChildNodes(node.getChildren());
        Set<TagClassIsSubclassOf> children = node.getParentOf();
        log.debug("children size = {}", children.size());

        hierarchy.put(depth, node);

        log.debug("{} : {}", depth, node.getName());
        boolean nodeHasChildren = !children.isEmpty();
        if (nodeHasChildren) {
            int counter = 1;
            for (TagClassIsSubclassOf c : children) {
                String newDepth = depth + "." + counter;
                //Long childId = c.getChildTag().getId();
                //Long parentId = c.getParentTag().getId();
                //log.info("child id = {} ", childId);
                //log.info("parent id = {}", parentId);
                buildTagHierarchy(c.getChildTag(), newDepth, hierarchy);
                counter++;
            }
        }
        log.debug("<-- buildTagHierarchy");
    }

    private Set<TagClass> extractChildNodes(Set<TagClassIsSubclassOf> children) {
        Set<TagClass> childNodes = new HashSet<>();
        for (TagClassIsSubclassOf c : children) {
            TagClass tagClass = c.getChildTag();
            childNodes.add(tagClass);
        }
        return childNodes;
    }

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
                .append("Most post country: ")
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
