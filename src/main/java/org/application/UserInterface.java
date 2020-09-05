package org.application;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
public class UserInterface {
    private static BufferedReader scanner;
    private List<String> mainMenu;
    private TransactionHandler transactionHandler;

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

    private void processUserInput(int option) {
        log.debug("--> processUserInput().");
        if (option > 0 && option < 7) {
            System.out.println("Enter user id");
            Long userId = Long.valueOf(getUserInput());
            transactionHandler.runTransactionFor(option, userId);
        } else {
            System.out.println("You must enter a valid option.");
        }
        log.debug("<-- processUserInput().");
    }


}
