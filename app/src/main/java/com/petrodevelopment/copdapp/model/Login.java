package com.petrodevelopment.copdapp.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created on 23/05/2015.
 */
public class Login extends ArrayList {

    public String email;
    public String password;
    public int passNumber;


    public Login(String email, String password, int passNumber) {

        this.email = email;
        this.password = password;
        this.passNumber = passNumber;
    }

    public Login() {
    }

    public static List<Login> getLogin() {
        List<Login> logins = new ArrayList<>();
        logins.add(new Login("tomwhite1209@gmail.com", "password", 1234));
        return logins;
    }

    public void addLogin(List<Login> logins)
    {
        logins.add(new Login(email, password, passNumber));
    }
}


