package org.elucidus.generation;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.GenerationException;

/**
 * This is the interface for the Elucidus generators. Generators work by taking raw data streams
 * and generating correctly formatted Items.
 * @author Ian Lawson <a href="mailto:ian.lawson@redhat.com">ian.lawson@redhat.com</a>
 */
public interface IGenerator
{
  public List<Item> generate( InputStream inputStream, String source ) throws GenerationException;
  public List<Item> generate( File inputFile, String source ) throws GenerationException;
  public List<Item> generate( String inputString, String source ) throws GenerationException;
  public List<Item> generate( URL url, String source ) throws GenerationException;
  public void setParameter( String name, String value ) throws GenerationException;
}
