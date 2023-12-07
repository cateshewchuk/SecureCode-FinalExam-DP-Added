package com.securecode.DomainObject;

import java.util.ArrayList;
import com.securecode.DataObject.ItemDataObject;
import com.securecode.DomainPrimitive.ItemName;
import com.securecode.DomainPrimitive.ItemDescription;

public class ItemDomainObject {

	public int itemId;
	public int userId;
	public ItemName name;
    public ItemDescription description;

	public UserDomainObject user;

    public ItemDomainObject(int userId, String name, String description) throws Exception {
        this.userId = userId;
        this.name = new ItemName(name);
        this.description = new ItemDescription(description);
    }

	public ItemDomainObject(ItemDataObject data) throws Exception {

        this.itemId = data.itemId;
        this.userId = data.userId;
        this.name = new ItemName(data.name);
        this.description = new ItemDescription(data.description);
	}
/* 
	public ItemDomainObject(EditItemRequest data) throws Exception {
	
        try {
            this.itemId = data.getItemId();
            this.authorId = data.getAuthorId();
            this.item = new Item(data.getItem());
        } catch (Exception ex) {
            throw ex;
        }

	}

	public ItemDomainObject(CreateItemRequest data) throws Exception {

        try {
            this.authorId = data.getAuthorId();
            this.item = new Item(data.getItem());
        } catch (Exception ex) {
            throw ex;
        }
    
	}
*/
	public static ArrayList<ItemDomainObject> MapList(ArrayList<ItemDataObject> dataList) throws Exception {
		ArrayList<ItemDomainObject> domainObjectList = new ArrayList<ItemDomainObject>();
		
		for (int i=0; i<dataList.size(); i++) {
			ItemDomainObject domain = new ItemDomainObject(dataList.get(i));
			domainObjectList.add(domain);
		}

		return domainObjectList;
	}


}
