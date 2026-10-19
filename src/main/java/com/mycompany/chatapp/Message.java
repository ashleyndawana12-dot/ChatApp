
package com.mycompany.chatapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class Message {

    // Stores the message ID, message number, recipient, text, and hash.
    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    // Keeps track of messages that have been successfully sent.
    private static final List<Message> sentMessages = new ArrayList<>();

    // Creates a message with a newly generated ID.
    public Message(int messageNumber, String recipient, String messageText) {
        this(generateMessageID(), messageNumber, recipient, messageText);
    }

    // Initialises the message fields and creates its hash.
    public Message(String messageID, int messageNumber,
                   String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Generates a random numeric message ID containing ten digits.
    public static String generateMessageID() {
        long id = ThreadLocalRandom.current().nextLong(
                1_000_000_000L, 10_000_000_000L);
        return String.valueOf(id);
    }

    // Checks whether the message ID contains exactly ten digits.
    public boolean checkMessageID() {
        return messageID.matches("\\d{10}");
    }

    // Validates the recipient's South African cellphone number format.
    private boolean isRecipientValid() {
        return recipient.matches("\\+27[0-9]{9}");
    }

    // Returns feedback about whether the cellphone number is valid.
    public String checkRecipientCell() {
        if (isRecipientValid()) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.";
    }

    // Checks that the message does not exceed 250 characters.
    public boolean checkMessageLength() {
        return messageText != null && messageText.length() <= 250;
    }

    // Provides feedback about the message's character limit.
    public String messageLengthStatus() {
        if (checkMessageLength()) {
            return "Message ready to send.";
        }

        int excess = messageText == null ? 0 : messageText.length() - 250;
        return "Message exceeds 250 characters by " + excess
                + "; please reduce the size.";
    }

    // Builds the hash using the first two ID digits, message number,
    // and the first and last words of the message.
    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1]
                .replaceAll("[^A-Za-z0-9]", "");

        return messageID.substring(0, 2) + ":"
                + messageNumber + ":"
                + (firstWord + lastWord).toUpperCase();
    }

    // Displays the available message actions and reads the user's choice.
    public String SentMessage() {
        Scanner input = new Scanner(System.in);

        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Choose an option: ");

        int choice = input.nextInt();
        return SentMessage(choice);
    }

    // Processes the selected action for the current message.
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                return "Message successfully sent.";

            case 2:
                return "Press 0 to delete the message.";

            case 3:
                return storeMessage();

            default:
                return "Invalid option. Please choose 1, 2, or 3.";
        }
    }

    // Formats the details of all successfully sent messages for display.
    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent.";
        }

        StringBuilder output = new StringBuilder();

        for (Message item : sentMessages) {
            output.append("Message ID: ").append(item.messageID).append("\n");
            output.append("Message Hash: ").append(item.messageHash).append("\n");
            output.append("Recipient: ").append(item.recipient).append("\n");
            output.append("Message: ").append(item.messageText).append("\n\n");
        }

        return output.toString();
    }

    // Returns the number of messages recorded as successfully sent.
    public int returnTotalMessages() {
        return sentMessages.size();
    }

    // Stores the current message by adding its JSON object to messages.json.
    public String storeMessage() {
        Path file = Path.of("messages.json");

        String jsonObject = "{"
                + "\"messageID\":\"" + escapeJson(messageID) + "\","
                + "\"messageNumber\":" + messageNumber + ","
                + "\"recipient\":\"" + escapeJson(recipient) + "\","
                + "\"message\":\"" + escapeJson(messageText) + "\","
                + "\"messageHash\":\"" + escapeJson(messageHash) + "\""
                + "}";

        try {
            // Reads the existing JSON array or starts a new empty array.
            String existing = Files.exists(file)
                    ? Files.readString(file).trim() : "[]";

            // Checks that the existing file ends with a closing array bracket.
            if (!existing.endsWith("]")) {
                return "Unable to store message: messages.json is not a valid JSON array.";
            }

            // Removes the final bracket so the new message can be appended.
            String content = existing.substring(0, existing.length() - 1).trim();

            String updated;
            if (content.equals("[")) {
                updated = "[" + jsonObject + "]";
            } else {
                updated = content + "," + System.lineSeparator()
                        + jsonObject + "]";
            }

            // Saves the updated JSON array to the file.
            Files.writeString(file, updated);
            return "Message successfully stored.";

        } catch (IOException exception) {
            // Reports a file error if the message cannot be saved.
            return "Unable to store message: " + exception.getMessage();
        }
    }

    // Escapes special characters so message text can be written in JSON.
    private String escapeJson(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Returns the message ID.
    public String getMessageID() {
        return messageID;
    }

    // Returns the message hash.
    public String getMessageHash() {
        return messageHash;
    }

    // Returns the recipient's cellphone number.
    public String getRecipient() {
        return recipient;
    }

    // Returns the message text.
    public String getMessageText() {
        return messageText;
    }
}
