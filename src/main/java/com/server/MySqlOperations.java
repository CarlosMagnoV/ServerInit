package com.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository("mysql")
public class MySqlOperations implements UsersInterface {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public String login(String username,String password){
        // SELECT column_name(s) FROM table_name where column = value
        Users users = null;
        try {
            String sql = "SELECT id, username, password FROM users WHERE username = ?";
            users = jdbcTemplate.queryForObject(sql, new UsersRowMapper(), username);
        }catch (Exception e){
            return "usernameN";
        }

        try {
            String sql = "SELECT id, username, password FROM users WHERE password = ?";
            users = jdbcTemplate.queryForObject(sql, new UsersRowMapper(), password);
        }catch (Exception e){
            return "passwordN";
        }

        String info = users.getUsername()+"&"+users.getPassword();
        return info;
    }

    private static class UsersRowMapper implements RowMapper<Users> {

        @Override
        public Users mapRow(ResultSet resultSet, int i) throws SQLException {
            Users users = new Users();
            users.setId(resultSet.getInt("id"));
            users.setUsername(resultSet.getString("username"));
            users.setPassword(resultSet.getString("password"));
            return users;
        }
    }


    @Override
    public Collection<Users> getAllUsers() {
        // SELECT column_name(s) FROM table_name
        final String sql = "SELECT * FROM users";
        List<Users> users = jdbcTemplate.query(sql, new UsersRowMapper());
        return users;
    }


    @Override
    public String signUp(String username, String password) {

        Users users = null;
        try {
            String sql = "SELECT id, username, password FROM users WHERE username = ?";
            users = jdbcTemplate.queryForObject(sql, new UsersRowMapper(), username);
            return "usernameInUse";
        }catch (Exception e){}

        Collection<Users> usersTotal = getAllUsers();
        int rowsCount = usersTotal.size()+1;

        final String sql = "INSERT INTO users (id, username, password) VALUES ("+rowsCount+", ?, ?)";
        jdbcTemplate.update(sql, new Object[]{username, password});

        return username;

    }
}
