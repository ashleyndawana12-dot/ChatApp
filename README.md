QuickChat Application

Description
QuickChat is a Java-based messaging application developed using Maven. It allows users to register, log in, enter messages, and choose how to handle each message.

 Features
- User registration and login authentication
- Username, password, and cellphone number validation
- Main menu for sending messages, displaying recently sent messages, and quitting the application
- Message IDs generated using a ten-digit numeric format
- Message hashes generated using the message ID, message number, and message text
- Message length validation with a maximum of 250 characters
- Recipient cellphone number validation using the South African +27 format
- Display of successfully sent message details
- Functionality to store messages in a JSON file

 Technologies Used
- Java
- Apache Maven
- JUnit 5
- GitHub

 Testing
The project includes unit tests for the login and message functionality.

The test suite was run using Maven in NetBeans, with the following results:

- LoginTest: 11 tests passed
- MessageTest: 10 tests passed
- Total: 21 tests passed
- Failures: 0
- Errors: 0
- Skipped: 0
  
Running the Project
Open the project in Apache NetBeans and run the `ChatApp` main class. The project can also be built and tested using Maven.

Repository
This project is maintained in GitHub on the `KhanbanTasks` branch.
