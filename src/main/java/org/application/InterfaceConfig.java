package org.application;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
final class InterfaceConfig {
    private static final String PROPERTY_PREFIX = "root";
    private static final String FILE_NAME = "src/main/resources/configuration.xml";

    /**
     * Retrieves the data for a given Fake file number that is defined in the config.properties.
     * Will retrieve the data and convert it into a FakeFile object.
     *
     * @return the converted FakeFile that corresponds to the given number.
     * @throws IOException if properties cannot be reached.
     */
    static List<String> getPropertyValues() {
        log.debug("--> getPropertyValues().");
        List<String> properties = new ArrayList<>();

        try {

            File configFile = new File(FILE_NAME);
            boolean fileExists = configFile.exists();

            Xml config = new Xml(new FileInputStream(configFile), "config");
            Xml rootMenu = config.child("RootMenu");
            String title = rootMenu.child("Title").content();
            properties.add(title);
            Xml entries = rootMenu.child("Entries");
            for (Xml entry : entries.children("Entry")) {
                properties.add(entry.content());
            }


            //XMLConfiguration configuration = new XMLConfiguration(configFile);
            //XMLPropertiesConfiguration configuration = new XMLPropertiesConfiguration(configFile);
            //HierarchicalConfiguration configuration = new HierarchicalConfiguration();


            /*
            String title = configuration.getString(PROPERTY_PREFIX + ".title");
            // properties = configuration.getList(PROPERTY_PREFIX + ".entries.entry");
            String[] stringArray = configuration.getStringArray(PROPERTY_PREFIX + ".entries.entry");
            properties = Arrays.asList(stringArray);

             */

        } catch (Exception e) {
            log.error("Something went wrong here.", e);
        } finally {
            log.debug("<-- getPropertyValues().");
        }
        return properties;
    }

}
