package org.application;

import lombok.extern.slf4j.Slf4j;
import org.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Provides a user interface to navigate the database functions concerning both
 * {@link PersonRelatedImpl} and {@link StatisticsImpl}.
 * Provides a starting point for {@link Main} to initialize the application,
 * uses {@link ConsoleUtils} to get user input and forwards it to {@link TransactionHandler}.
 */
@Slf4j
public class UserInterface extends ConsoleUtils {
    private static BufferedReader scanner;
    private List<String> mainMenu;
    private TransactionHandler transactionHandler;

    /**
     * Standard no argument constructor. Triggers {@link #initialize()}.
     */
    public UserInterface() {
        initialize();
    }

    public void displayUserInterface() throws NullPointerException {
        log.debug("--> displayUserInterface().");
        for (String s : mainMenu) {
            System.out.println(s);
        }
        log.debug("<-- displayUserInterface().");
    }

    private void initialize() {
        log.debug("--> initialize().");
        try {
            scanner = new BufferedReader(new InputStreamReader(System.in));
            mainMenu = InterfaceConfig.getPropertyValues();
            transactionHandler = new TransactionHandler();
            runMainMenu();
        } catch (IOException e) {
            log.error("IOException occurred.", e);
        } catch (Exception e) {
            log.error("Something went wrong here.", e);
        } finally {
            log.debug("<-- initialize().");
        }
    }

    private void close() {
        try {
            System.out.println("Good Bye!");
            scanner.close();
            transactionHandler.closeTransactionHandler();
        } catch (IOException e) {
            log.error("IOException occurred.", e);
        }
    }

    private void runMainMenu() throws IOException {

        boolean userIsRequestingInputs = true;

        while (userIsRequestingInputs) {
            displayUserInterface();
            System.out.println("Pick an option.");
            long selectedOption = getUserInput();

            if (selectedOption == 0) {
                userIsRequestingInputs = false;
            } else {
                processUserInput((int) selectedOption);
            }
        }
        close();
    }

    /*
    private long getUserInput() {
        log.debug("--> processUserInput().");
        long userInput = -1;
        try {
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

     */

    /**
     * Figure out how often the user needs to be prompted for a user id.
     *
     * @param option
     */
    private void processUserInput(int option) {
        log.debug("--> processUserInput().");
        Long userId = null;
        Long userId2 = null;
        // TODO for tagClassHierarchy we have to enter a tag class id not user id
        // TODO for popularComment we have to enter a minimum number of likes
        // Prompt the user for either 1 or 2 person ids.
        if (option > 0 && option < 9) {
            System.out.println("Enter user id");
            userId = Long.valueOf(getUserInput());
        } else if (option != 9) {
            System.out.println("You must enter a valid option.");
        }
        if (option == 3 || option == 6) {
            System.out.println("Enter second user id");
            userId2 = Long.valueOf(getUserInput());
        }

        // Run TransactionHandler
        if (userId == null) {
            transactionHandler.runTransactionFor(option);
        } else if (userId2 == null) {
            transactionHandler.runTransactionFor(option, userId);
        } else {
            transactionHandler.runTransactionFor(option, userId, userId2);
        }

        log.debug("<-- processUserInput().");
    }


}
