# QuickChat Application

## 1. Project Overview

QuickChat is a Java console-based messaging application developed as part of an academic programming project. It allows users to register an account, log in, and manage messages through a simple menu system.

The application validates user registration details, generates unique-format message IDs, creates message hashes, validates recipient cellphone numbers, and allows users to send, disregard, or store messages for later.

The project demonstrates fundamental Java programming concepts, object-oriented programming, input validation, file handling, and unit testing.

## 2. Features

### User Registration and Login
- Register a username containing exactly five characters and an underscore.
- Validate passwords using uppercase letters, lowercase letters, numbers, special characters, and a minimum length of eight characters.
- Validate South African cellphone numbers using the `+27` international format followed by nine digits.
- Log in using the registered username and password.

### Message Management
- Enter a specified number of messages.
- Validate recipient cellphone numbers using an international dialing code.
- Ensure messages are not empty and do not exceed 250 characters.
- Generate a ten-digit message ID for each message.
- Generate a message hash using the message ID prefix, message number, and the first and last words of the message.
- Choose to send a message, disregard it, or store it for later.
- Display sent message details and calculate the total number of messages sent.

### File Handling
- Store messages selected for later in a `messages.json` file.
- Save message IDs, message hashes, recipient numbers, and message text in JSON format.
- Handle file-writing errors using exception handling.

### Unit Testing
- Use JUnit 5 to test message length validation.
- Test recipient cellphone number validation.
- Test message ID generation and validation.
- Test message hash generation.
- Test sending, disregarding, and storing messages.
- Verify the total number of sent messages.

## 3. Technologies Used

- Java
- Apache Maven
- JUnit 5
- Java Standard Library
- Regular expressions
- JSON file handling
- NetBeans or another Java IDE

## 4. Requirements

To run the application, you need:

- A compatible Java Development Kit (JDK).
- Apache Maven or an IDE with Maven support.
- A Java development environment such as NetBeans, IntelliJ IDEA, or Eclipse.

The project is configured to use Java release 25 in its Maven configuration. Use a JDK compatible with this setting, or adjust the configuration to match your installed JDK.

## 5. Project Structure

```text
Main/
├── pom.xml
├── README.md
├── messages.json
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── mycompany/
    │               └── main/
    │                   ├── Main.java
    │                   └── Message.java
    └── test/
        └── java/
            └── com/
                └── mycompany/
                    └── main/
                        └── MessageTest.java
```

Note: The `messages.json` file is created when a message is stored. It does not need to exist before running the application.

## 6. How to Run the Application

1. Clone or download the repository from GitHub.
2. Open the project in a Java IDE or terminal.
3. Ensure the required source files are in the correct package.
4. Check that the installed JDK is compatible with the Maven configuration.
5. Build the project using Maven:

   ```bash
   mvn clean package
   ```

6. Run the application using Maven:

   ```bash
   mvn exec:java
   ```

7. Follow the console instructions to register, log in, and access the QuickChat menu.

## 7. How to Use the Application

1. Enter a valid username.
2. Create a password that meets the complexity requirements.
3. Enter your cellphone number in the required `+27` format.
4. Log in using your registered details.
5. Choose how many messages you want to enter.
6. Select an option from the QuickChat menu.
7. Enter a recipient number and a message.
8. Choose whether to send, disregard, or store the message.
9. Review the message details displayed in the console.
10. Select Quit when you want to exit the application.

## 8. Running the Unit Tests

The project uses JUnit Jupiter 5 for automated testing.

To execute the tests, run:

```bash
mvn test
```

The `MessageTest.java` class contains tests for message validation, hash generation, message IDs, message management options, and message totals.

## 9. Current Limitations

- User registration details are stored in memory and are not permanently saved.
- The application requires users to register again when starting a new session.
- The recently sent messages menu option currently displays "Coming Soon."
- Stored messages are written to `messages.json`, but the application does not yet load previously stored messages automatically.
- Disregarded messages are not added to the sent messages list.
- The application operates through a console rather than a graphical user interface.

## 10. Possible Future Improvements

- Implement the recently sent messages feature.
- Add permanent user registration and login storage.
- Load previously stored messages when the application starts.
- Improve message retrieval and message history functionality.
- Strengthen validation and error handling.
- Develop a graphical user interface.

## 11. Author

**Student Number:** ST10542449

## 12. License

This project was developed for educational purposes.
