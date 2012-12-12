package org.elucidus.persistence;

public class PersisterFactory
{
  /**
   * Private constructor.
   */
  private PersisterFactory()
  {

  }

  @SuppressWarnings("unchecked")
  public static  <T extends IPersister> T getPersister( String className ) throws ClassCastException,ClassNotFoundException, InstantiationException, IllegalAccessException
  {
    return (T)Class.forName(className).newInstance();
  }
}
