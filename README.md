 QuickChat Application

Description
QuickChat is a Java-based messaging application developed using Maven. It allows users to register, log in, send messages, and manage messages through different options.

Features
- User registration and login authentication.
- Username, password, and cellphone number validation.
- Main menu for sending messages, displaying recently sent messages, accessing stored messages, and quitting the application.
- Message IDs generated using a ten-digit numeric format.
- Message hashes generated using the message ID, message number, and message text.
- Message length validation with a maximum of 250 characters.
- Recipient cellphone number validation using the South African +27 format.
- Display of successfully sent message details.
- Options to send, disregard, or store messages.
- Messages stored in a JSON file.
- Stored Messages menu with options to:
  - Display senders and recipients.
  - Display the longest message.
  - Search for a message by ID.
  - Search for messages by recipient.
  - Delete a message using its hash.
  - Display a full message report.

Technologies Used
- Java
- Apache Maven
- JUnit 5
- GitHub

Testing
The project includes unit tests for the login and message functionality.

The test suite was run using Maven in NetBeans, with the following results:

- LoginTest: 11 tests passed.
- MessageTest: 10 tests passed.
- Total: 21 tests passed.
- Failures: 0
- Errors: 0
- Skipped: 0

 Running the Project
Open the project in Apache NetBeans and run the `ChatApp` main class. The project can also be built and tested using Maven.

 Repository
This project is maintained on GitHub in the `KhanbanTasks` branch.

Usage Guide

1. Run the `ChatApp` main class.
2. Register by entering your first name, last name, username, password, and cellphone number.
3. Log in using your registered username and password.
4. Select the messaging options from the main menu.
5. Enter the number of messages you want to process.
6. Choose whether to send, disregard, or store each message.
7. Access the Stored Messages menu to search, display, or delete stored messages.
8. Select Quit when you are finished.
