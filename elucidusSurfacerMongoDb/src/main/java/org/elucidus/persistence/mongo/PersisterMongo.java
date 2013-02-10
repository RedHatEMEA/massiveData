package org.elucidus.persistence.mongo;

import java.net.UnknownHostException;
import java.util.List;

import org.elucidus.currency.Item;
import org.elucidus.currency.utils.ItemNameTools;
import org.elucidus.exceptions.ItemNameFormatException;
import org.elucidus.exceptions.PersistenceException;
import org.elucidus.persistence.IPersister;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteResult;

public class PersisterMongo implements IPersister {
	private MongoConnectionManager mcm;
	private String collection;
	private String database;
	 
	public void setLocation(String location) throws PersistenceException {
		try {
			mcm = new MongoConnectionManager(location);
		} catch (UnknownHostException e) {
			throw new PersistenceException("Cannot connect to Mongo", e); 
		}
	}  

	public void setParameter(String name, String value) throws PersistenceException {
		switch (name) {
		case "database":
			this.database = value;
			break;
		case "collection":
			this.collection = value;
			break; 
		default:
			throw new PersistenceException("Paramater: " + name + " is not valid for this persister: " + this.getClass().getSimpleName());				
		}
	} 

	public boolean removeItem(Item item) throws PersistenceException {
		for (String itemAspectKey : item.getContents().keySet()) {
			DBObject obj = new BasicDBObject();
			
			this.mcm.getDB(this.database).getCollection(collection).remove(obj);  
		}
		
		return true; 
	}

	public boolean persistItem(Item item, boolean overwrite) throws PersistenceException {
		BasicDBObject obj = new BasicDBObject();
		
		for (String itemAspectKey : item.getContents().keySet()) {
			obj.append(itemAspectKey, item.getContents().get(itemAspectKey));  
		}
  		
		WriteResult res = mcm.getDB(this.database).getCollection(this.collection).insert(obj);
		 
		return true; 
	}

	public List<Item> persistItems(List<Item> items, boolean overwrite) throws PersistenceException {
		for (Item i : items) {
			this.persistItem(i, overwrite);
		}
		
		return null;  
	}

	public boolean contains(Item item) throws PersistenceException {
		QueryBuilder qb = QueryBuilder.start();
		
		mcm.dumpDbToStdOut(this.database);
 
		qb = qb.elemMatch(mcm.itemToMongoObject(item)); 
		
		DBCursor cur = this.mcm.getDB(this.database).getCollection(this.collection).find(qb.get());
		
		while (cur.hasNext()) {
			System.out.println(cur.curr());
		}
		
		mcm.dumpDbToStdOut(this.database);
		
		if (cur.length() == item.getContents().size()) {
			return true;
		} else {
			return false;
		}
	}

	public void clearAll() { 
		DB db = this.mcm.getDB(this.database);
		
		if (db.collectionExists(this.collection)) {
			db.getCollection(this.collection).drop(); 
		} 
	}

}
