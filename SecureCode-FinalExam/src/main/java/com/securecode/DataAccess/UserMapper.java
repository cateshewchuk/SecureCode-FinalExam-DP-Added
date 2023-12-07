package com.securecode.DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.securecode.DataObject.UserDataObject;

public class UserMapper implements RowMapper<UserDataObject> {


    public UserDataObject mapRow(ResultSet rs, int rowNum) throws SQLException {

        int userId = rs.getInt("userid");
        String username = rs.getString("username");

        UserDataObject user = new UserDataObject(userId, username);
        return user;
    }
}