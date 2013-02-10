package org.elucidus.persistence.mongo;

import java.net.UnknownHostException;
import java.util.Hashtable;

import org.elucidus.currency.Item;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoConnectionManager extends MongoClient {
	public MongoConnectionManager(String host) throws UnknownHostException {
		super(host);     
	} 
	 
	public void dumpDbToStdOut(String database) {
		DB db = this.getDB(database);
		
		System.out.println("Dumping DB to stdout:" + database);
		  
		for (String collectionName : db.getCollectionNames()) {
			if (collectionName.equals("system.indexes")) {
				continue;
			}
			
			System.out.println("Collection:" + collectionName);
			
			for (DBObject s : db.getCollection(collectionName).find()) {
				for (String key : s.keySet()) {
					System.out.println("Object: " + key + " = " + s.get(key));
				}
			}
		}
	}

	public DBObject itemToMongoObject(Item testingItem) {
		BasicDBObject mongoObject = new BasicDBObject();
		
		Hashtable<String, Object> itemAspects = testingItem.getContents();
		
		for (String itemAspectKey : itemAspects.keySet()) {
			mongoObject.append(itemAspectKey, itemAspects.get(itemAspectKey));
		}
		
		return mongoObject; 
	}

}
