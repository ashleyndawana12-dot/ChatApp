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

        Login user = new Login(
                firstName, lastName, username, password, cellphone);

        if (!user.checkUserName()) {
            System.out.println("Username is incorrectly formatted.");
            System.out.println("It must contain an underscore and be no more than 5 characters.");
            input.close();
            return;
        }

        if (!user.checkPasswordComplexity()) {
            System.out.println("Password is incorrectly formatted.");
            System.out.println("Use at least 8 characters, a capital letter, a number and a special character.");
            input.close();
            return;
        }

        if (!user.checkCellPhoneNumber()) {
            System.out.println("Cell phone number is incorrectly formatted.");
            System.out.println("Use +27 followed by 9 digits.");
            input.close();
            return;
        }

        System.out.println(user.registerUser());

        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("\nLOGIN");
            System.out.print("Enter username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = input.nextLine();

            user.setLoginUsername(loginUsername);
            user.setLoginPassword(loginPassword);

            loggedIn = user.loginUser();
            System.out.println(user.returnLoginStatus());

            if (!loggedIn) {
                System.out.println("Please try logging in again.");
            }
        }

        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages would you like to enter? ");
        int messageLimit = Integer.parseInt(input.nextLine());

        int messagesEntered = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
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
                        System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                        System.out.print("Enter recipient number: ");
                        recipient = input.nextLine();
                    }

                    System.out.print("Enter your message (maximum 250 characters): ");
                    String messageText = input.nextLine();

                    while (messageText.length() > 250) {
                        System.out.println("Please enter a message of less than 250 characters.");
                        System.out.print("Enter your message again: ");
                        messageText = input.nextLine();
                    }

                    Message message = new Message(
                            messagesEntered, recipient, messageText);

                    System.out.println("Message ID generated: "
                            + message.getMessageID());
                    System.out.println("Message ready to send.");
                    System.out.println("Message Hash: "
                            + message.getMessageHash());

                    System.out.println("\n1. Send Message");
                    System.out.println("2. Disregard Message");
                    System.out.println("3. Store Message to send later");
                    System.out.print("Choose an option: ");

                    String action = input.nextLine();
                    int actionNumber;

                    try {
                        actionNumber = Integer.parseInt(action);
                    } catch (NumberFormatException exception) {
                        System.out.println("Invalid option.");
                        break;
                    }

                    String status = message.SentMessage(actionNumber);
                    System.out.println(status);

                    if (actionNumber == 1) {
                        System.out.println("\nMessage details:");
                        System.out.println("Message ID: " + message.getMessageID());
                        System.out.println("Message Hash: " + message.getMessageHash());
                        System.out.println("Recipient: " + message.getRecipient());
                        System.out.println("Message: " + message.getMessageText());
                    } else if (actionNumber == 2) {
                        System.out.print("Enter 0 to confirm deletion: ");
                        String deleteChoice = input.nextLine();

                        if (deleteChoice.equals("0")) {
                            System.out.println("Message disregarded.");
                        }
                    }

                    messagesEntered++;

                    if (messagesEntered >= messageLimit) {
                        System.out.println("\nYou have entered your allocated messages.");
                        System.out.println("Total messages successfully sent: "
                                + message.returnTotalMessages());
                    }
                    break;

                case "2":
                   Message display = new Message(
            "0000000000", 0,
            "+27718693002", "Temporary message");
    System.out.println(display.printMessages());
    break;

                case "3":
                    System.out.println("Total messages successfully sent: "
                            + new Message("0000000000", 0,
                                    "+27718693002", "Temporary message")
                                    .returnTotalMessages());
                    System.out.println("Thank you for using QuickChat.");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        input.close();
    }
}
