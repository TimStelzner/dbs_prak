package org.application;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// TODO Should put this in PersonRelatedImpl and UserInterface

/**
 * Provides utility methods for the user interface and console input/output.
 */
@Slf4j
public abstract class ConsoleUtils {
    // TODO Is this the best approach? We have another scanner object in UserInterface. Yuck!
    static long getUserInput() {
        log.debug("--> processUserInput().");

        long userInput = -1;
        try {
            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
            String input = scanner.readLine();
            userInput = Long.parseLong(input);
            log.debug("You typed {}", input);
        } catch (NumberFormatException e) {
            log.info("You did not enter a number.");
            return -1;
        } catch (IOException e) {
            log.error("IOException occurred.", e);
        } finally {
            log.debug("<-- processUserInput(). Return [{}]", userInput);
        }
        return userInput;
    }

    /**
     * Provides white space padding for a long list to help left-align its columns.
     * Takes a string and pads the right side with white space characters
     * until it reaches the given final length.
     *
     * @param inStr       the string that needs to be padded.
     * @param finalLength The final length of the padded string.
     * @return the padded string.
     */
    static String insertRightPad(String inStr, int finalLength) {
        return (inStr + "                          "
        ).substring(0, finalLength);
    }
}
