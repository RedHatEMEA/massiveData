package org.elucidus.generation.base;

import java.io.ByteArrayInputStream; 
import java.io.File;
import java.io.FileInputStream;  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.GenerationException;
import org.elucidus.generation.IGenerator; 
    
public abstract class BaseGenerator implements IGenerator {
	@Override
	public List<Item> generate(File inputFile) throws GenerationException {
		try {
			return generate(new FileInputStream(inputFile));
		} catch (FileNotFoundException e) {
			throw new GenerationException(e); 
		}	
	};

	@Override
	public List<Item> generate(String inputString) throws GenerationException {
		return generate(new ByteArrayInputStream(inputString.getBytes(Charset.defaultCharset())));
	}
	  
	/* 
	 * This is bad interface method imo, but hey ho. Without any understanding
	 * of the URL protocol, exceptions cannot be caught and handled in a sensible way.  
	 */
	@Override
	public List<Item> generate(URL url) throws GenerationException {
		
		try {
			return generate(url.openStream());
		} catch (IOException e) {
			throw new GenerationException(e); 
		} 
	}

	@Override
	public void setParameter(String name, String value)	throws GenerationException {
		
	}  
	
	protected String sanitizeValue(Object value) {
		if (value == null) {
			return "";
		} else { 
			return value.toString().trim(); 
		}
	}
}
