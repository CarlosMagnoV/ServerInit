package com.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.ResultSet;
import java.util.Collection;

@org.springframework.stereotype.Service
public class UsersService {

    @Autowired
    @Qualifier("mysql")
    private UsersInterface usersInterface;

    public String signUp(String username, String password) {return this.usersInterface.signUp(username,password);}

    public String updateList(String username) {return this.usersInterface.updateList(username);}

    public String sendLocations(String username) {return this.usersInterface.sendLocations(username);}

    public String sendKeywords(String username) {return this.usersInterface.sendKeywords(username);}

    public String updateListPosts(String username) {return this.usersInterface.updateListPosts(username);}

    public String updateDecentralizedListPosts(String username) {return this.usersInterface.updateDecentralizedListPosts(username);}

    public String updateListLocation(String username) {return this.usersInterface.updateListLocation(username);}

    public String deleteKey(String username,String keyword) {return this.usersInterface.deleteKeyword(username,keyword);}

    public String deletePost(String title,String location,String keyword,String content,String action,String username){
        return this.usersInterface.deletePost(title,location,keyword,content,action,username);
    }

    public String keywordsAnalyse(String username, String keyword, String action,String content) {return this.usersInterface.keywordsAnalyse(username,keyword,action,content);}

    public String deleteLocation(String username,String nameLocation,String lat, String lon,String radius) {return this.usersInterface.deleteLocation(username,nameLocation,lat,lon,radius);}

    public String login(String username, String password){return this.usersInterface.login(username,password);}

    public String location(String lat, String lon, String username){return this.usersInterface.location(lat,lon,username);}

    public String locationD(String lat, String lon, String username){return this.usersInterface.locationD(lat,lon,username);}

    public String keywords(String username, String keyword){return this.usersInterface.keywords(username,keyword);}

    public String locationInsert(String lat, String lon,String name,String radius,String username){return this.usersInterface.locationInsert(lat,lon,name,radius,username);}

    public String post(String title, String location,String key,String content,String action,String username){return this.usersInterface.post(title,location,key,content,action,username);}

    public String decentralizedPost(String title, String location,String key,String content,String action,String username){return this.usersInterface.decentralizedPost(title,location,key,content,action,username);}

    public String decentralizedDeletePost(String title,String location,String keyword,String content,String action,String username) {return this.usersInterface.decentralizedDeletePost(title, location, keyword, content, action, username);}
}
