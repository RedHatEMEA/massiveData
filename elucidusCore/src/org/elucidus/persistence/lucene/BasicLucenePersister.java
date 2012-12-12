package org.elucidus.persistence.lucene;

import java.util.List;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.PersistenceException;
import org.elucidus.persistence.IPersister;

public class BasicLucenePersister implements IPersister
{
  private String _luceneIndexDirectory = null;

  @Override
  public void setLocation(String location)
  {
    _luceneIndexDirectory = location;
  }

  @Override
  public void setParameter(String name, String value)
  {

  }

  @Override
  public boolean removeItem(Item item) throws PersistenceException
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean persistItem(Item item, boolean overwrite) throws PersistenceException
  {
    

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
