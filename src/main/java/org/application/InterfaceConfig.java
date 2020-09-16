package org.application;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves the xml customizable data from configuration.xml.
 */
@Slf4j
final class InterfaceConfig {
    private static final String FILE_NAME = "src/main/resources/configuration.xml";

    /**
     * Retrieves the Content of Root Menu required to build the user interface.
     *
     * @return Content of root menu as an ArrayList.
     * @throws IOException if properties cannot be reached.
     */
    static List<String> getPropertyValues() {
        log.debug("--> getPropertyValues().");
        List<String> properties = new ArrayList<>();

        try {
            File configFile = new File(FILE_NAME);

            Xml config = new Xml(new FileInputStream(configFile), "config");
            Xml rootMenu = config.child("RootMenu");
            String title = rootMenu.child("Title").content();
            properties.add(title);
            Xml entries = rootMenu.child("Entries");
            for (Xml entry : entries.children("Entry")) {
                properties.add(entry.content());
            }
        } catch (Exception e) {
            log.error("Something went wrong here.", e);
        } finally {
            log.debug("<-- getPropertyValues().");
        }
        return properties;
    }

}
