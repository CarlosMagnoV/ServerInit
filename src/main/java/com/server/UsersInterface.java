package com.server;

import java.util.Collection;

public interface UsersInterface {

    String signUp(String username, String password);

    String login(String username, String password);

    Collection<Users> getAllUsers();
}
