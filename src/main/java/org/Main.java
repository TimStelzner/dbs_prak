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
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("dbs");

    public static void main(String[] args) {
        log.debug("--> Main().");

        try {
            UserInterface ui = new UserInterface();
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
