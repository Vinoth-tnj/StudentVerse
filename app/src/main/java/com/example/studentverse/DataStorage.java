package com.example.studentverse;

public class DataStorage {

    String username,mail,password,confirmpassword;

    public DataStorage() {
    }

    public DataStorage(String name, String mail, String password, String confirmpassword) {
        this.username = name;
        this.mail = mail;
        this.password = password;
        this.confirmpassword = confirmpassword;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}
