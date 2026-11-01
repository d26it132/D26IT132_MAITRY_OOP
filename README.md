# D26IT132_MAITRY_OOP

# MiniBank – Object-Oriented Programming Project

## Project Description
MiniBank is a console-based banking application developed using Java as part of the Object-Oriented Programming (OOP) practicals. The project demonstrates important OOP concepts and Java features through a simple banking system.

## Objectives
- Understand and implement Object-Oriented Programming concepts.
- Use classes, objects, inheritance, polymorphism, and abstraction.
- Implement interfaces, enums, annotations, and exception handling.
- Apply collections, generics, and Java I/O where required.
- Demonstrate multithreading and file handling.
- Organize code using packages and follow modular programming practices.

## Features
- Menu-driven banking application.
- Account and customer management.
- Support for different account types, such as Savings, Current, and Fixed Deposit.
- Deposit, withdrawal, and fund transfer operations.
- Input validation using regular expressions.
- Custom exception handling.
- Transaction logging and account statement generation.
- File-based account data persistence.
- Reflection-based validation using custom annotations.

*Note: Features may vary depending on the practical completed.*

## Technologies Used
- **Programming Language:** Java
- **JDK:** Java 23
- **IDE:** Visual Studio Code / IntelliJ IDEA
- **Version Control:** Git and GitHub

## Project Structure

```text
MiniBank/
├── src/
│   ├── model/
│   │   └── annotation/
│   ├── exception/
│   ├── util/
│   ├── service/
│   └── MiniBank.java
├── data/
├── logs/
├── reports/
├── out/
└── README.md
```

## How to Compile and Run

### 1. Open the project directory
Open a terminal in the MiniBank project folder.

### 2. Compile the Java files

```bash
javac -d out src/model/annotation/*.java src/exception/*.java src/model/*.java src/util/*.java src/service/*.java src/MiniBank.java
```

### 3. Run the application

```bash
java -cp out MiniBank
```

**Note:** Run these commands from the project root directory. The commands assume that each listed source folder contains Java files and that the main class is named `MiniBank`.

## Learning Outcomes
- Gained practical knowledge of OOP principles in Java.
- Learned to create and organize packages.
- Implemented custom exceptions and input validation.
- Understood interfaces, abstract classes, and polymorphism.
- Practiced file handling, persistence, and transaction logging.
- Learned to manage source code using Git and GitHub.

## Author
**Name:** Maitry Rana J.  
**Roll Number:** D26IT132 
**Course:** B.Tech – Semester 3  
**Subject:** Object-Oriented Programming(CEUE203)

## Conclusion
The MiniBank project provides practical experience in developing a modular Java application while applying core OOP concepts and advanced Java features.
