package com.fragoso.factory;

import com.fragoso.pojo.Login;

public class LoginDataFactory {
    public static Login identity(String email, String password){
        Login access = new Login();
        access.setEmail(email);
        access.setPassword(password);
        return access;
    }
}
