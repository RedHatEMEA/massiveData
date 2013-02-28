package org.elucidus.generation.base.testHelpers;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import org.elucidus.currency.Item;
import org.elucidus.currency.utils.ItemNameTools;
import org.elucidus.exceptions.GenerationException;
import org.elucidus.generation.IGenerator;
import org.elucidus.generation.base.BaseGenerator;

public class DefaultFallbackGenerator extends BaseGenerator,Generator {
	private Vector<Item> aspects = new Vector<Item>(); 

	@Override
	public List<Item> generate(InputStream inputStream, String source )
			throws GenerationException {

		Item item = new Item();
		item.addString("uuid", ItemNameTools.generateUUID(false));
    
		
		aspects.add(item); 
		 
		return aspects;
	}
	
	@Override
	public List<Item> generate(File inputFile) throws GenerationException {
		List<Item> itemList = super.generate(inputFile);
		
		for (Item i : itemList) {
			this.addFileRelatedAspects(i, inputFile); 
		}       
		   
		return itemList; 
	}

}
