package org.elucidus.generation.base.testHelpers;

import java.util.Hashtable;  
import java.util.List;
import java.util.TreeSet;  

import org.elucidus.currency.Item;
 
public abstract class GeneratorTest {
	protected void debugPrintAttributes(List<Item> itemList) {
		debugPrintAttributes(itemList, 128);  
	}  
	  
	protected void debugPrintAttributes(List<Item> itemList, int maxValueLength) {
		for (Item i : itemList) {
			Hashtable<String, Object> attributes = i.getContents();
			
			for (String key : new TreeSet<String>(attributes.keySet())) {
				String value = attributes.get(key).toString();
  
				if (value.length() > maxValueLength) {
					value = value.substring(0, maxValueLength);
					value = value.replace("\n", "");
					value = value.replace("\r", "");
				}

				System.out.println(key + " = " + value);
			}
		}
	}  
}
 