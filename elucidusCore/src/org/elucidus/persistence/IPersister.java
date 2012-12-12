package org.elucidus.persistence;

import java.util.List;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.PersistenceException;

/**
 * Persister Interface for defining functionality across all persisters.
 * @author Ian Lawson (<a href="mailto:ian.lawson@redhat.com">ian.lawson@redhat.com</a>).
 *
 */
public interface IPersister
{
  public void setLocation( String location ) throws PersistenceException;
  public void setParameter( String name, String value ) throws PersistenceException;
  public boolean removeItem( Item item ) throws PersistenceException;
  public boolean persistItem( Item item, boolean overwrite ) throws PersistenceException;
  public List<Item> persistItems( List<Item> items, boolean overwrite ) throws PersistenceException;
  public boolean contains( Item item ) throws PersistenceException;
}
