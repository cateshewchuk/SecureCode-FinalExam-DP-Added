package com.securecode.DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.securecode.DataObject.ItemDataObject;

public class ItemMapper implements RowMapper<ItemDataObject> {


    public ItemDataObject mapRow(ResultSet rs, int rowNum) throws SQLException {

        int itemId = rs.getInt("itemid");
        int userId = rs.getInt("userid");
        String nameValue = rs.getString("name");
        String descriptionValue = rs.getString("description");

        ItemDataObject item = new ItemDataObject(itemId, userId, nameValue, descriptionValue);
        return item;
    }
}