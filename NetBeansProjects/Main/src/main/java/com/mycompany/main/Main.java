package com.mycompany.main;

/**
 * @author ST10542449
 */
// Imports Scanner so we can get user input
import java.util.Scanner;

public class Main {

    // Creates Scanner for user input
    static Scanner input = new Scanner(System.in);
    // Stores the user's entered details
    static String username;
    static String password;
    static String cellPhone;
    // Stores the successfully registered details
    static String registeredUsername;
    static String registeredPassword;
    static String registeredCellphone;

    // Username must have exactly 5 characters and contain an underscore
    public static boolean checkUserName(String username) {
        return username.length() == 5 && username.contains("_");
    }

    // Password must have at least 8 characters and contain uppercase,
    // lowercase, number and special characters
    public static boolean checkpasswordComplexity(String password) {
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    // Number must start with +27 followed by exactly 9 digits
    public static boolean checkCellPhone(String number) {
        return number.matches("^\\+27[0-9]{9}$");
    }

    // Registration method: each for loop repeats until the input is valid
    public static void registeredUser() {
        // Username
        for (boolean valid = false; !valid; ) {
            System.out.print("Enter your username: ");
            username = input.nextLine();
            valid = checkUserName(username);
            if (!valid) {
                System.out.println("Username is incorrectly formatted. Please try again.");
            }
        }
        // Password
        for (boolean valid = false; !valid; ) {
            System.out.print("Enter your password: ");
            password = input.nextLine();
            valid = checkpasswordComplexity(password);
            if (!valid) {
                System.out.println("Password is incorrectly formatted. Please try again.");
            }
        }
        // Cellphone number
        for (boolean valid = false; !valid; ) {
            System.out.print("Enter your cellphone number: (+27) ");
            cellPhone = input.nextLine();
            valid = checkCellPhone(cellPhone);
            if (!valid) {
                System.out.println("Phone is incorrectly formatted. Please try again.");
            }
        }
        // Save the valid details
        registeredUsername = username;
        registeredPassword = password;
        registeredCellphone = cellPhone;
        System.out.println("User registered successfully.");
    }

    // Checks the login details; keeps asking until they match
    public static boolean loginUser(String username, String password) {
        for (boolean correct = false; !correct; ) {
            correct = username.equals(registeredUsername)
                    && password.equals(registeredPassword);
            if (!correct) {
                System.out.println("Username or password incorrect, please try again.");
                System.out.print("Enter your username to login: ");
                username = input.nextLine();
                System.out.print("Enter your password to login: ");
                password = input.nextLine();
            }
        }
        return true;
    }

    // Returns the login status message
    public static String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome, it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    // Asks for a whole number and keeps asking until one is entered
    public static int readInt(String prompt) {
        int value = 0;
        for (boolean valid = false; !valid; ) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(input.nextLine().trim());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
        return value;
    }

    // Lets the user enter the set number of messages
    public static void sendMessages(int numMessages) {
        for (int i = 1; i <= numMessages; i++) {
            System.out.println("\n--- Message " + i + " of " + numMessages + " ---");

            // Recipient: repeat until valid
            String recipient = "";
            for (boolean valid = false; !valid; ) {
                System.out.print("Enter the recipient cell number (with international code, e.g. +27718693002): ");
                recipient = input.nextLine().trim();
                String result = Message.validateRecipient(recipient);
                System.out.println(result);
                valid = result.equals("Cell phone number successfully captured.");
            }

            // Message text: repeat until 250 characters or fewer
            String text = "";
            for (boolean valid = false; !valid; ) {
                System.out.print("Enter your message: ");
                text = input.nextLine();
                if (text.trim().isEmpty()) {
                    System.out.println("The message cannot be empty.");
                } else if (text.length() > 250) {
                    System.out.println("Please enter a message of less than 250 characters.");
                    System.out.println(Message.checkMessageLength(text));
                } else {
                    System.out.println("Message sent");
                    valid = true;
                }
            }

            Message message = new Message(recipient, text);
            System.out.println("Message ID generated: " + message.getMessageID());

            // Send, disregard or store
            int choice = 0;
            for (boolean valid = false; !valid; ) {
                choice = readInt("Choose an option:\n 1) Send Message\n 2) Disregard Message\n 3) Store Message to send later\n> ");
                valid = choice >= 1 && choice <= 3;
                if (!valid) {
                    System.out.println("Please choose 1, 2 or 3.");
                }
            }
            System.out.println(message.sentMessage(choice));
            if (choice == 2) {
                int zero = readInt("> ");
                if (zero == 0) {
                    System.out.println("Message deleted.");
                }
            }
            if (choice == 1) {
                System.out.println("\n" + message);
            }
        }
        System.out.println("Total number of messages sent: " + Message.returnTotalMessages());
        System.out.println("\nSent messages:\n" + Message.printMessages());
    }

    // QuickChat menu: runs until the user chooses Quit
    public static void quickChat() {
        System.out.println("Welcome to QuickChat.");
        int numMessages = 0;
        for (boolean valid = false; !valid; ) {
            numMessages = readInt("How many messages do you wish to enter? ");
            valid = numMessages > 0;
            if (!valid) {
                System.out.println("Please enter a number greater than 0.");
            }
        }
        boolean messagesEntered = false;

        for (int option = 0; option != 3; ) {
            option = readInt("\nMenu:\n 1) Send Messages\n 2) Show recently sent messages\n 3) Quit\n> ");
            switch (option) {
                case 1:
                    if (messagesEntered) {
                        System.out.println("You have already entered your " + numMessages + " message(s).");
                    } else {
                        sendMessages(numMessages);
                        messagesEntered = true;
                    }
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Please choose 1, 2 or 3.");
            }
        }
    }

    // Main method where the program starts
    public static void main(String[] args) {
        registeredUser();
        // Ask user to login
        System.out.print("Enter your username to login: ");
        String loginUsername = input.nextLine();
        System.out.print("Enter your password to login: ");
        String loginPassword = input.nextLine();
        boolean loginSuccessful = loginUser(loginUsername, loginPassword);
        System.out.println(returnLoginStatus(loginSuccessful));
        // Only logged-in users can use QuickChat
        if (loginSuccessful) {
            quickChat();
        }
    }
}