package com.example.javafxtutorial.model;


import java.io.Serializable;
import java.util.ArrayList;

public class Role implements Serializable {
    private String roleName;

    private ArrayList<String> permissions;

    public Role(String roleName){
        this.roleName = roleName;
    }

    public Role(String roleName, ArrayList<String> permissions) {
        this.roleName = roleName;
        this.permissions = permissions;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public ArrayList<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(ArrayList<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + roleName + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}

