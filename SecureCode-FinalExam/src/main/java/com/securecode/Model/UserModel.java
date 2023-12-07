package com.securecode.Model;
import java.util.ArrayList;
import java.util.Set;

import com.securecode.DataAccess.UserDataAccess;
import com.securecode.DataObject.UserDataObject;
import com.securecode.DomainObject.UserDomainObject;
import com.securecode.DomainPrimitive.Password;
import com.securecode.DomainPrimitive.SecureString;
import com.securecode.DomainPrimitive.Username;
import com.securecode.Web.Alert;
import com.securecode.Web.Alert.Type;


public class UserModel {
	
	public static UserDomainObject GetUserById(Alert alert, int id) throws Exception {
		UserDataObject userData = UserDataAccess.getUserById(id);
		UserDomainObject userDomain = new UserDomainObject(userData);
		return userDomain;
	}

	public static ArrayList<UserDomainObject> GetUsersByIds(Alert alert, Set<Integer> ids) throws Exception {
		ArrayList<UserDataObject> userDataList = UserDataAccess.getUsersByIds(ids);
		ArrayList<UserDomainObject> userDomainList = UserDomainObject.MapList(userDataList);
		return userDomainList;
	}

	public static ArrayList<UserDomainObject> GetAllUsers(Alert alert) throws Exception {
		ArrayList<UserDataObject> userDataList = UserDataAccess.getAllUsers();
		ArrayList<UserDomainObject> userDomainList = UserDomainObject.MapList(userDataList);
		return userDomainList;
	}


	public static UserDomainObject CheckLogin(Alert alert, String username, SecureString password) {

        try {
            Username usernameDP = new Username(username);
            Password passwordDP = new Password(password.getValueOnce().toCharArray());

            UserDataObject userData = UserDataAccess.getUserByCredentials(usernameDP, passwordDP);
            if (userData == null) {
                alert.addAlert(Type.Error, "Invalid Credentials");
                return null;
            }

            UserDomainObject userDomain = new UserDomainObject(userData);
            return userDomain;

        } catch (Exception ex) {
            alert.addAlert(Type.Error, "Invalid Credentials");
            return null;
        }

	}

}