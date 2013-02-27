package org.elucidus.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.elucidus.currency.Item;
import org.elucidus.currency.utils.ItemNameTools;
import org.elucidus.currency.utils.ItemQualifier;
import org.elucidus.generation.GeneratorFactory;
import org.elucidus.generation.IGenerator;

public class BasicTitledCSVGeneratorTest
{
  public static void main( String[] args )
  {
    if( args.length != 2 )
    {
      System.out.println( "Usage: java BasicTitledCSVGeneratorTest targetFile dataCacheName");
      System.exit(0);
    }
    
    new BasicTitledCSVGeneratorTest( args[0], args[1]);
  }
  
  public BasicTitledCSVGeneratorTest( String targetFile, String cacheName )
  {
    try
    {
      // First, use the Factory to get a generator appropriately
      IGenerator generator = GeneratorFactory.getGenerator("org.elucidus.generation.basic.BasicTitledCSVGenerator");
      
      // Now try the generator
      long startTime = System.currentTimeMillis();
      List<Item> items = generator.generate(new File( targetFile ), targetFile );
      long endTime = System.currentTimeMillis();
      
      System.out.println( "Generated " + items.size() + " items in " + ( endTime - startTime ) + "ms.");
      
      System.out.println( "Qualifying aspects of items.");
      
      List<Item> qualifiedItems = new ArrayList<Item>();
      
      for( Item item : items )
      {
        Item qualifiedItem = ItemQualifier.qualify(item, cacheName, ItemNameTools.generateUUID(true));
        
        qualifiedItems.add( qualifiedItem );
      }
      
      for( int loop = 0; loop < qualifiedItems.size(); loop++ )
      {
        System.out.println( "  Item(" + ( loop + 1 ) + "):");
        
        Hashtable<String,Object> contents = qualifiedItems.get(loop).getContents();
        
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
