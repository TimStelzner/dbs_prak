package org.application;

import lombok.extern.slf4j.Slf4j;
import org.Main;
import org.tables.TagClass;
import org.tables.composite.TagClassIsSubclassOf;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Slf4j
public class StatisticsImpl implements StatisticAPI {

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
        hierarchy.append("Hello World!")
                .append(LINE_BREAK);

        // Setup query. Assume the referenced person from id is called Bob.
        String query = "SELECT c FROM TagClassIsSubclassOf c WHERE c.id.parentId = :id";
        Query typedQuery = entityManager.createQuery(query, TagClassIsSubclassOf.class);
        typedQuery.setParameter("id", tagId);

        // Run query
        List<TagClassIsSubclassOf> resultList = typedQuery.getResultList();

        if (resultList.isEmpty()) {
            return "Tag class does not exist or has no children.";
        }

        Map<String, TagClass> tagClassTreemap = new TreeMap<>();
        Set<TagClass> visited = new HashSet<>();
        int counter = 1;
        log.info(counter + " : " + tagId);
        for (TagClassIsSubclassOf r : resultList) {
            // TODO recursively iterate through resultList
            TagClass childTag = r.getChildTag();
            visited.add(childTag);
            //log.info(counter + " : " + childTag.getId());
            buildTagHierarchy(childTag, String.valueOf(counter), tagClassTreemap, visited);
            counter++;
        }


        log.debug("<-- getTagClassHierarchy(tagId = {})", tagId);
        return hierarchy.toString();
    }

    private void buildTagHierarchy(TagClass node, String depth, Map<String, TagClass> hierarchy, Set<TagClass> visited) {
        log.debug("--> buildTagHierarchy(node = {}, {})", node.getId(), node.getName());
        //Set<TagClass> children = extractChildNodes(node.getChildren());
        //log.debug("children size = {}", children.size());
        Set<TagClassIsSubclassOf> children = node.getParents();

        hierarchy.put(depth, node);
        visited.add(node);
        log.info("{} : {}", depth, node.getId());
        boolean nodeHasChildren = !children.isEmpty();
        boolean nodeIsNew = !visited.contains(node);
        if (nodeHasChildren && nodeIsNew) {
            int counter = 1;
            for (TagClassIsSubclassOf c : children) {
                String newDepth = depth + "." + counter;
                Long childId = c.getChildTag().getId();
                Long parentId = c.getParentTag().getId();
                log.info("child id = {} ", childId);
                log.info("parent id = {}", parentId);
                buildTagHierarchy(c.getChildTag(), newDepth, hierarchy, visited);
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
    public String getPopularComments(long likes) {
        return null;
    }

    @Override
    public String getMostPostingCountry() {
        return null;
    }
}
