package com.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@org.springframework.stereotype.Service
public class UsersService {

    @Autowired
    @Qualifier("mysql")
    private UsersInterface usersInterface;

    public Collection<Users> getAllUsers(){
        return this.usersInterface.getAllUsers();
    }

    public String signUp(String username, String password) {return this.usersInterface.signUp(username,password);}

    public String login(String username, String password){return this.usersInterface.login(username,password);}
}
