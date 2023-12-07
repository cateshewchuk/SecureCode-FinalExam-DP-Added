package com.securecode.DataObject;

public class ItemDataObject {

	public int itemId;
	public int userId;
	public String name;
	public String description;


	public ItemDataObject (int itemId, int userId, String name, String description) {
		this.itemId = itemId;
		this.userId = userId;
		this.name = name;
        this.description = description;
	}
}