package com.securecode.DomainObject;

import java.util.ArrayList;
import com.securecode.DataObject.UserDataObject;
import com.securecode.DomainPrimitive.Username;

public class UserDomainObject {

	private int userid;
	private Username username;

    public int getUserId() {
        return userid;
    }
    public String getUsername() {
        return username.getValue();
    }


	public UserDomainObject(int userId, String username, String fullname) throws Exception {
        try {
            this.userid = userId;
            this.username = new Username(username);
           }
        catch (Exception ex) {
            throw ex;
        }
    }


	public UserDomainObject(UserDataObject data) throws Exception {
        try{
            this.userid = data.userid;
            this.username = new Username(data.username);
  
        }
        catch (Exception ex) {
            throw ex;
        }
	}
	
	public static ArrayList<UserDomainObject> MapList(ArrayList<UserDataObject> dataList) throws Exception {
		ArrayList<UserDomainObject> domainObjectList = new ArrayList<UserDomainObject>();
		
		for (int i=0; i<dataList.size(); i++) {
			UserDomainObject domain = new UserDomainObject(dataList.get(i));
			domainObjectList.add(domain);
		}

		return domainObjectList;
	}

}