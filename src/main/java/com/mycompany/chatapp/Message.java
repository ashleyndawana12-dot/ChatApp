
package com.mycompany.chatapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    // Message lists.
    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> disregardedMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();

    // Dynamically populated arrays required for Part 3.
    private static Message[] sentMessagesArray = new Message[0];
    private static Message[] disregardedMessagesArray = new Message[0];
    private static Message[] storedMessagesArray = new Message[0];
    private static String[] messageHashesArray = new String[0];
    private static String[] messageIDsArray = new String[0];

    private static final Path FILE = Path.of("messages.json");

    public Message(int messageNumber, String recipient, String messageText) {
        this(generateMessageID(), messageNumber, recipient, messageText);
    }

    public Message(String messageID, int messageNumber,
                   String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Generates a random ten-digit message ID.
    public static String generateMessageID() {
        long id = ThreadLocalRandom.current().nextLong(
                1_000_000_000L, 10_000_000_000L);
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID.matches("\\d{10}");
    }

    private boolean isRecipientValid() {
        return recipient.matches("\\+27[0-9]{9}");
    }

    public String checkRecipientCell() {
        if (isRecipientValid()) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.";
    }

    public boolean checkMessageLength() {
        return messageText != null && messageText.length() <= 250;
    }

    public String messageLengthStatus() {
        if (checkMessageLength()) {
            return "Message ready to send.";
        }

        int excess = messageText == null ? 0 : messageText.length() - 250;
        return "Message exceeds 250 characters by " + excess
                + "; please reduce the size.";
    }

    // Creates the message hash.
    public String createMessageHash() {
        String[] words = messageText == null || messageText.trim().isEmpty()
                ? new String[]{"EMPTY"}
                : messageText.trim().split("\\s+");

        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1]
                .replaceAll("[^A-Za-z0-9]", "");

        String idStart = messageID.length() >= 2
                ? messageID.substring(0, 2) : messageID;

        return idStart + ":" + messageNumber + ":"
                + (firstWord + lastWord).toUpperCase();
    }

    // Processes the user's selected action.
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                refreshArrays();
                return "Message successfully sent.";

            case 2:
                disregardedMessages.add(this);
                refreshArrays();
                return "Message successfully disregarded.";

            case 3:
                return storeMessage();

            default:
                return "Invalid option. Please choose 1, 2, or 3.";
        }
    }

    public String printMessages() {
        return displayMessages(sentMessages);
    }

    private static String displayMessages(List<Message> messages) {
        if (messages.isEmpty()) {
            return "No messages available.";
        }

        StringBuilder output = new StringBuilder();

        for (Message item : messages) {
            output.append("Message ID: ").append(item.messageID).append("\n");
            output.append("Message Hash: ").append(item.messageHash).append("\n");
            output.append("Recipient: ").append(item.recipient).append("\n");
            output.append("Message: ").append(item.messageText).append("\n\n");
        }

        return output.toString();
    }

    public int returnTotalMessages() {
        return sentMessages.size();
    }

    // Stores a message in the JSON file.
    public String storeMessage() {
        try {
            loadStoredMessages();

            storedMessages.removeIf(
                    item -> item.messageID.equals(messageID));

            storedMessages.add(this);

            refreshArrays();
            saveStoredMessages();

            return "Message successfully stored.";

        } catch (IOException e) {
            return "Error storing message: " + e.getMessage();
        }
    }

    // Reads JSON records into the stored messages list and array.
    public static void loadStoredMessages() throws IOException {
        storedMessages.clear();

        if (!Files.exists(FILE)) {
            refreshArrays();
            return;
        }

        String json = Files.readString(FILE);

        Pattern objectPattern = Pattern.compile("\\{([^{}]*)\\}");
        Matcher objectMatcher = objectPattern.matcher(json);

        while (objectMatcher.find()) {
            String object = objectMatcher.group();

            String id = readString(object, "messageID");
            String number = readNumber(object, "messageNumber");
            String recipient = readString(object, "recipient");
            String text = readString(object, "message");

            if (id != null && number != null
                    && recipient != null && text != null) {

                Message message = new Message(
                        id, Integer.parseInt(number), recipient, text);

                storedMessages.add(message);
            }
        }

        refreshArrays();
    }

    // Extracts a string value from a JSON object.
    private static String readString(String object, String key) {
        Pattern pattern = Pattern.compile(
                "\"" + Pattern.quote(key)
                + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");

        Matcher matcher = pattern.matcher(object);

        if (!matcher.find()) {
            return null;
        }

        return unescapeJson(matcher.group(1));
    }

    private static String readNumber(String object, String key) {
        Pattern pattern = Pattern.compile(
                "\"" + Pattern.quote(key) + "\"\\s*:\\s*(\\d+)");

        Matcher matcher = pattern.matcher(object);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static String unescapeJson(String value) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);

            if (character == '\\' && i + 1 < value.length()) {
                char next = value.charAt(++i);

                switch (next) {
                    case '"':
                        result.append('"');
                        break;
                    case '\\':
                        result.append('\\');
                        break;
                    case 'n':
                        result.append('\n');
                        break;
                    case 'r':
                        result.append('\r');
                        break;
                    case 't':
                        result.append('\t');
                        break;
                    default:
                        result.append(next);
                        break;
                }
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    // Saves stored messages to the JSON file.
    private static void saveStoredMessages() throws IOException {
        StringBuilder json = new StringBuilder("[");
        boolean first = true;

        for (Message item : storedMessages) {
            if (!first) {
                json.append(",");
            }

            json.append("{")
                    .append("\"messageID\":\"")
                    .append(escapeJson(item.messageID)).append("\",")
                    .append("\"messageNumber\":")
                    .append(item.messageNumber).append(",")
                    .append("\"recipient\":\"")
                    .append(escapeJson(item.recipient)).append("\",")
                    .append("\"message\":\"")
                    .append(escapeJson(item.messageText)).append("\",")
                    .append("\"messageHash\":\"")
                    .append(escapeJson(item.messageHash)).append("\"")
                    .append("}");

            first = false;
        }

        json.append("]");
        Files.writeString(FILE, json.toString());
    }

    private static String escapeJson(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Displays senders and recipients of stored messages.
    public static String displayStoredSendersAndRecipients() {
        try {
            loadStoredMessages();
        } catch (IOException exception) {
            return "Unable to read stored messages.";
        }

        StringBuilder output = new StringBuilder();

        for (Message item : storedMessages) {
            output.append("Sender: Developer\n");
            output.append("Recipient: ").append(item.recipient).append("\n\n");
        }

        return output.length() == 0
                ? "No stored messages available." : output.toString();
    }

    // Finds the longest stored message.
    public static String displayLongestMessage() {
        try {
            loadStoredMessages();
        } catch (IOException exception) {
            return "Unable to read stored messages.";
        }

        if (storedMessages.isEmpty()) {
            return "No stored messages available.";
        }

        Message longest = storedMessages.get(0);

        for (Message item : storedMessages) {
            if (item.messageText.length() > longest.messageText.length()) {
                longest = item;
            }
        }

        return longest.messageText;
    }

    // Searches for a stored message by ID.
    public static String searchMessageByID(String id) {
        try {
            loadStoredMessages();
        } catch (IOException exception) {
            return "Unable to read stored messages.";
        }

        for (Message item : storedMessages) {
            if (item.messageID.equals(id)) {
                return "Recipient: " + item.recipient
                        + "\nMessage: " + item.messageText;
            }
        }

        return "Message ID not found.";
    }

    // Searches sent and stored messages by recipient.
    public static String searchMessagesByRecipient(String recipient) {
        try {
            loadStoredMessages();
        } catch (IOException exception) {
            return "Unable to read stored messages.";
        }

        StringBuilder output = new StringBuilder();

        for (Message item : sentMessages) {
            if (item.recipient.equals(recipient)) {
                output.append(item.messageText).append("\n");
            }
        }

        for (Message item : storedMessages) {
            if (item.recipient.equals(recipient)
                    && sentMessages.stream().noneMatch(
                            sent -> sent.messageID.equals(item.messageID))) {
                output.append(item.messageText).append("\n");
            }
        }

        return output.length() == 0
                ? "No messages found for this recipient."
                : output.toString();
    }

    // Deletes a stored message by its hash.
    public static String deleteMessageByHash(String hash) {
        try {
            loadStoredMessages();

            Message found = null;

            for (Message item : storedMessages) {
                if (item.messageHash.equals(hash)) {
                    found = item;
                    break;
                }
            }

            if (found == null) {
                return "Message hash not found.";
            }

            storedMessages.remove(found);

            refreshArrays();
            saveStoredMessages();

            return "Message: \"" + found.messageText
                    + "\" successfully deleted.";

        } catch (IOException exception) {
            return "Unable to delete message: " + exception.getMessage();
        }
    }

    // Displays the full stored-message report.
    public static String displayStoredMessageReport() {
        try {
            loadStoredMessages();
        } catch (IOException exception) {
            return "Unable to read stored messages.";
        }

        return displayMessages(storedMessages);
    }

    // Returns the five dynamically populated arrays.
    public static Message[] getSentMessagesArray() {
        refreshArrays();
        return sentMessagesArray;
    }

    public static Message[] getDisregardedMessagesArray() {
        refreshArrays();
        return disregardedMessagesArray;
    }

    public static Message[] getStoredMessagesArray() {
        try {
            loadStoredMessages();
        } catch (IOException exception) {
            return new Message[0];
        }

        return storedMessagesArray;
    }

    public static String[] getMessageHashesArray() {
        refreshArrays();
        return messageHashesArray;
    }

    public static String[] getMessageIDsArray() {
        refreshArrays();
        return messageIDsArray;
    }

    // Combines all message categories.
    private static List<Message> getAllMessages() {
        List<Message> all = new ArrayList<>();

        all.addAll(sentMessages);
        all.addAll(disregardedMessages);
        all.addAll(storedMessages);

        return all;
    }

    // Updates all five arrays.
    private static void refreshArrays() {

        sentMessagesArray = sentMessages.toArray(new Message[0]);

        disregardedMessagesArray =
                disregardedMessages.toArray(new Message[0]);

        storedMessagesArray = storedMessages.toArray(new Message[0]);

        List<Message> allMessages = getAllMessages();

        messageHashesArray = new String[allMessages.size()];
        messageIDsArray = new String[allMessages.size()];

        for (int i = 0; i < allMessages.size(); i++) {
            messageHashesArray[i] = allMessages.get(i).messageHash;
            messageIDsArray[i] = allMessages.get(i).messageID;
        }
    }

    // Getters.
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }
}
