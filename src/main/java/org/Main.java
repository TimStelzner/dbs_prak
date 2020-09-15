package org;

import lombok.extern.slf4j.Slf4j;
import org.application.UserInterface;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 * Main class to initialize {@link UserInterface}.
 */
@Slf4j
public class Main {
    // Note, that name must match persistence-unit name in persistence.xml
    // TODO Dont we want the entity manager in Transaction Handler?
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("dbs");

    public static void main(String[] args) {
        log.debug("--> Main().");

        try {
            //TransactionUtils.selectAll(SqlUtils.PERSON_KNOWS_SYMMETRIC);
            //TransactionUtils.getPost(001232L);
            //TransactionUtils.getPost(8400046488L);
            UserInterface ui = new UserInterface();
            //TransactionHandler th = new TransactionHandler();
            //th.runTransactionFor(6, 3298534883405L, 3298534883392L);
            //th.runTransactionFor(7, 149L);
            //th.runTransactionFor(7, 211L);
            //String s = th.runTransactionFor(5, 12094627905604L);
            //String s = th.runTransactionFor(2, 2199023255625L);
            //System.out.println(s);

        } catch (NoResultException e) {
            log.error("No match found for your query.", e);
        } catch (PersistenceException e) {
            log.error("Database persistence error.", e);
        } catch (Exception e) {
            log.error("Something went wrong here.", e);
        } finally {
            ENTITY_MANAGER_FACTORY.close();
        }

        log.debug("<-- Main().");
    }

}
