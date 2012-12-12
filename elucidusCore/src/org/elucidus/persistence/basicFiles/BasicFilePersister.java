package org.elucidus.persistence.basicFiles;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.PersistenceException;
import org.elucidus.persistence.IPersister;

/**
 * Persister for storing the Items in Basic Files.
 * @author Ian Lawson (<a href="mailto:ian.lawson@redhat.com">ian.lawson@redhat.com</a>).
 *
 */
public class BasicFilePersister implements IPersister
{
  private File _targetDirectory = null;
  private String _uuid = null;

  @Override
  /**
   * Implementation of the set location method. This stores the physical location of the file to use.
   */
  public void setLocation(String location) throws PersistenceException
  {
    _targetDirectory = new File( location );
      
    if( !( _targetDirectory.exists() ) )
    {
      throw new PersistenceException( "No such target directory in BasicFilePersister " + location );
    }

    if( !( _targetDirectory.isDirectory() ) )
    {
      throw new PersistenceException( "Target directory is not a directory in BasicFilePersister " + location );
    }
  }

  /**
   * Implementation of the set parameter method. In the case of the Basic File Persister this is the
   * uuid to be used for the item/file.
   * @param name Parameter name to store.
   * @param value uuid to store.
   * @throws PersistenceException is the parameter name is not uuid
   */
  @Override
  public void setParameter(String name, String value) throws PersistenceException
  {
    if( !( name.equalsIgnoreCase( "uuid" ) ) )
    {
      throw new PersistenceException( "Unknown parameter for BasicFilePersister " + name );
    }
    
    _uuid = value;
  }

  /**
   * Implementation of the remove item method. In the case of the Basic File Persister this
   * deletes the physical file.
   * @param item item to remove. This is associated to the uuid which is set separately.
   * @return true if the file is removed correctly
   * @throws PersistenceException not thrown but inherited via interface
   */
  @Override
  public boolean removeItem(Item item) throws PersistenceException
  {
    String targetLocation = _targetDirectory + File.pathSeparator + _uuid + ".elu";
    
    File targetFile = new File( targetLocation );
    

    return targetFile.delete();
  }

  /**
   * 
   */
  @Override
  public boolean persistItem(Item item, boolean overwrite)
      throws PersistenceException
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<Item> persistItems(List<Item> items, boolean overwrite)
      throws PersistenceException
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean contains(Item item) throws PersistenceException
  {
    // TODO Auto-generated method stub
    return false;
  }

}
