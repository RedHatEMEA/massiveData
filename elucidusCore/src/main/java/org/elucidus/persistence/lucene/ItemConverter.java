package org.elucidus.persistence.lucene;

import java.util.Hashtable;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.elucidus.currency.Item;

/**
 * This class provides a converter that takes a generic Elucidus Item and produces a
 * LUCENE 3.0.2 Document that matches it. This document can then be used in the persistence
 * of the Item to a searchable LUCENE directory.
 * @author Ian Lawson <@link 
 *
 */
public class ItemConverter
{
  /**
   * private constructor, class provides static methods only.
   */
  private ItemConverter()
  {
    
  }
  /**
   * Convert method that takes an Elucidus Item and produces a LUCENE 3.0.2 document.
   * @param item item to convert to a LUCENE document
   * @return fully constructed LUCENE document
   */
  @SuppressWarnings("unused")
  public static Document convert( Item item )
  {
    // Instantiate the document
    Document workingDocument = new Document();
    
    Hashtable<String,Object> contents = item.getContents();
    
    for( String key : contents.keySet())
    {
      Object value = contents.get(key);
      
      if( value.getClass().getCanonicalName().equals( "java.lang.String"))
      {
        String actualValue = (String)value;
        
        if( actualValue == null ) 
        {
          actualValue = "null";
        }
        
        // Create a matching indexed field within the document for each string component
        workingDocument.add( new Field( key, actualValue, Field.Store.YES, Field.Index.ANALYZED));
      }
    }
    
    String convertedDate = Long.toString(System.currentTimeMillis());
    
    workingDocument.add( new Field( "converter.date", convertedDate, Field.Store.YES, Field.Index.ANALYZED));
    
    return workingDocument;
  }
}
