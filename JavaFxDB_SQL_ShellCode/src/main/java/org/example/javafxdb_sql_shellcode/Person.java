package org.example.javafxdb_sql_shellcode;
/**
 * @Title: Person class
 * The Person class represents a user in the database.
 * It includes fields for ID, first name, last name, department, major,
 * address, email, and phone, with associated getters and setters.
 *
 * This class serves as a data model to be used in JavaFX `TableView`
 * and for CRUD operations.
 * @author cody bandrowski
 */
public class Person {


    private Integer id;
    private String firstName;
    private String lastName;
    private String dept;
    private String major;
    private String address = "";
    private String email= "";
    private String phone = "";

    public Person() {
    }


    public Person(Integer id, String firstName, String lastName, String dept, String major) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.dept = dept;


    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }


    public String getDept() {
        return dept;
    }

    public String  setAddress(String input ) { address = input; return address; }
    public String getAddress() { return address; }

    public String  setEmail(String email) { this.email = email; return email; }
    public String getEmail() { return email; }
    public String  setPhone(String phone) { this.phone = phone; return phone; }
    public String getPhone() { return phone; }

    public void setDept(String dept) {
        this.dept = dept;
    }
    @Override
    public String toString() {
        return "ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName +
                ", Department: " + dept + ", Major: " + major;
    }


    public static class DB_GUI_Controller {
    }
}
