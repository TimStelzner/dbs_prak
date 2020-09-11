package org.application;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.NoResultException;
import java.util.NoSuchElementException;

@Data
@Slf4j
public class TransactionHandler {
    private PersonRelatedImpl personRelated;
    private StatisticsImpl statisticsRelated;

    public TransactionHandler() {
        personRelated = new PersonRelatedImpl();
        statisticsRelated = new StatisticsImpl();
    }

    public String runTransactionFor(int option, long... ids) {
        log.debug("--> runTransactionFor(option = {}, id = {})", option, ids);
        String queryResult = "";
        long id = 0;
        long id2 = 0;
        try {

            // Setup id parameters according to varargs
            if (ids.length > 0) {
                id = ids[0];
                if (ids.length == 2) {
                    id2 = ids[1];
                }
            } else {
                throw new Exception("Method has received no parameters. At least 1 is required.");
            }

            // Process option and run corresponding method
            switch (option) {
                case 1:
                    queryResult = personRelated.getProfile(id);
                    break;
                case 2:
                    queryResult = personRelated.getCommonInterestsOfMyFriends(id);
                    break;
                case 3:
                    queryResult = personRelated.getCommonFriends(id, id2);
                    break;
                case 4:
                    queryResult = personRelated.getPersonsWitMostCommonInterests(id);
                    break;
                case 5:
                    queryResult = personRelated.getJobRecommendation(id);
                    break;
                case 6:
                    queryResult = personRelated.getShortestFriendshipPath(id, id2);
                    break;
                case 7:
                    queryResult = statisticsRelated.getTagClassHierarchy(id);
                    break;
                case 8:
                    queryResult = statisticsRelated.getPopularComments((int) id);
                    break;
            }
            log.info(queryResult);

        } catch (NoResultException | NoSuchElementException e) {
            log.info("No result found for given query.");
            log.debug("NoResultException.", e);
        } catch (Exception e) {
            log.error("Something went wrong here.", e);
        } finally {
            log.debug("<-- runTransactionFor().");
        }
        return queryResult;
    }

    public void closeTransactionHandler() {
        // TODO personRelated is missing
        statisticsRelated.closeStatistics();
    }
}
