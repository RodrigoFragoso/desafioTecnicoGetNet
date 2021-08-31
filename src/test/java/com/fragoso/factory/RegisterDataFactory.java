package com.fragoso.factory;

import com.fragoso.pojo.Register;
import com.fragoso.pojo.User;

import java.io.IOException;

public class RegisterDataFactory {
    public static Register registerUser(String email, String password) throws IOException {
        Register register = new Register();
        register.setEmail(email);
        register.setPassword(password);
        return register;
    }
}
