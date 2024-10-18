# JavaFX DB Application with GUI

This project is a JavaFX application that connects to a MySQL database to manage users, allowing you to perform CRUD operations (Create, Read, Update, Delete). It includes a splash screen, followed by a GUI with a `TableView` to display user details such as name, email, phone, and address.

## Features

- Splash Screen: Displays a splash screen when the application starts, followed by a smooth transition into the main GUI.
- CRUD Operations: Manage users in the database through the GUI:
  - Add new users.
  - Edit existing user details.
  - Delete users.
  - View a list of all users.
- Database Connectivity: The app connects to a MySQL database using JDBC.
- JavaFX TableView: The main GUI uses a `TableView` to display users fetched from the database.

## Requirements

- Java 8 or later
- JavaFX SDK (if not bundled with the JDK)
- MySQL Database (or another compatible SQL database)
- Maven or Gradle for dependency management

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── org/example/javafxdb_sql_shellcode/
│   │       ├── App.java             # Main entry point for the JavaFX application
│   │       ├── DB_GUI_Controller.java # Controller for the GUI managing database operations
│   │       ├── ConnDbOps.java       # Class to manage database operations (CRUD)
│   │       └── Person.java          # Model class for User (Person)
│   ├── resources/
│   │   └── org/example/javafxdb_sql_shellcode/
│   │       ├── db_interface_gui.fxml # FXML file for the main GUI layout
│   │       ├── SplashScreen.fxml    # FXML file for the splash screen layout
│   │       ├── style.css            # Stylesheet for the application
```

## Setup

### 1. Database Setup

1. Create a MySQL Database and set up a `users` table with the following SQL:

```
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255)
);
```

2. Update the database connection settings in `ConnDbOps.java` to match your database configuration:

```
private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

### 2. Run the Application

- Using an IDE: Import the project into your preferred IDE (e.g., IntelliJ IDEA or Eclipse), and run the `App.java` class.
- Using Maven: If you're using Maven, you can run the application by executing the following command:


mvn javafx:run

## How to Use

### GUI Menu

Once the application loads, you can interact with the GUI to manage users:

- Add User: Fill in the text fields and click "Add User" to insert a new user into the database.
- Edit User: Select a user from the `TableView`, modify the fields, and click "Edit User" to update the user's information.
- Delete User: Select a user from the `TableView` and click "Delete User" to remove them from the database.
- Refresh: Click "Refresh" to reload the list of users from the database.

### Console Menu

You can also manage users through the console. Run the application and choose one of the following options:

============== Menu ==============
| To start GUI,           press 'g' |
| To connect to DB,       press 'c' |
| To display all users,   press 'a' |
| To insert to the DB,    press 'i' |
| To edit a user,         press 'e' |
| To delete a user,       press 'd' |
| To query by name,       press 'q' |
| To exit,                press 'x' |
===================================


## Key Classes

- App.java: The main class responsible for launching the application and managing scene transitions.
- DB_GUI_Controller.java: The controller for the GUI that handles CRUD operations and updates the `TableView`.
- ConnDbOps.java: This class handles all database operations (inserting, updating, deleting, querying users).
- Person.java: A model class representing a user in the application (ID, name, email, phone, address).

## Troubleshooting

### Common Issues

- NullPointerException when loading CSS or FXML**: Ensure that the paths to resources (CSS or FXML files) are correct and that the files are placed in the `resources` folder. Double-check the paths when calling `getClass().getResource()` to make sure they start with `/` for classpath-relative paths.

- Database Connectivity Issues**: Ensure that MySQL is running, and that the JDBC URL, username, and password in `ConnDbOps.java` are correct. Test your database connection separately if needed.

- TableView not showing all columns**: Ensure that the `Person` class has the correct getters and that the `TableColumn` bindings in the FXML and controller are properly set using `PropertyValueFactory`.


