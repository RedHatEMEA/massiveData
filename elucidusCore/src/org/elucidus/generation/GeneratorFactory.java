package org.elucidus.generation;

/**
 * This is the static factory for generating Persisters using the interface.
 * @author Ian Lawson (<a href="mailto:ian.lawson@redhat.com">ian.lawson@redhat.com</a>).
 */
public class GeneratorFactory
{
  /**
   * Private constructor.
   */
  private GeneratorFactory()
  {

  }

  @SuppressWarnings("unchecked")
  /**
   * Static factory for generating Generators.
   * @param className class name of Generator to create (must conform to {@link org.elucidus.generation.IGenerator IGenerator} interface. 
   * @return an instantiated object for the Generator
   * @throws ClassCastException if the target class cannot be cast to IGenerator
   * @throws ClassNotFoundException if the target class is not in the ClassLoader classpath
   * @throws InstantiationException if the instantiation fails
   * @throws IllegalAccessException if the class is unreachable within the ClassLoader
   */
  public static  <T extends IGenerator> T getGenerator( String className ) throws ClassCastException,ClassNotFoundException, InstantiationException, IllegalAccessException
  {
    return (T)Class.forName(className).newInstance();
  }
}
