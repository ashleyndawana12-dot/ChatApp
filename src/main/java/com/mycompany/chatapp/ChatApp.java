
package com.mycompany.chatapp;

import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("QUICKCHAT REGISTRATION");
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        System.out.print("Create a username: ");
        String username = input.nextLine();

        System.out.print("Create a password: ");
        String password = input.nextLine();

        System.out.print("Enter cellphone number (+27 followed by 9 digits): ");
        String cellphone = input.nextLine();

        Login user = new Login(firstName, lastName, username, password, cellphone);

        if (!user.checkUserName()) {
            System.out.println("Username is incorrectly formatted.");
            input.close();
            return;
        }

        if (!user.checkPasswordComplexity()) {
            System.out.println("Password is incorrectly formatted.");
            input.close();
            return;
        }

        if (!user.checkCellPhoneNumber()) {
            System.out.println("Cell phone number is incorrectly formatted.");
            input.close();
            return;
        }

        System.out.println(user.registerUser());

        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("\nLOGIN");
            System.out.print("Enter username: ");
            user.setLoginUsername(input.nextLine());

            System.out.print("Enter password: ");
            user.setLoginPassword(input.nextLine());

            loggedIn = user.loginUser();
            System.out.println(user.returnLoginStatus());

            if (!loggedIn) {
                System.out.println("Please try logging in again.");
            }
        }

        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages would you like to enter? ");
        int messageLimit;

        try {
            messageLimit = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException exception) {
            System.out.println("Please enter a valid number.");
            input.close();
            return;
        }

        int messagesEntered = 0;
        int messageNumber = 1;
        boolean running = true;

        while (running) {
            System.out.println("\n1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages");
            System.out.print("Choose an option: ");

            String option = input.nextLine();

            switch (option) {

                case "1":
                    if (messagesEntered >= messageLimit) {
                        System.out.println("You have reached your message limit.");
                        break;
                    }

                    System.out.print("Enter recipient number (+27 followed by 9 digits): ");
                    String recipient = input.nextLine();

                    while (!recipient.matches("\\+27[0-9]{9}")) {
                        System.out.println("Incorrect cellphone number format.");
                        System.out.print("Enter recipient number again: ");
                        recipient = input.nextLine();
                    }

                    System.out.print("Enter your message (maximum 250 characters): ");
                    String messageText = input.nextLine();

                    while (messageText.length() > 250) {
                        System.out.println("Message exceeds 250 characters.");
                        System.out.print("Enter your message again: ");
                        messageText = input.nextLine();
                    }

                    Message message = new Message(
                            messageNumber, recipient, messageText);

                    System.out.println("Message ID: " + message.getMessageID());
                    System.out.println("Message Hash: " + message.getMessageHash());

                    System.out.println("\n1. Send Message");
                    System.out.println("2. Disregard Message");
                    System.out.println("3. Store Message to send later");
                    System.out.print("Choose an option: ");

                    int action;

                    try {
                        action = Integer.parseInt(input.nextLine());
                    } catch (NumberFormatException exception) {
                        System.out.println("Invalid option.");
                        break;
                    }

                    String status = message.SentMessage(action);
                    System.out.println(status);

                    if (action == 1) {
                        System.out.println("\nMessage details:");
                        System.out.println("Message ID: " + message.getMessageID());
                        System.out.println("Message Hash: " + message.getMessageHash());
                        System.out.println("Recipient: " + message.getRecipient());
                        System.out.println("Message: " + message.getMessageText());
                    }

                    if (action >= 1 && action <= 3) {
                        messagesEntered++;
                        messageNumber++;
                    }

                    break;

                case "2":
                    System.out.println(MessageDisplay());
                    break;

                case "3":
                    System.out.println("Total messages successfully sent: "
                            + Message.getSentMessagesArray().length);
                    System.out.println("Thank you for using QuickChat.");
                    running = false;
                    break;

                case "4":
                    storedMessagesMenu(input);
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3 or 4.");
            }
        }

        input.close();
    }

    // Displays all recently sent messages.
    private static String MessageDisplay() {
        Message[] sent = Message.getSentMessagesArray();

        if (sent.length == 0) {
            return "No messages have been sent.";
        }

        StringBuilder output = new StringBuilder();

        for (Message item : sent) {
            output.append("Message ID: ").append(item.getMessageID()).append("\n");
            output.append("Message Hash: ").append(item.getMessageHash()).append("\n");
            output.append("Recipient: ").append(item.getRecipient()).append("\n");
            output.append("Message: ").append(item.getMessageText()).append("\n\n");
        }

        return output.toString();
    }

    // Provides the required Part 3 stored-message functions.
    private static void storedMessagesMenu(Scanner input) {

        boolean menuRunning = true;

        while (menuRunning) {
            System.out.println("\nSTORED MESSAGES");
            System.out.println("1. Display senders and recipients");
            System.out.println("2. Display longest message");
            System.out.println("3. Search message by ID");
            System.out.println("4. Search messages by recipient");
            System.out.println("5. Delete message by hash");
            System.out.println("6. Display full message report");
            System.out.println("7. Return to main menu");
            System.out.print("Choose an option: ");

            String choice = input.nextLine();

            switch (choice) {

                case "1":
                    System.out.println(
                            Message.displayStoredSendersAndRecipients());
                    break;

                case "2":
                    System.out.println("Longest stored message:");
                    System.out.println(Message.displayLongestMessage());
                    break;

                case "3":
                    System.out.print("Enter message ID: ");
                    String id = input.nextLine();
                    System.out.println(Message.searchMessageByID(id));
                    break;

                case "4":
                    System.out.print("Enter recipient number: ");
                    String recipient = input.nextLine();
                    System.out.println(
                            Message.searchMessagesByRecipient(recipient));
                    break;

                case "5":
                    System.out.print("Enter message hash: ");
                    String hash = input.nextLine();
                    System.out.println(Message.deleteMessageByHash(hash));
                    break;

                case "6":
                    System.out.println(
                            Message.displayStoredMessageReport());
                    break;

                case "7":
                    menuRunning = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
