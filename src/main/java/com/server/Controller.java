package com.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/users")
public class Controller {

    @Autowired
    private UsersService usersService;

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

    @RequestMapping("/updateList")
    public String updateList(@RequestParam(value="username") String username)
    {
        return usersService.updateList(username);
    }

    @RequestMapping("/updateListLocation")
    public String updateListLocation(@RequestParam(value="username") String username)
    {
        return usersService.updateListLocation(username);
    }

    @RequestMapping("/updateListPosts")
    public String updateListPosts(@RequestParam(value="username") String username)
    {
        return usersService.updateListPosts(username);
    }

    @RequestMapping("/deleteKey")
    public String deleteKey(@RequestParam(value="username") String username,@RequestParam(value="keyword") String keyword)
    {
        return usersService.deleteKey(username,keyword);
    }

    @RequestMapping("/deletePost")
    public String deletePost(@RequestParam(value="title") String title,@RequestParam(value="location") String location,
                             @RequestParam(value="keyword") String keyword,@RequestParam(value="content") String content,
                             @RequestParam(value="action") String action,@RequestParam(value="username") String username)
    {
        return usersService.deletePost(title,location,keyword,content,action,username);
    }

    @RequestMapping("/deleteLocation")
    public String deleteLocation(@RequestParam(value="username") String username,@RequestParam(value="nameLocation") String nameLocation,
                            @RequestParam(value="lat") String lat, @RequestParam(value="lon") String lon,
                            @RequestParam(value="radius") String radius)
    {
        return usersService.deleteLocation(username,nameLocation,lat,lon,radius);
    }

    @RequestMapping("/location")
    public String location(@RequestParam(value="lat") String lat, @RequestParam(value="lon") String lon,@RequestParam(value="username") String username)
    {
        return usersService.location(lat,lon,username);
    }

    @RequestMapping("/locationD")
    public String locationD(@RequestParam(value="lat") String lat, @RequestParam(value="lon") String lon,@RequestParam(value="username") String username)
    {
        return usersService.locationD(lat,lon,username);
    }

    @RequestMapping("/keywords")
    public String keywords(@RequestParam(value="username") String username, @RequestParam(value="keyword") String keyword)
    {
        return usersService.keywords(username,keyword);
    }

    @RequestMapping("/keywordsAnalyse")
    public String keywordsAnalyse(@RequestParam(value="username") String username, @RequestParam(value="keyword") String keyword,
                                  @RequestParam(value="action") String action,@RequestParam(value="content") String content)
    {
        return usersService.keywordsAnalyse(username,keyword,action,content);
    }

    @RequestMapping("/sendLocations")
    public String sendLocations(@RequestParam(value="username") String username)
    {
        return usersService.sendLocations(username);
    }

    @RequestMapping("/sendKeywords")
    public String sendKeywords(@RequestParam(value="username") String username)
    {
        return usersService.sendKeywords(username);
    }

    @RequestMapping("/locationInsert")
    public String locationInsert(@RequestParam(value="lat") String lat, @RequestParam(value="lon") String lon,
                           @RequestParam(value="name") String name,@RequestParam(value="radius") String radius,
                                 @RequestParam(value="username") String username)
    {
        return usersService.locationInsert(lat,lon,name,radius,username);
    }

    @RequestMapping("/post")
    public String post(@RequestParam(value="title") String title, @RequestParam(value="location") String location,
                       @RequestParam(value="key") String key,@RequestParam(value="content") String content,
                       @RequestParam(value="action") String action,@RequestParam(value="username") String username)
    {
        return usersService.post(title,location,key,content,action,username);
    }

    @RequestMapping("/decentralizedPost")
    public String decentralizedPost(@RequestParam(value="title") String title, @RequestParam(value="location") String location,
                       @RequestParam(value="key") String key,@RequestParam(value="content") String content,
                       @RequestParam(value="action") String action,@RequestParam(value="username") String username)
    {
        return usersService.decentralizedPost(title,location,key,content,action,username);
    }

    @RequestMapping("/decentralizedDeletePost")
    public String decentralizedDeletePost(@RequestParam(value="title") String title, @RequestParam(value="location") String location,
                                    @RequestParam(value="key") String key,@RequestParam(value="content") String content,
                                    @RequestParam(value="action") String action,@RequestParam(value="username") String username)
    {
        return usersService.decentralizedDeletePost(title,location,key,content,action,username);
    }

    @RequestMapping("/updateDecentralizedListPosts")
    public String updateDecentralizedListPosts(@RequestParam(value="username") String username)
    {
        return usersService.updateDecentralizedListPosts(username);
    }



}


