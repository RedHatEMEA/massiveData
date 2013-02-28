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
import org.elucidus.currency.StandardAspects;
import org.elucidus.exceptions.GenerationException;
import org.elucidus.generation.IGenerator; 
    
public abstract class BaseGenerator implements IGenerator {
	@Override 
	public List<Item> generate(File inputFile, String source) throws GenerationException {
		try {
			return generate(new FileInputStream(inputFile), source);
		} catch (FileNotFoundException e) {
			throw new GenerationException(e); 
		}	
	};

	@Override
	public List<Item> generate(String inputString, String source ) throws GenerationException {
		return generate(new ByteArrayInputStream(inputString.getBytes(Charset.defaultCharset())), source );
	}
	  
	/* 
	 * This is bad interface method imo, but hey ho. Without any understanding
	 * of the URL protocol, exceptions cannot be caught and handled in a sensible way.  
	 */
	@Override
	public List<Item> generate(URL url, String source) throws GenerationException {
		
		try {
			return generate(url.openStream(), source);
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
	
	public void addFileRelatedAspects(Item i, File f) {
		i.addString(StandardAspects.FILE_FILENAME, f.getAbsolutePath());
		i.addObject(StandardAspects.FILE_MODIFIED_UTC, f.lastModified());
		i.addObject(StandardAspects.FILE_SIZE_BYTES, f.length()); 
	}  
}
