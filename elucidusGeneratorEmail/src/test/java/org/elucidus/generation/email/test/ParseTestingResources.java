package org.elucidus.generation.email.test;

import java.io.File; 
import java.io.FilenameFilter;
import java.util.List;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.GenerationException;
import org.elucidus.generation.base.test.GeneratorTest;
import org.elucidus.generation.email.GeneratorEmail;
import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory; 
import org.junit.runner.RunWith;

@RunWith(Theories.class)   
public class ParseTestingResources extends GeneratorTest {
	@DataPoints
	public static File[] getTestingFiles() {
		File testingResourcesDirectory = new File("src/test/resources");

		return testingResourcesDirectory.listFiles(new FilenameFilter() {
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".email"); 
			}   
		});
	}       
	  
	
	
	@Theory  
	public void parseTestingResources(File testingFile) throws GenerationException{
		System.out.println("GeneratorEmail Testing: " + testingFile.getAbsoluteFile());
		System.out.println("---------------------------------------------");

		Assert.assertNotNull(testingFile);
		 
		GeneratorEmail gen = new GeneratorEmail();
		
		List<Item> items = gen.generate(testingFile);
		
		Assert.assertNotNull(items);
		 
		Assert.assertNotEquals(0, items.size());
		
		this.debugPrintAttributes(items); 
	}
}
  