package org.elucidus.currency;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.elucidus.exceptions.ContentNotUniqueException;
import org.elucidus.exceptions.InvalidObjectTypeException;

/**
 * This class represents a single lowest common denominator Item for an Elucidus system.
 * @author Ian Lawson (<a href="mailto:ian.lawson@redhat.com">ian.lawson@redhat.com</a>).
 */
public class Item implements Serializable
{
  // Required static for Serializable interface
  private static final long serialVersionUID = 1L;

  // Class Properties
  private Hashtable<String,Object> _contents = null;
  private List<String> _comparitors = null;
  private long _creationUTC = 0;
  
  /**
   * Parameterless constructor, empty item
   */
  public Item()
  {
    _creationUTC = System.currentTimeMillis();
  }
  
  /**
   * Deep copy constructor with provided contents
   * @param contents existing contents for creation of a new item
   * @param comparitors item comparitors for uniqueness checks
   */
  public Item( Hashtable<String,Object> contents, List<String> comparitors )
  {
    _contents = new Hashtable<String,Object>();
    
    for( String key : contents.keySet())
    {
      _contents.put( key, contents.get(key));
    }
    
    _comparitors = new ArrayList<String>();
    
    for( String comparitor : comparitors )
    {
      _comparitors.add(comparitor);
    }
    
    _creationUTC = System.currentTimeMillis(); 
  }
  
  /**
   * Dependency injection constructor with time.
   * @param contents contents to store
   * @param comparitors comparitors to store
   * @param originalCreationUTC original UTC creation time to store
   */
  public Item( Hashtable<String,Object> contents, List<String> comparitors, long originalCreationUTC )
  {
    _contents = new Hashtable<String,Object>();
    
    for( String key : contents.keySet())
    {
      _contents.put( key, contents.get(key));
    }
    
    _comparitors = new ArrayList<String>();
    
    for( String comparitor : comparitors )
    {
      _comparitors.add(comparitor);
    }
    
    _creationUTC = originalCreationUTC;     
  }
  
  /**
   * Deep item copy constructor.
   * @param original original item to deep copy
   */
  public Item( Item original )
  {
    _contents = new Hashtable<String,Object>();
    
    for( String key : original.getContents().keySet() )
    {
      _contents.put(key, original.getContents().get(key));
    }
    
    _comparitors = new ArrayList<String>();
    
    for( String comparitor : original.getComparitors())
    {
      _comparitors.add(comparitor);
    }
    
    _creationUTC = original.getCreationUTC();
  }
  
  /**
   * Contents accessor.
   * @return the contents of this item
   */
  public Hashtable<String,Object> getContents()
  {
    return _contents;
  }
  
  /**
   * Comparitors accessor.
   * @return the comparitors for this item
   */
  public List<String> getComparitors()
  {
    return _comparitors;
  }
  
  /**
   * Creation UTC accessor.
   * @return the stored creation UTC
   */
  public long getCreationUTC()
  {
    return _creationUTC;
  }
  
  /**
   * Contents aggregator. This method returns the entire textual contents as a list of Strings,
   * deduping the contents depending on the parameter.
   * @param dedupe true if the returned aggregated contents need to be deduped
   * @return
   */
  public List<String> getContents( boolean dedupe )
  {
    ArrayList workingList = new ArrayList<String>();
    
    for( String key : _contents.keySet() )
    {
      Object value = _contents.get(key);
      
      if( value.getClass().getCanonicalName().equals( "java.lang.String"))
      {
        String content = (String)value;
        
        if( dedupe )
        {
          if( !(workingList.contains(content)))
          {
            workingList.add(content);
          }
        }
        else
        {
          workingList.add(content);
        }
      }      
    }
    
    return workingList;
  }
  
  /**
   * Add object to item mutator.
   * @param name name of the object to add
   * @param value the object to add
   * @throws ContentNotUniqueException if the name is not unique within the object
   */
  public void addObject( String name, Object value ) throws ContentNotUniqueException
  {
    if( _contents.containsKey(name))
    {
      throw new ContentNotUniqueException( "Duplicate field addition attempt with name " + name );
    }
    
    _contents.put(name, value);
  }
  
  public void addString( String name, String value, boolean aggregate ) throws ContentNotUniqueException, InvalidObjectTypeException
  {
    if( _contents.containsKey(name) && !aggregate )
    {
      throw new ContentNotUniqueException( "Duplicate string field addition attempt with non-aggregation using name " + name );
    }
    else if( _contents.containsKey(name))
    {
      Object currentObject = _contents.get(name);
      
      if( !currentObject.getClass().getCanonicalName().equals( "java.lang.String"))
      {
        throw new InvalidObjectTypeException( "Expected String, found " + currentObject.getClass().getCanonicalName() + " for field " + name );
      }
            
      String valueToAdd = (String)_contents.get(name);
      
      value = valueToAdd + " " + value;
    }
    
    _contents.put(name,value);
  }
  
  /**
   * Equivalency method. This method returns true *if* the comparitor fields
   * are identical *and* the contents of the comparitor fields in this item match
   * the contents of the comparitor fields in the item to compare.
   * @param comparisonItem an item to compare against this one
   * @return true if the contents indicated by the comparitors are equal
   */
  public boolean isEqual( Item comparisonItem )
  {
    // First, if the comparitors are different then fail at this point
    for( String comparitor : _comparitors )
    {
      if( !( comparisonItem.getComparitors().contains(comparitor)))
      {
        return false;
      }
    }
    
    // Now check the contents of the comparitor fields against the comparisonItem
    for( String comparitor : _comparitors )
    {
      if( !( _contents.get(comparitor).equals(comparisonItem.getContents().get(comparitor))))
      {
        return false;
      }
    }
    
    return true;
  }
}
