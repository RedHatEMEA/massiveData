package org.elucidus.tests;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

import org.elucidus.currency.Item;
import org.elucidus.generation.GeneratorFactory;
import org.elucidus.generation.IGenerator;

public class BasicTitledCSVGeneratorTest
{
  public static void main( String[] args )
  {
    if( args.length != 1 )
    {
      System.out.println( "Usage: java BasicTitledCSVGeneratorTest targetFile");
      System.exit(0);
    }
    
    new BasicTitledCSVGeneratorTest( args[0]);
  }
  
  public BasicTitledCSVGeneratorTest( String targetFile )
  {
    try
    {
      // First, use the Factory to get a generator appropriately
      IGenerator generator = GeneratorFactory.getGenerator("org.elucidus.generation.basic.BasicTitledCSVGenerator");
      
      // Now try the generator
      long startTime = System.currentTimeMillis();
      List<Item> items = generator.generate(new File( targetFile ));
      long endTime = System.currentTimeMillis();
      
      System.out.println( "Generated " + items.size() + " items in " + ( endTime - startTime ) + "ms.");
      
      for( int loop = 0; loop < items.size(); loop++ )
      {
        System.out.println( "  Item(" + ( loop + 1 ) + "):");
        
        Hashtable<String,Object> contents = items.get(loop).getContents();
        
        for( String key : contents.keySet())
        {
          System.out.println( "    " + key + ":" + (String)contents.get(key));
        }        
      }
    }
    catch( Exception exc )
    {
      System.out.println( "Test failed due to " + exc.toString());
      exc.printStackTrace();
    }
  }
}
