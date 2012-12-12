package org.elucidus.persistence.basicFiles;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.PersistenceException;
import org.elucidus.persistence.IPersister;

public class BasicFilePersister implements IPersister
{
  private File _targetDirectory = null;
  private String _uuid = null;

  @Override
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

  @Override
  public void setParameter(String name, String value) throws PersistenceException
  {
    if( !( name.equalsIgnoreCase( "uuid" ) ) )
    {
      throw new PersistenceException( "Unknown parameter for BasicFilePersister " + name );
    }
    
    _uuid = value;
  }

  @Override
  public boolean removeItem(Item item) throws PersistenceException
  {
    String targetLocation = _targetDirectory + File.pathSeparator + _uuid + ".elu";
    
    File targetFile = new File( targetLocation );
    

    return targetFile.delete();
  }

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
