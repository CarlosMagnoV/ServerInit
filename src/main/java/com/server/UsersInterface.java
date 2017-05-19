package com.server;

import java.sql.ResultSet;
import java.util.Collection;

public interface UsersInterface {

    String signUp(String username, String password);

    String login(String username, String password);

    String updateList(String username);

    String sendLocations(String username);

    String sendKeywords(String username);

    String updateListLocation(String username);

    String updateListPosts(String username);

    String deleteKeyword(String username,String keyword);

    String deletePost(String title,String location,String keyword,String content,String action,String username);

    String location(String lat, String lon,String username);

    String keywords(String username, String keyword);

    String deleteLocation(String username,String nameLocation,String lat, String lon,String radius);

    String locationInsert(String lat,String lon,String name,String radius,String username);

    String decentralizedPost(String title, String location,String key,String content,String action,String username);

    String decentralizedDeletePost(String title,String location,String keyword,String content,String action,String username);

    String updateDecentralizedListPosts(String username);

    String keywordsAnalyse(String username, String keyword, String action, String content);

    String locationD(String lat, String lon, String username);

    String post(String title,String location,String key,String content,String action,String username);
}
