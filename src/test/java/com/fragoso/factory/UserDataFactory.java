package com.fragoso.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fragoso.pojo.User;

import java.io.FileInputStream;
import java.io.IOException;

public class UserDataFactory {
    public static User createUser() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(new FileInputStream("src/test/resources/bodies/postUser.json"), User.class);
        return user;
    }

    public static User createUserRandom(String name, String job) throws IOException {
        User userRandom = createUser();
        userRandom.setName(name);
        userRandom.setJob(job);
        return userRandom;
    }

    public static User createUserEmptyName(String name, String job) throws IOException{
        User userEmptyName = createUser();
        userEmptyName.setName("");
        userEmptyName.setJob(job);
        return userEmptyName;
    }

    public static User createUserEmptyJob(String name, String job) throws IOException{
        User userEmptyName = createUser();
        userEmptyName.setName(name);
        userEmptyName.setJob("");
        return userEmptyName;
    }

    public static User updateUser() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(new FileInputStream("src/test/resources/bodies/putUser.json"), User.class);
        return user;
    }

}
