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

    /**
     * Runs transaction query according to input.
     * Uses {@link PersonRelatedImpl} and {@link StatisticsImpl}.
     * Takes the query result and passes it back to {@link UserInterface}
     *
     * @param option
     * @param ids
     * @return the result of the query as a String.
     */
    // TODO Use a list rather than ... varargs
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
                case 9:
                    queryResult = statisticsRelated.getMostPostingCountry();
                    break;
            }
            log.info(queryResult);

        } catch (NoResultException | NoSuchElementException e) {
            log.debug("NoResultException.", e);
        } catch (Exception e) {
            log.error("Something went wrong here.", e);
        } finally {
            log.debug("<-- runTransactionFor().");
        }
        return queryResult;
    }

    /**
     * Closes TransactionHandler by running closing methods for class objects.
     */
    public void closeTransactionHandler() {
        log.debug("--> closeTransactionHandler.");
        statisticsRelated.closeStatistics();
        personRelated.closePersonRelated();
        log.debug("<-- closeTransactionHandler.");
    }
}