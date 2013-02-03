package org.elucidus.surfacing.basicFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.SurfacingException;
import org.elucidus.surfacing.ISurfacer;

public class BasicFileSurfacer implements ISurfacer
{
  private String _location = null;
  private List<String> _fileList = null;
  private List<String> _dataDictionary = null;
  
  private void cacheFileList() throws SurfacingException
  {
    _fileList = new ArrayList<String>();
    
    // Given a single directory build a list of .elu files. This is a snapshot 
    // and is effectively temporally stamped
    File location = new File( _location );
    
    // Basic checks
    if( !location.exists())
    {
      throw new SurfacingException( "Target host does not exist." );
    }
    
    if( !location.isDirectory())
    {
      throw new SurfacingException( "Target host is not a directory." );
    }
    
    File[] files = location.listFiles();
    
    for( File file : files )
    {
      if( file.exists() && !file.isDirectory())
      {
        try
        {
          if( file.getCanonicalPath().endsWith(".elu"))
          {
            _fileList.add(file.getCanonicalPath());
          }
        }
        catch( Exception exc )
        {
          // TODO Add logging
          System.out.println( "Failed to get canonical name for file." );
        }
      }
    }
  }
  
  /**
   * Private readFile method for extracting and rebuilding an Item from a file.
   * @param target target file to convert back into an item
   * @return the converted item
   * @throws SurfacingException if the conversion process fails
   */
  private Item readFile( String target ) throws SurfacingException
  {
    // Read and quickly convert the contents
    try
    {   
      Scanner fileRead = new Scanner( new File( target ) );
      
      // Order - Creation UTC then all contents, all delimited by ":::"
      String data = fileRead.next();
      
      return null;
    }
    catch( Exception exc )
    {
      throw new SurfacingException( "Unable to read file due to " + exc.toString() );
    }
  }
  
  /**
   * Cache Data Dictionary 
   * @throws SurfacingException
   */
  private void cacheDataDictionary() throws SurfacingException
  {
    if( _fileList == null )
    {
      this.cacheFileList();      
    }
  }
  
  @Override
  /**
   * This overridden method sets the host directory for persisted files (the data cache)
   * @param location directory to store the persisted files in 
   */
  public void setLocation(String location)
  {
    _location = location;    
  }

  @Override
  public void setParameter(String name, String value)
  {
    
  }

  @Override
  public List<Item> surface(String name, String value, boolean exactMatch) throws SurfacingException
  {
    // If we haven't cached the files, build the cache now
    if( _fileList == null )
    {
      this.cacheFileList();
    }

    return null;
  }

  @Override
  public List<Item> surface(Map<String, String> tokens)
      throws SurfacingException
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean match(Item item, String cacheName) throws SurfacingException
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<String> surfaceDataDictionary() throws SurfacingException
  {
    // TODO Auto-generated method stub
    return null;
  }
}
