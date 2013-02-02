package org.elucidus.surfacing.basicFiles;

import java.util.List;
import java.util.Map;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.SurfacingException;
import org.elucidus.surfacing.ISurfacer;

public class BasicFileSurfacer implements ISurfacer
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
  public List<Item> surface(String name, String value, boolean exactMatch)
      throws SurfacingException
  {
    // TODO Auto-generated method stub
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
}
