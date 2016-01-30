package com.crossover.application;

import com.crossover.model.User;

/**
 * User: anlcan
 * Date: 30/01/16
 * Time: 19:43
 */
public class UserManager {
    private static UserManager ourInstance = new UserManager();

    public static UserManager getInstance() {
        return ourInstance;
    }

    private UserManager() {
    }

    public static User checkCredentials(String email, String passwor){
        return new User();
    }
}
