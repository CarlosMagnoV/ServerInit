package com.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository("mysql")
public class MySqlOperations implements UsersInterface {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Lock lock = new ReentrantLock();

    @Override
    public String login(String username,String password){

        String userE="usernameN";
        String passE="passwordN";
        try {

            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");


            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username=\'"+username+"\'");

            while(rs.next()){
                userE=username;
            }
            rs = stmt.executeQuery("SELECT * FROM users WHERE password=\'"+password+"\'");

            while(rs.next()){
                passE=password;
            }
            lock.unlock();
            con.close();

        } catch(SQLException e) {

            System.err.println("SQL exception occurred" + e);
            System.err.println("login");
        }

        String info = userE+"&"+ passE;
        return info;
    }


    @Override
    public String signUp(String username, String password) {


        String userE=username;
        String passE=password;
        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");


            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users where username =\'"+username+"\'");

            while(rs.next()){
                return "usernameInUse";
            }
            rs=stmt.executeQuery("SELECT * FROM users");
            int id=0;
            while(rs.next())
                id=rs.getInt("id");
            id++;

            final String sql = "INSERT INTO users (id, username, password) VALUES ("+id+", ?, ?)";
            jdbcTemplate.update(sql, new Object[]{username, password});
            con.close();
            lock.unlock();

        } catch(SQLException e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("signUp");
        }

