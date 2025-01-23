package com.example.javafxtutorial.controller;

import com.example.javafxtutorial.model.Role;

import java.io.*;
import java.util.ArrayList;

public class RoleController {

    private Role[] roles;

    private final String filePath = "src/main/resources/com/example/javafxtutorial/database/roles.dat";

    public RoleController(){
        roles = new Role[3];
        readRolesFromFile();
    }

    public void readRolesFromFile() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            roles = (Role[]) ois.readObject();
            System.out.println("Roles read from file: " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeRolesToFile() {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/com/example/javafxtutorial/database/roles.dat"))) {
            oos.writeObject(roles);
            System.out.println("Roles written to file: " + "src/main/resources/com/example/javafxtutorial/database/roles.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeRolesToFile(Role[] roles) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/com/example/javafxtutorial/database/roles.dat"))) {
            oos.writeObject(roles);
            System.out.println("Roles written to file: " + "src/main/resources/com/example/javafxtutorial/database/roles.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public void printRoles(){
        for(Role r: roles){
            System.out.println(r.toString());
        }
    }

    //testing
    public static void main(String[] args) {
        RoleController roleController = new RoleController();

//        roleController.printRoles();

        ArrayList<String> librarianPermission = new ArrayList<>();
        librarianPermission.add("Home");
        librarianPermission.add("Cart");

        ArrayList<String> managerPermissions = new ArrayList<>();
        managerPermissions.add("Add Book");
        managerPermissions.add("Edit Books");
        managerPermissions.add("Check Performance");
        managerPermissions.add("Statistics");

        ArrayList<String> administratorPermissions = new ArrayList<>();
        administratorPermissions.add("All");

        Role[] roles = new Role[3];
        roles[0] = new Role("Librarian", librarianPermission);
        roles[1] = new Role("Manager", managerPermissions);
        roles[2] = new Role("Administrator", administratorPermissions);

        roleController.writeRolesToFile(roles);
    }

}
