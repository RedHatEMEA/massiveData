package org.elucidus.tests;

import java.util.Map;

import org.elucidus.currency.Item;
import org.elucidus.surfacing.basicFiles.BasicFileSurfacer;

/**
 * Simple test to flex the private methods within the BasicFileSurfacer that convert the proprietary
 * file format for an Item into an Elucidus Item.
 * @author Ian Lawson <a href="mailto:ian.lawson@redhat.com">ian.lawson@redhat.com</a>
 */
public class BasicFileSurfacerReaderTest
{
  public static void main( String[] args )
  {
    if( args.length != 1 )
    {
      System.out.println( "Usage: java BasicFileSurfacerTest targetFile");
      System.exit(0);
    }
    
    try
    {
      long loadStart = System.currentTimeMillis();
      Item testItem = BasicFileSurfacer.testFileRead(args[0]);
      long loadEnd = System.currentTimeMillis();
      
      System.out.println( "Read file " + args[0] + " in " + ( loadEnd - loadStart ) + "ms." );
      
      System.out.println( "Item creation UTC: " + testItem.getCreationUTC());
      
      Map<String,Object> contents = testItem.getContents();
      
      for( String key : contents.keySet())
      {
        Object value = contents.get(key);
        
        if( value.getClass().getCanonicalName().equals( "java.lang.String"))
        {
          System.out.println( "  " + key + " : " + (String)value);
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
