package com.securecode.DataAccess;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.securecode.DataObject.ItemDataObject;

public class ItemDataAccess {


	private static String tableName = "item";
	private static String fields = "itemid, userid, name, description";
	private static String idField = "itemid";


	public ItemDataAccess() {
	}


	public static ArrayList<ItemDataObject> getAllItems() {
		String sql = String.format("select %s from %s", fields, tableName);
		List<ItemDataObject> list = DBAccesser.jdbcTemplate.query(sql, new ItemMapper());

		return (ArrayList<ItemDataObject>)list;
	}

	public static ArrayList<ItemDataObject> getAllItemsByUser(int userid) {
		String sql = String.format("select %s from %s where userid = %s", fields, tableName, userid);
		List<ItemDataObject> list = DBAccesser.jdbcTemplate.query(sql, new ItemMapper());

		return (ArrayList<ItemDataObject>)list;
	}

	public static ItemDataObject getItemById(int id) {

		String sql = String.format("select %s from %s where %s = ?", fields, tableName, idField);
		List<ItemDataObject> list = DBAccesser.jdbcTemplate.query(sql, new ItemMapper(), id);
		
		if (list.size() < 1)
			return null;

		return list.get(0);

	}

public static ItemDataObject updateItem(ItemDataObject data) {
		
		String sql = String.format("update %s set authorid = ?, item = ? where %s = %s", tableName, idField, data.itemId);
		DBAccesser.jdbcTemplate.update(sql, data.userId, data.name, data.description);
	
		return getItemById(data.itemId);
	}


	public static ItemDataObject createItem(ItemDataObject data) {

		String sql = String.format("INSERT INTO %s (userid, name, description) VALUES (%s,?,?);", tableName, data.userId);

        KeyHolder keyHolder = new GeneratedKeyHolder();

		PreparedStatementCreator preparedStatementCreator = c-> {
            PreparedStatement preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, data.name);
            preparedStatement.setString(2, data.description);
            return preparedStatement;
        };
		
        int count = DBAccesser.jdbcTemplate.update(preparedStatementCreator, keyHolder);
		if (count == 1) {
			int id = keyHolder.getKey().intValue();
			return getItemById(id);
		}

		//Error case
		return null;

	}




}