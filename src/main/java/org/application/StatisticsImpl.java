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
        TagClass root = resultList.get(0).getParentTag();
        Map<String, TagClass> tagClassTreemap = new TreeMap<>();
        buildTagHierarchy(root, "1", tagClassTreemap);
        
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
        //log.debug("children size = {}", children.size());
        Set<TagClassIsSubclassOf> children = node.getParents();

        hierarchy.put(depth, node);
        log.info("{} : {}", depth, node.getName());
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
    public String getPopularComments(long likes) {
        return null;
    }

    @Override
    public String getMostPostingCountry() {
        return null;
    }
}
