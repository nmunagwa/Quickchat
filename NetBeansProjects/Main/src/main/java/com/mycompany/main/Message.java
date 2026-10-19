package com.mycompany.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents one QuickChat message and keeps track of every message
 * sent or stored while the program is running.
 */
public class Message {

    // All messages that were sent while the program is running
    private static final ArrayList<Message> sentMessages = new ArrayList<>();
    // All messages stored to send later
    private static final ArrayList<Message> storedMessages = new ArrayList<>();
    // Counts the messages that were created (used as the message number)
    private static int messageCounter = 0;

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    // Creates a message with a randomly generated ten-digit ID
    public Message(String recipient, String messageText) {
        this(generateMessageID(), recipient, messageText);
    }

    // Creates a message with a given ID (used for testing)
    public Message(String messageID, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageCounter++;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Generates a random ten-digit number as a String (leading zeros allowed)
    public static String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    // Message ID must not be more than ten characters
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Recipient must start with an international code (+)
    public String checkRecipientCell() {
        return validateRecipient(recipient);
    }

    public static String validateRecipient(String recipient) {
        if (recipient != null && recipient.matches("^\\+[0-9]{10,12}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an "
                + "international code. Please correct the number and try again.";
    }

    // Message must not exceed 250 characters
    public static String checkMessageLength(String text) {
        if (text.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = text.length() - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    // Hash = first two digits of ID : message number : FIRSTWORDLASTWORD
    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        String prefix = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        return (prefix + ":" + messageNumber + ":" + first + last).toUpperCase();
    }

    // choice: 1 = send, 2 = disregard, 3 = store for later
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    // Returns the details of all messages sent while the program is running
    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append(m.toString()).append("\n");
        }
        return sb.toString();
    }

    // Returns the total number of messages sent
    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    // Stores the message in messages.json (the file holds a JSON array)
    public void storeMessage() {
        storedMessages.add(this);
        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write("[\n");
            for (int i = 0; i < storedMessages.size(); i++) {
                writer.write(storedMessages.get(i).toJson());
                writer.write(i < storedMessages.size() - 1 ? ",\n" : "\n");
            }
            writer.write("]\n");
        } catch (IOException e) {
            System.out.println("Could not write messages.json: " + e.getMessage());
        }
    }

    // Converts this message to a JSON object
    private String toJson() {
        return "  {\n"
                + "    \"messageID\": \"" + escape(messageID) + "\",\n"
                + "    \"messageHash\": \"" + escape(messageHash) + "\",\n"
                + "    \"recipient\": \"" + escape(recipient) + "\",\n"
                + "    \"message\": \"" + escape(messageText) + "\"\n"
                + "  }";
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    // Clears all saved state (used by unit tests)
    public static void resetAll() {
        sentMessages.clear();
        storedMessages.clear();
        messageCounter = 0;
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }

    // Order required: Message ID, Message Hash, Recipient, Message
    @Override
    public String toString() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText + "\n";
    }
}