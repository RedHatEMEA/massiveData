package org.elucidus.exceptions;

/**
 * This class provides an exception for when a data format conversion failure occurs.
 * @author Ian Lawson (<a href="mailto:ian.lawson@redhat.com">ian.lawson@redhat.com</a>).
 */
public class SurfacingException extends Exception
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Standard no parameter constructor - calls the super() method from {@link java.lang.Exception java.lang.Exception}.
   */
  public SurfacingException()
  {
    super();
  }

  /**
   * Standard message parameter constructor - calls the super() method from {@link java.lang.Exception java.lang.Exception}.
   * @param message message to add to the exception object
   */
  public SurfacingException( String message )
  {
    super( message );
  }

}