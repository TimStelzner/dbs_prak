package org.application;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.NoResultException;

@Data
@Slf4j
public class TransactionHandler {
    private PersonRelatedImpl personRelated;

    public TransactionHandler() {
        personRelated = new PersonRelatedImpl();
    }

    public String runTransactionFor(int option, long id) {
        log.debug("--> runTransactionFor(option = {}, id = {})", option, id);
        String queryResult = "";
        try {
            switch (option) {
                case 1:
                    queryResult = personRelated.getProfile(id);
                    break;
                case 2:
                    queryResult = personRelated.getCommonInterestsOfMyFriends(id);
                    break;
            }
            log.info(queryResult);

        } catch (NoResultException e) {
            log.info("No result found for given query.");
            log.debug("NoResultException.", e);
        } catch (Exception e) {
            log.error("Something went wrong here.", e);
        } finally {
            log.debug("<-- runTransactionFor().");
        }
        return queryResult;
    }
}
