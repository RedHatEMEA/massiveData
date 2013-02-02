package org.elucidus.surfacing;

import java.util.List;
import java.util.Map;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.SurfacingException;

public interface ISurfacer
{
  public void setLocation( String location );
  public void setParameter( String name, String value );
  public List<Item> surface( String name, String value, boolean exactMatch ) throws SurfacingException;
  public List<Item> surface( Map<String,String> tokens ) throws SurfacingException;
  public List<String> surfaceDataDictionary() throws SurfacingException;
  public boolean match( Item item, String cacheName ) throws SurfacingException;  
}
