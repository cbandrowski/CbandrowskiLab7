/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.javafxdb_sql_shellcode.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.javafxdb_sql_shellcode.Person;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author MoaathAlrajab
 */
public class ConnDbOps {
    final String MYSQL_SERVER_URL = "jdbc:mysql://bandrowskicsc311server.mysql.database.azure.com";
    final String DB_URL = "jdbc:mysql://bandrowskicsc311server.mysql.database.azure.com/DBname"; // Replace DBname with your actual database name
    final String USERNAME = "bandrowskiadmin"; // Use your admin login with @servername
    final String PASSWORD = "Farmingdale24";       // Replace with your actual password



    public  boolean connectToDatabase() {
        boolean hasRegistredUsers = false;


        //Class.forName("com.mysql.jdbc.Driver");
        try {
            //First, connect to MYSQL server and create the database if not created
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS DBname");
            statement.close();
            conn.close();

            //Second, connect to the database and create the table "users" if cot created
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT( 10 ) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL UNIQUE,"
                    + "phone VARCHAR(200),"
                    + "address VARCHAR(200),"
                    + "password VARCHAR(200) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);

            //check if we have users in the table users
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");

            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasRegistredUsers = true;
                }
            }

            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasRegistredUsers;
    }


public Person queryUserByName(String name) {
    Person person = null;

    try {
        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        String sql = "SELECT * FROM users WHERE name LIKE ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
                String[] nameParts = name.split("_");
                String firstName = nameParts[0];  // First part before underscore
                String lastName = nameParts[1];   //
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phone + ", Address: " + address);
                String dept = "dept";
                String major = "major";
                person = new Person(id,firstName,lastName,dept,major );
}

        preparedStatement.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return person;
}

        // Method to fetch all users from the database
        public ObservableList<Person> getAllUsers() {
            ObservableList<Person> userList = FXCollections.observableArrayList();
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String sql = "SELECT * FROM users";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String dept = resultSet.getString("dept");
                    String major = resultSet.getString("major");

                    Person person = new Person(id, firstName, lastName, dept, major);
                    userList.add(person);
                }

                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return userList;
        }

        // Method to add a new user
        public void addUser(Person person) {
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String sql = "INSERT INTO users (first_name, last_name, dept, major) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, person.getFirstName());
                preparedStatement.setString(2, person.getLastName());
                preparedStatement.setString(3, person.getDept());
                preparedStatement.setString(4, person.getMajor());

                preparedStatement.executeUpdate();
                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Method to update an existing user
        public void updateUser(Person person) {
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String sql = "UPDATE users SET name = ?, email = ?, phone = ?, address = ? WHERE id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);;
                preparedStatement.setString(1, person.getFirstName()+"_"+person.getLastName());
                preparedStatement.setString(2, person.getEmail());
                preparedStatement.setString(3, person.getPhone());
                preparedStatement.setString(4, person.getAddress());
                preparedStatement.setInt(5, person.getId());

                preparedStatement.executeUpdate();
                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Method to delete a user by ID
        public void deleteUserById(int id) {
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String sql = "DELETE FROM users WHERE id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();
                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



//    public  void listAllUsers() {
//
//
//
//        try {
//            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//            String sql = "SELECT * FROM users ";
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                String email = resultSet.getString("email");
//                String phone = resultSet.getString("phone");
//                String address = resultSet.getString("address");
//                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phone + ", Address: " + address);
//            }
//
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
public List<Person> listAllUsers() {
    List<Person> users = new ArrayList<>();  // Create a list to store the users

    try {
        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        String sql = "SELECT * FROM users";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            // Retrieve user details from the result set
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String phone = resultSet.getString("phone");
            String address = resultSet.getString("address");

            // Create a new Person object and add it to the list
            Person person = new Person(id, name, email, phone, address);
            users.add(person);
        }

        preparedStatement.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Return the list of users
    return users;
}

    public  void insertUser(String name, String email, String phone, String address, String password) {


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO users (name, email, phone, address, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, password);

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("A new user was inserted successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
