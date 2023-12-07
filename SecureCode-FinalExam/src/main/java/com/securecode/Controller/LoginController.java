package com.securecode.Controller;


import com.securecode.Logging;
import com.securecode.DomainObject.UserDomainObject;
import com.securecode.DomainPrimitive.SecureString;
import com.securecode.Logging.Level;
import com.securecode.Model.UserModel;
import com.securecode.Web.Alert;

public class LoginController {
	//The LoginController will convert the request from the REST/Web Request and the Domain Model.
	//Then it will call the Model or Service, and convert the response from the Domain model
	//to the REST Response.
	
	public static UserDomainObject Login(Alert alert, String username, SecureString password) {
		try{
            UserDomainObject member = UserModel.CheckLogin(alert, username, password);
            return member;

		} catch (Exception ex) {
            Logging.Log(Level.ERROR, String.format("Error for authentication user %s, password %s", username, password));
            Logging.Log(Level.ERROR, ex.getMessage());
            Logging.Log(Level.ERROR, ex.getStackTrace().toString());

            alert.addAlert(Alert.Type.Error, "Error in Authenticating User");
			return null;
		}

	}

}
