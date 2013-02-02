package org.elucidus.currency;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

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
  private final Hashtable<String,Object> _contents = new Hashtable<>();
  private final List<String> _comparitors = new Vector<String>(); 
  private long _creationUTC = 0;
  
  /**
   * Parameterless constructor, empty item
   */
  public Item()
  {
    _creationUTC = System.currentTimeMillis();
  }
  
  /**
   * Basic constructor for retaining previous creation UTC (for fully qualifying vanilla Items).
   * @param creationUTC pre-created creation time
   */
  public Item( long creationUTC )
  {
    _creationUTC = creationUTC;
  }
    
  /**
   * Deep copy constructor with provided contents
   * @param contents existing contents for creation of a new item
   * @param comparitors item comparitors for uniqueness checks
   */
  public Item( Hashtable<String,Object> contents, List<String> comparitors )
  {
	this(contents, comparitors, System.currentTimeMillis());
  }
  
  /**
   * Dependency injection constructor with time.
   * @param contents contents to store
   * @param comparitors comparitors to store
   * @param originalCreationUTC original UTC creation time to store
   */
  public Item( Hashtable<String,Object> contents, List<String> comparitors, long originalCreationUTC )
  {
    this._contents.putAll(contents);
    this._comparitors.addAll(comparitors);
    this._creationUTC = originalCreationUTC;
  }
  
  /**
   * Deep item copy constructor.
   * @param original original item to deep copy
   */
  public Item( Item original )
  {
	  this(original.getContents(), original.getComparitors(), original.getCreationUTC());
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
    ArrayList<String> workingList = new ArrayList<String>();
    
    for( String key : _contents.keySet() )
    {
      Object value = _contents.get(key);
       
      if( value.getClass() == String.class)
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
  
  /**
   * Simple add string method for adding an Aspect to the item.
   * @param identifier name to store the aspect under
   * @param value value to store for the aspect
   */
  public void addString(String identifier, String value) 
  {
	_contents.put(identifier, value);
  } 
  
  /**
   * Add String method for aspect aggregation - this allows multiple values (String) to be stored against the same single name aspect.
   * @param name name to store
   * @param value value to store (or aggregate if it already exists *and* the boolean aggregate is set to true
   * @param aggregate whether to aggregate or not. If set to not then a duplicate field exception is thrown
   * @throws ContentNotUniqueException name already exists in Item *and* Aggregate is set to false
   * @throws InvalidObjectTypeException if the name already exists *and* aggregate is set to true but the existing object is not a string
   */
  public void addString( String name, String value, boolean aggregate ) throws ContentNotUniqueException, InvalidObjectTypeException
  {
    if( _contents.containsKey(name) && !aggregate )
    {
      throw new ContentNotUniqueException( "Duplicate string field addition attempt with non-aggregation using name " + name );
    }
    else if( _contents.containsKey(name))
    {
      Object currentObject = _contents.get(name);
      
      if( currentObject.getClass() != String.class)
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
