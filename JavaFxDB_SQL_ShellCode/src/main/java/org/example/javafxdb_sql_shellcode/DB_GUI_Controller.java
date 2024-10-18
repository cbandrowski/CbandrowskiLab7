package org.example.javafxdb_sql_shellcode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.javafxdb_sql_shellcode.db.ConnDbOps;
/**
 * @Title: DB_GUI_Controller
 * Controller class for the database GUI.
 *
 * This class is responsible for managing the CRUD operations in the graphical user interface (GUI).
 * It binds the `TableView` to the data model and provides methods to add, edit, delete, and refresh
 * the list of users in the database.
 * @author cody bandrowski
 */
public class DB_GUI_Controller {

    @FXML
    private TableView<Person> userTable;
    @FXML
    private TableColumn<Person, Integer> colId;
    @FXML
    private TableColumn<Person, String> colName;
    @FXML
    private TableColumn<Person, String> colEmail;
    @FXML
    private TableColumn<Person, String> colPhone;
    @FXML
    private TableColumn<Person, String> colAddress;

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;

    private ConnDbOps dbOps = new ConnDbOps();
    private ObservableList<Person> userData;

    /**
     * Initializes the TableView with column bindings and loads the user data.
     * This method is called automatically when the GUI is initialized.
     */

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        refreshUserTable();  // Load the users when the GUI starts
    }
    /**
     * Handles the action of adding a new user.
     * This method retrieves the input from the text fields, inserts a new user
     * into the database, and refreshes the table view.
     */

    public void handleAddUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        dbOps.insertUser(name, email, phone, address, "defaultpassword");  // Assuming default password for simplicity
        refreshUserTable();
    }
    /**
     * Handles the action of editing the selected user.
     * This method retrieves the selected user from the TableView, updates their information
     * based on the input fields, and saves the changes to the database.
     */
    public void handleEditUser() {
        Person selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            selectedUser.setFirstName(nameField.getText());
            selectedUser.setEmail(emailField.getText());
            selectedUser.setPhone(phoneField.getText());
            selectedUser.setAddress(addressField.getText());

            dbOps.updateUser(selectedUser);
            refreshUserTable();
        }
    }
    /**
     * Handles the action of deleting the selected user.
     * This method retrieves the selected user from the TableView and deletes them from the database.
     */
    public void handleDeleteUser() {
        Person selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            dbOps.deleteUserById(selectedUser.getId());
            refreshUserTable();
        }
    }
    /**
     * Handles the action of refreshing the TableView.
     * This method reloads the user data from the database and updates the TableView.
     */
    public void handleRefresh() {
        refreshUserTable();
    }
    /**
     * Refreshes the TableView by reloading the user data from the database.
     * This method fetches the list of users from the database and updates the TableView.
     */
    private void refreshUserTable() {
        userData = FXCollections.observableArrayList(dbOps.listAllUsers());
        userTable.setItems(userData);
    }
}
