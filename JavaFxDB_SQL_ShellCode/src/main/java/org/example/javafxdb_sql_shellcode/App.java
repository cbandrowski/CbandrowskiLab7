package org.example.javafxdb_sql_shellcode;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.javafxdb_sql_shellcode.db.ConnDbOps;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
/**
 *
 * @Title App
 * The App class serves as the main entry point for the JavaFX application.
 * It includes methods to display the splash screen, handle scene transitions,
 * and provide a console-based menu to interact with the database via a GUI.
 *
 * This application demonstrates CRUD operations (Create, Read, Update, Delete) on a
 * user database using JavaFX for the GUI and MySQL for database management.
 * @author Cody Bandrowski
 */

public class App extends Application {

    private static Scene scene;
    private static ConnDbOps cdbop;
    private static Stage primaryStage;  // Store reference to the primary stage

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        showSplashScreen();  // Show the splash screen when GUI starts
    }
    // Method to switch the root of the current scene
    public static void setRoot(String fxml) throws IOException {
        Parent root = loadFXML(fxml);  // Load the new FXML file
        primaryStage.getScene().setRoot(root);  // Set the new root in the current scene
    }


    // Method to load the FXML files
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // Method to show splash screen
    private void showSplashScreen() {
        try {
            Parent splashRoot = FXMLLoader.load(getClass().getResource("/org/example/javafxdb_sql_shellcode/SplashScreen.fxml"));
            Scene splashScene = new Scene(splashRoot, 850, 560);
            splashScene.getStylesheets().add(getClass().getResource("/org/example/javafxdb_sql_shellcode/styless.css").toExternalForm());
            primaryStage.setScene(splashScene);
            primaryStage.show();

            // Transition to main application after splash screen
            transitionToMainApp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle the fade-out and transition to the main application
    private void transitionToMainApp() {
        try {
            Parent mainRoot = FXMLLoader.load(getClass().getResource("db_interface_gui.fxml"));

            Scene currentScene = primaryStage.getScene();
            Parent currentRoot = currentScene.getRoot();

            // Create a fade-out effect for the splash screen
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), currentRoot);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {
                Scene newScene = new Scene(mainRoot, 850, 560);
                newScene.getStylesheets().add(getClass().getResource("/org/example/javafxdb_sql_shellcode/styless.css").toExternalForm());
                primaryStage.setScene(newScene);  // Load the main scene after fade-out
            });

            fadeOut.play();  // Play the fade-out transition
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        cdbop = new ConnDbOps();
        Scanner scan = new Scanner(System.in);

        char input;
        do {
            System.out.println(" ");
            System.out.println("============== Menu ==============");
            System.out.println("| To start GUI,           press 'g' |");
            System.out.println("| To connect to DB,       press 'c' |");
            System.out.println("| To display all users,   press 'a' |");
            System.out.println("| To insert to the DB,    press 'i' |");
            System.out.println("| To edit a user,         press 'e' |");
            System.out.println("| To delete a user,       press 'd' |");
            System.out.println("| To query by name,       press 'q' |");
            System.out.println("| To exit,                press 'x' |");
            System.out.println("===================================");
            System.out.print("Enter your choice: ");
            input = scan.next().charAt(0);

            switch (input) {
                case 'g':
                    launch(args); // Launch the JavaFX GUI
                    break;

                case 'c':
                    cdbop.connectToDatabase();  // Connect to the database

                    break;

                case 'a':
                    cdbop.listAllUsers();  // Display all users
                    break;

                case 'i':
                    System.out.print("Enter Name: ");
                    String name = scan.next();
                    System.out.print("Enter Email: ");
                    String email = scan.next();
                    System.out.print("Enter Phone: ");
                    String phone = scan.next();
                    System.out.print("Enter Address: ");
                    String address = scan.next();
                    System.out.print("Enter Password: ");
                    String password = scan.next();
                    cdbop.insertUser(name, email, phone, address, password);  // Insert user into DB
                    break;

                case 'e':
                    System.out.print("Enter the name of the user to edit: ");
                    String editName = scan.next();
                    Person personToEdit = cdbop.queryUserByName(editName);  // Query user by name

                    if (personToEdit != null) {
                        System.out.println("Current user information: " + personToEdit);

                        System.out.print("Enter new first name (leave blank to keep current): ");
                        String newFirstName = scan.next();
                        if (!newFirstName.isEmpty()) personToEdit.setFirstName(newFirstName);

                        System.out.print("Enter new last name (leave blank to keep current): ");
                        String newLastName = scan.next();
                        if (!newLastName.isEmpty()) personToEdit.setLastName(newLastName);

                        System.out.print("Enter new department (leave blank to keep current): ");
                        String newDept = scan.next();
                        if (!newDept.isEmpty()) personToEdit.setDept(newDept);

                        System.out.print("Enter new major (leave blank to keep current): ");
                        String newMajor = scan.next();
                        if (!newMajor.isEmpty()) personToEdit.setMajor(newMajor);

                        System.out.print("Enter new email (leave blank to keep current): ");
                        String newEmail = scan.next();
                        if (!newEmail.isEmpty()) personToEdit.setEmail(newEmail);

                        System.out.print("Enter new Phone # (leave blank to keep current): ");
                        String newPhone = scan.next();
                        if (!newPhone.isEmpty()) personToEdit.setPhone(newPhone);

                        System.out.print("Enter new address (leave blank to keep current): ");
                        String newAddress = scan.next();
                        if (!newAddress.isEmpty()) personToEdit.setAddress(newAddress);

                        // Call update method
                        cdbop.updateUser(personToEdit);
                        System.out.println("User information updated successfully.");
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case 'd':  // Deleting a user
                    System.out.print("Enter the name of the user to delete: ");
                    String deleteName = scan.next();
                    Person personToDelete = cdbop.queryUserByName(deleteName);

                    if (personToDelete != null) {
                        System.out.println("User to be deleted: " + personToDelete);
                        System.out.print("Are you sure you want to delete this user? (yes/no): ");
                        String confirmation = scan.next();

                        if (confirmation.equalsIgnoreCase("yes")) {
                            cdbop.deleteUserById(personToDelete.getId());  // Delete user by ID
                            System.out.println("User deleted successfully.");
                        } else {
                            System.out.println("Deletion canceled.");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case 'q':
                    System.out.print("Enter the name to query: ");
                    String queryName = scan.next();
                    cdbop.queryUserByName(queryName);  // Query user by name
                    break;

                case 'x':
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println(" ");
        } while (input != 'x');

        scan.close();  // Close the scanner when exiting
    }
}
