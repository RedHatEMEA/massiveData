package org.elucidus.exceptions;

/**
 * This class provides an exception for when a data format conversion failure occurs.
 * @author Ian Lawson (Logica Uk) <a href="mailto:ian.lawson@logica.com">ian.lawson@logica.com</a>
 */
public class ContentNotUniqueException extends Exception
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Standard no parameter constructor - calls the super() method from {@link java.lang.Exception java.lang.Exception}.
   */
  public ContentNotUniqueException()
  {
    super();
  }

  /**
   * Standard message parameter constructor - calls the super() method from {@link java.lang.Exception java.lang.Exception}.
   * @param message message to add to the exception object
   */
  public ContentNotUniqueException( String message )
  {
    super( message );
  }

}