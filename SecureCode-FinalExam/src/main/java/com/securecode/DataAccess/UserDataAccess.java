package com.securecode.DataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import com.securecode.DataObject.UserDataObject;
import com.securecode.DomainPrimitive.Password;
import com.securecode.DomainPrimitive.Username;



public class UserDataAccess {

	private static String tableName = "user2";
	private static String fields = "userid, username";
	private static String idField = "userid";

	
	public UserDataAccess() {
	}


	public static ArrayList<UserDataObject> getAllUsers() {
		String sql = String.format("select %s from %s", fields, tableName);
		List<UserDataObject> list = DBAccesser.jdbcTemplate.query(sql, new UserMapper());

		return (ArrayList<UserDataObject>)list;
	}

	public static ArrayList<UserDataObject> getUsersByIds(Set<Integer> ids) {

		String idsString = "";
		for (Integer id: ids) {
			if (!idsString.equals("")) idsString += ",";
			idsString += "'" + id + "'";
		}

		String sql = String.format("select %s from %s where %s in (%s)", fields, tableName, idField, idsString);
		List<UserDataObject> list = DBAccesser.jdbcTemplate.query(sql, new UserMapper());
		
		return (ArrayList<UserDataObject>)list;

	}



	public static UserDataObject getUserById(int id) {

		String sql = String.format("select %s from %s where %s = ?", fields, tableName, idField);
		List<UserDataObject> list = DBAccesser.jdbcTemplate.query(sql, new UserMapper(), id);
		
		if (list.size() < 1)
			return null;

		return list.get(0);

	}

   	public static UserDataObject getUserByCredentials(Username username, Password password) {
		
		String sql = String.format("select %s from %s where username = ? AND password = ?", fields, tableName);
		List<UserDataObject> list = DBAccesser.jdbcTemplate.query(sql, new UserMapper(), username.getValue(), password.getValueOnce());
		
		if (list.size() < 1)
			return null;

		return list.get(0);
	}




}