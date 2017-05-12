package com.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class Controller {

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Users> getAllUsers(){
        return usersService.getAllUsers();
    }

    @RequestMapping("/login")
    public String login(@RequestParam(value="username") String username, @RequestParam(value="password") String password)
    {
        return usersService.login(username,password);
    }

    @RequestMapping("/signUp")
    public String signUp(@RequestParam(value="username") String username, @RequestParam(value="password") String password)
    {
        return usersService.signUp(username,password);
    }

}


