package org.elucidus.persistence.mongo;

import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.druid.currency.Item;
import org.druid.currency.utils.ItemNameTools;
import org.druid.exceptions.ItemNameFormatException;
import org.druid.exceptions.PersistenceException;
import org.druid.persistence.IPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteResult;
 
public class PersisterMongo implements IPersister {
	private static final transient Logger LOG = LoggerFactory.getLogger(PersisterMongo.class); 
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
		
		Hashtable<String, Object> obs = item.getContents();
 
		for (String aspectKey : obs.keySet()) { 
			qb.put(aspectKey).exists(obs.get(aspectKey));
		}    
		     
		DB db = this.mcm.getDB(this.database);
		DBCollection col = db.getCollection(this.collection);  
		DBCursor cur = col.find(qb.get());
		
		System.out.println("Contains mongo query: " + qb.get().toString());
		System.out.println("cur size: " + cur.size());
		
		if (cur.size() != 1) {
			return false; 			
		} else {
			return true;
		} 
	} 

	public void clearAll() { 
		DB db = this.mcm.getDB(this.database);
		
		if (db.collectionExists(this.collection)) {
			db.getCollection(this.collection).drop(); 
		} 
	}


  	public Map<String, String> report() throws PersistenceException {
		return null;
	} 
 
	public void finalise() {}
	public void initialise(String offeringOfCake) throws PersistenceException {}
}
