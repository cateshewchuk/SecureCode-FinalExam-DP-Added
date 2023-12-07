package com.securecode.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.securecode.DataObject.UserDataObject;
import com.securecode.DataObject.ItemDataObject;
import com.securecode.DataAccess.UserDataAccess;
import com.securecode.DataAccess.ItemDataAccess;
import com.securecode.DomainObject.UserDomainObject;
import com.securecode.DomainObject.ItemDomainObject;
import com.securecode.DomainPrimitive.ItemName;
import com.securecode.DomainPrimitive.ItemDescription;
import com.securecode.Web.Alert;

public class ItemModel {
	

	public static ArrayList<ItemDomainObject> GetAllItems(Alert alert) throws Exception {
		ArrayList<ItemDataObject> itemDataList = ItemDataAccess.getAllItems();
		ArrayList<ItemDomainObject> itemDomainList = ItemDomainObject.MapList(itemDataList);

        //Get Users for each Item
        HashSet<Integer> userIdSet = new HashSet<Integer>();

		for (ItemDomainObject itemDO : itemDomainList) {
			userIdSet.add(itemDO.userId);
		}

		ArrayList<UserDomainObject> users = UserModel.GetUsersByIds(alert, userIdSet);

		HashMap<Integer, UserDomainObject> userMap = new HashMap<Integer, UserDomainObject>();
		for (UserDomainObject user : users) {
			userMap.put(user.getUserId(), user);
		}

		for (ItemDomainObject itemDO : itemDomainList) {
			itemDO.user = userMap.get(itemDO.userId);
		}


		return itemDomainList;
	}


    public static ArrayList<ItemDomainObject> GetAllItemsByUser(Alert alert, int userid) throws Exception {
		ArrayList<ItemDataObject> itemDataList = ItemDataAccess.getAllItemsByUser(userid);
		ArrayList<ItemDomainObject> itemDomainList = ItemDomainObject.MapList(itemDataList);

        //Get Users for each Item
        HashSet<Integer> userIdSet = new HashSet<Integer>();

		for (ItemDomainObject itemDO : itemDomainList) {
			userIdSet.add(itemDO.userId);
		}

		ArrayList<UserDomainObject> users = UserModel.GetUsersByIds(alert, userIdSet);

		HashMap<Integer, UserDomainObject> userMap = new HashMap<Integer, UserDomainObject>();
		for (UserDomainObject user : users) {
			userMap.put(user.getUserId(), user);
		}

		for (ItemDomainObject itemDO : itemDomainList) {
			itemDO.user = userMap.get(itemDO.userId);
		}


		return itemDomainList;
	}

	public static ItemDomainObject GetItemById(Alert alert, int id) throws Exception {
		ItemDataObject itemData = ItemDataAccess.getItemById(id);
        ItemDomainObject itemDomain = new ItemDomainObject(itemData);

        //Get User for item
		UserDomainObject user = UserModel.GetUserById(alert, itemDomain.userId);
        itemDomain.user = user;

        return itemDomain;
	}


	public static ItemDomainObject UpdateItem(Alert alert, ItemDomainObject domainItemToUpdate) throws Exception {
		
		if (!validate(alert, domainItemToUpdate))
			return null;

		ItemDataObject newItem = new ItemDataObject(domainItemToUpdate.itemId, domainItemToUpdate.userId, domainItemToUpdate.name.getValue(), domainItemToUpdate.description.getValue());
		ItemDataObject updatedItem = ItemDataAccess.updateItem(newItem);

        return new ItemDomainObject(updatedItem);
	}

	public static ItemDomainObject CreateItem(Alert alert, ItemDomainObject domainItemToCreate) throws Exception {

		if (!validate(alert, domainItemToCreate))
			return null;

		if(domainItemToCreate.userId== 0){
			alert.addAlert(Alert.Type.Error, "Must include all parameters.");
			return null;
		}

		//check if author is registered


		ItemDataObject newItem = new ItemDataObject(0, domainItemToCreate.userId, domainItemToCreate.name.getValue(), domainItemToCreate.description.getValue());
		ItemDataObject createdItem = ItemDataAccess.createItem(newItem);

        return new ItemDomainObject(createdItem);
	}



	private static boolean validate(Alert alert, ItemDomainObject itemDomainObject) throws Exception {
		boolean valid = true;


		UserDataObject user = UserDataAccess.getUserById(itemDomainObject.userId);
		if (user == null) {
            valid = false;
            throw new Exception("UserId does not exist");
		}




		return valid;

	}
}