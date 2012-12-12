package org.elucidus.persistence.lucene;

import java.util.List;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.PersistenceException;
import org.elucidus.persistence.IPersister;

public class Persister implements IPersister
{

  @Override
  public void setLocation(String location)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setParameter(String name, String value)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean removeItem(Item item) throws PersistenceException
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean persistItem(Item item, boolean overwrite)
      throws PersistenceException
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean contains(Item item) throws PersistenceException
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
  
}
