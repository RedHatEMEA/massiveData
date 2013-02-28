package org.elucidus.persistence.mongo.test;

import java.net.UnknownHostException;

import org.junit.Assert; 
import org.junit.Ignore;

import com.mongodb.BasicDBObject;   
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import org.druid.currency.Item;
import org.druid.currency.utils.ItemNameTools;
import org.elucidus.persistence.mongo.MongoConnectionManager;
import org.elucidus.persistence.mongo.PersisterMongo;
import org.junit.Test;
  
public class TestConnectivity {
	@Test
	@Ignore
	public void testConnectivity() throws UnknownHostException {
		MongoConnectionManager mcm = new MongoConnectionManager("localhost");
		
		BasicDBObject object = new BasicDBObject();
		object.append("key", "val");
	
		DB db = mcm.getDB("testDatabase");
		
		Assert.assertFalse(db.collectionExists("col1"));
		 
		DBCollection dbCollection = db.createCollection("col1", object);
		
		Assert.assertTrue(db.collectionExists("col1"));
		Assert.assertNotNull(dbCollection);
		
		db.getCollection("col1").drop();
		
		Assert.assertFalse(db.collectionExists("col1"));
		 
		mcm.close();
	}
	
	@Test
	public void testInsertContainsRemove() throws Exception {
		Item item = new Item();
		item.addString("name", "Hilda"); 
		item.addString("jobTitle", "Waffle Sales Representitive"); 
		
		PersisterMongo persister = new PersisterMongo();
		persister.setParameter("database", "testingDb");
		persister.setParameter("collection", "testingCollection");
		persister.setLocation("localhost"); 
		persister.clearAll();
		persister.persistItem(item, false);  
		    
		Assert.assertTrue(persister.contains(item));
 		
		persister.removeItem(item);
		
		Assert.assertFalse(persister.contains(item));  
	} 
	
	@Test
	@Ignore
	public void testRawInsert() throws Exception {
		String database = "main"; 
		String collection = "testingItemInsert";
		MongoConnectionManager mcm = new MongoConnectionManager("127.0.0.1");

		mcm.getDB(database).getCollection(collection).drop();
		Assert.assertFalse(mcm.getDB(database).collectionExists(collection));
		mcm.getDB(database).createCollection(collection, null);  
		
		Item testingItem = new Item();
		testingItem.addString("Title", "The Wonderful Adventure of the Curious Developer");
		DBObject dbItem = mcm.itemToMongoObject(testingItem); 
		 
		mcm.getDB(database).getCollection(collection).insert(dbItem);
		
		mcm.dumpDbToStdOut(database);
		mcm.close(); 
	}
}  