        return username;

    }

    @Override
    public String post(String title,String location,String key,String content,String action,String username){


        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");
            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery("SELECT * FROM posts");
            int id=0;
            while(rs.next())
                id=rs.getInt("id");
            id++;

            final String sql = "INSERT INTO posts (id, title, location, keyword, content, action, username) VALUES ("+id+",?,?,?,?,?,?)";
            jdbcTemplate.update(sql,new Object[]{title,location,key,content,action,username});
            con.close();
            lock.unlock();

        } catch(SQLException e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("post");
        }

        return username;
    }

    @Override
    public String decentralizedPost(String title,String location,String key,String content,String action,String username){


        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");

            lock.lock();
            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery("SELECT * FROM decentralizedPosts");
            int id=0;
            while(rs.next())
                id=rs.getInt("id");
            id++;

            final String sql = "INSERT INTO decentralizedPosts (id, title, location, keyword, content, action, username) VALUES ("+id+",?,?,?,?,?,?)";
            jdbcTemplate.update(sql,new Object[]{title,location,key,content,action,username});
            con.close();
            lock.unlock();

        } catch(SQLException e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("decentralizedPost");
        }

        return username;
    }

    @Override
    public String keywordsAnalyse(String username, String keyword, String action,String content){
        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest", "huckleberry", "Blueberry*77");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM keywords WHERE username =\'"+username+"\'");
            lock.unlock();

            while(rs.next()){
                if(rs.getString("keyword").equals(keyword)){
                    if(action.equals("whitelist")){
                        return content;
                    }else if (action.equals("blacklist")){
                        return "cannotReceive";
                    }else{
                        return content;
                    }
                }
            }
            con.close();

        }catch(Exception e){
            System.err.println("SQL exception occurred" + e);
            System.err.println("keywordsAnalyse");
        }
        return "cannotReceive";
    }


    @Override
    public String decentralizedDeletePost(String title,String location,String keyword,String content,String action,String username){


        try {
            lock.lock();
            final String sql = "DELETE FROM decentralizedPosts WHERE title = ? AND location = ? AND keyword = ? AND content = ? AND action = ? AND username = ?";
            jdbcTemplate.update(sql,new Object[]{title,location,keyword,content,action,username});

            lock.unlock();
        } catch(Exception e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("decentralizedDeletePost");
        }

        return location;
    }


    @Override
    public String deletePost(String title,String location,String keyword,String content,String action,String username){


        try {
            lock.lock();
            final String sql = "DELETE FROM posts WHERE title = ? AND location = ? AND keyword = ? AND content = ? AND action = ? AND username = ?";
            jdbcTemplate.update(sql,new Object[]{title,location,keyword,content,action,username});
            lock.unlock();

        } catch(Exception e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("deletePost");
        }

        return location;
    }

    @Override
    public String updateList(String username){


        String all = null;
        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");


            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery("SELECT * FROM keywords WHERE username =\'"+username+"\'");

            lock.unlock();
            while(rs.next()){
                all+="&"+rs.getString("keyword");
            }
            con.close();


        } catch(SQLException e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("updateList");
        }

        return all;
    }

    @Override
    public String updateListLocation(String username){


        String all = null;
        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");

            Statement stmt = con.createStatement();


            ResultSet rs=stmt.executeQuery("SELECT * FROM location WHERE username =\'"+username+"\'");
            lock.unlock();
            while(rs.next()){
                all+="&"+rs.getString("nameLocation");
                all+=","+rs.getString("lat");
                all+=","+rs.getString("lon");
                all+=","+rs.getString("radius");
            }
            con.close();


        } catch(SQLException e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("updateListLocation");
        }

        return all;
    }

    @Override
    public String updateListPosts(String username){

        String all = null;
        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");

            Statement stmt = con.createStatement();


            ResultSet rs=stmt.executeQuery("SELECT * FROM posts WHERE username =\'"+username+"\'");
            lock.unlock();
            while(rs.next()){
                all+="&"+rs.getString("title");
                all+=","+rs.getString("location");
                all+=","+rs.getString("keyword");
                all+=","+rs.getString("content");
                all+=","+rs.getString("action");
            }
            con.close();


        } catch(SQLException e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("updateListPosts");
        }

        return all;
    }

    @Override
    public String updateDecentralizedListPosts(String username){


        String all = null;
        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");

            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery("SELECT * FROM decentralizedPosts WHERE username =\'"+username+"\'");
            lock.unlock();
            while(rs.next()){
                all+="&"+rs.getString("title");
                all+=","+rs.getString("location");
                all+=","+rs.getString("keyword");
                all+=","+rs.getString("content");
                all+=","+rs.getString("action");
            }
            con.close();


        } catch(SQLException e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("updateDecentralizedListPosts");
        }

        return all;
    }


    @Override
    public String sendLocations(String username){


        String all = null;
        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");

            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery("SELECT * FROM location");
            lock.unlock();
            while(rs.next()){
                all+="&"+rs.getString("nameLocation");
            }
            con.close();

        } catch(SQLException e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("sendLocations");
        }

        return all;
    }

    @Override
    public String sendKeywords(String username){


        String all = null;
        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");

            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery("SELECT * FROM keywords");
            lock.unlock();
            while(rs.next()){
                all+="&"+rs.getString("keyword");
            }

            con.close();


        } catch(SQLException e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("sendKeywords");
        }

        return all;
    }

    @Override
    public String deleteLocation(String username,String nameLocation,String lat, String lon,String radius){


        try {
            lock.lock();
            final String sql = "DELETE FROM location WHERE username = ? AND nameLocation = ? AND lat = ? AND lon = ? AND radius = ?";
            jdbcTemplate.update(sql,new Object[]{username,nameLocation,lat,lon,Integer.parseInt(radius)});
            lock.unlock();

        } catch(Exception e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("deleteLocation");
        }

        return nameLocation;
    }

    @Override
    public String deleteKeyword(String username,String keyword){


        try {

            lock.lock();
            final String sql = "DELETE FROM keywords WHERE username = ? AND keyword = ?";
            jdbcTemplate.update(sql,new Object[]{username,keyword});
            lock.unlock();

        } catch(Exception e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("deleteKeyword");
        }

        return keyword;
    }



    @Override
    public String location(String lat, String lon,String username)  {



        double x = Double.parseDouble(lat);
        double y = Double.parseDouble(lon);

        String msgSent = "";
        String locationName = "";

        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM location");
            lock.unlock();

            while (rs.next()) {

                double tableLat = rs.getDouble("lat");
                double tableLon = rs.getDouble("lon");
                String tableLocationName = rs.getString("nameLocation");
                int rad = rs.getInt("radius");
                int dist = (int)(distance(x, y, tableLat, tableLon, 'K')*1000);

                if(rad>=dist){
                    locationName+="&"+tableLocationName;

                }

            }

            String[] splitLocations = locationName.split("&");
            List<String> keywords = new ArrayList<>();
            List<String> contents = new ArrayList<>();
            List<String> actions = new ArrayList<>();
            List<String> keywordsByUser = new ArrayList<>();
            List<String> nameLocations = new ArrayList<>();
            int i=0;
            for(String nameLocation : splitLocations){
                if(i>0){
                    lock.lock();
                    rs = stmt.executeQuery("SELECT * FROM posts WHERE username <> \'"+username+"\' AND location = \'"+nameLocation+"\'");
                    lock.unlock();
                    while(rs.next()){
                        keywords.add(rs.getString("keyword"));
                        contents.add(rs.getString("content"));
                        actions.add(rs.getString("action"));
                        nameLocations.add(rs.getString("location"));
                    }
                }
                i++;
            }

            lock.lock();
            rs = stmt.executeQuery("SELECT * FROM keywords WHERE username = \'"+username+"\' ");
            lock.unlock();

            while(rs.next()){
                keywordsByUser.add(rs.getString("keyword"));
            }

            for(int j=0;j<keywords.size();j++){
                if(actions.get(j).equals("whitelist")){
                    if(keywordsByUser.contains(keywords.get(j))){
                        msgSent += nameLocations.get(j)+","+contents.get(j)+"&";
                    }
                }else if(actions.get(j).equals("blacklist")){
                    if(!keywordsByUser.contains(keywords.get(j))) {
                        msgSent += nameLocations.get(j) + "," + contents.get(j) + "&";
                    }
                } else{
                    msgSent += nameLocations.get(j) + "," + contents.get(j) + "&";
                }
            }


            if (msgSent.length() > 2) {
                StringBuilder builder = new StringBuilder(msgSent);
                builder.deleteCharAt(msgSent.length() - 1);

                return builder.toString();
            }
            con.close();

        }catch (Exception e){
            System.err.println("SQL exception occurred" + e);
            System.err.println("location");
        }


        return "noMsg";

    }


    @Override
    public String locationD(String lat, String lon,String username)  {



        double x = Double.parseDouble(lat);
        double y = Double.parseDouble(lon);

        String msgSent = "";
        String locationName = "";

        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM location");
            lock.unlock();

            while (rs.next()) {

                double tableLat = rs.getDouble("lat");
                double tableLon = rs.getDouble("lon");
                String tableLocationName = rs.getString("nameLocation");
                int rad = rs.getInt("radius");
                int dist = (int)(distance(x, y, tableLat, tableLon, 'K')*1000);

                if(rad>=dist){
                    locationName+="&"+tableLocationName;

                }

            }

            String[] splitLocations = locationName.split("&");
            List<String> keywords = new ArrayList<>();
            List<String> contents = new ArrayList<>();
            List<String> actions = new ArrayList<>();
            List<String> keywordsByUser = new ArrayList<>();
            List<String> nameLocations = new ArrayList<>();
            int i=0;
            for(String nameLocation : splitLocations){
                if(i>0){
                    lock.lock();
                    rs = stmt.executeQuery("SELECT * FROM decentralizedPosts WHERE username = \'"+username+"\' AND location = \'"+nameLocation+"\'");
                    lock.unlock();
                    while(rs.next()){

                        msgSent+= rs.getString("content")+","+rs.getString("keyword")+","+
                                rs.getString("action")+"&";
                    }
                }
                i++;
            }


            if (msgSent.length() > 2) {
                StringBuilder builder = new StringBuilder(msgSent);
                builder.deleteCharAt(msgSent.length() - 1);

                return builder.toString();
            }
            con.close();
        }catch (Exception e){
            System.err.println("SQL exception occurred" + e);
            System.err.println("locationD");
        }

        return "noMsg";

    }

    @Override
    public String locationInsert(String lat, String lon,String name,String radius,String username)  {

        lock.lock();

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");

            Statement stmt = con.createStatement();

            lock.lock();
            ResultSet rs=stmt.executeQuery("SELECT * FROM location");
            int id=0;
            while(rs.next())
                id=rs.getInt("id");
            id++;

            final String sql = "INSERT INTO location (id, lat, lon, nameLocation, radius, username) VALUES ("+id+",?,?,?,?,?)";
            int radiusI= Integer.parseInt(radius);
            jdbcTemplate.update(sql,new Object[]{lat,lon,name,radiusI,username});
            con.close();
            lock.unlock();

        } catch(SQLException e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("locationInsert");
        }
        lock.unlock();
        return name;

    }

    @Override
    public String keywords(String username,String keyword){



        try {
            lock.lock();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://databasetest.cd1vwdkyb6ad.us-west-2.rds.amazonaws.com:3306/databaseTest","huckleberry", "Blueberry*77");

            Statement stmt = con.createStatement();


            ResultSet rs=stmt.executeQuery("SELECT * FROM keywords");
            int id=0;
            while(rs.next())
                id=rs.getInt("id");
            id++;

            final String sql = "INSERT INTO keywords (id, username, keyword) VALUES ("+id+",?,?)";
            jdbcTemplate.update(sql,new Object[]{username,keyword});
            con.close();
            lock.unlock();

        } catch(SQLException e) {
            System.err.println("SQL exception occurred" + e);
            System.err.println("keywords");
        }

        return keyword;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {

        double theta = lon1 - lon2;

        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);

        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;

        if (unit == 'K') {

            dist = dist * 1.609344;

        } else if (unit== 'N') {

            dist = dist * 0.8684;

        }

        return (dist);

    }


    private double deg2rad(double deg) {

        return (deg * Math.PI / 180.0);
    }


    private double rad2deg(double rad) {

        return (rad * 180.0 / Math.PI);
    }


}
