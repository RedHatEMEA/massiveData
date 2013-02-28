package org.druid.generation.basic;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import org.druid.currency.Item;
import org.druid.exceptions.GenerationException;
import org.druid.generation.Generator;
import org.druid.generation.IGenerator;

public class BasicTextGenerator extends Generator implements IGenerator
{

  @Override
  public List<Item> generate(InputStream inputStream, String source ) throws GenerationException
  {
    StringBuffer contents = new StringBuffer();
    
    int content = 0;
    
    try
    {
      while((content = inputStream.read()) != -1 )
      {
        contents.append((char)content);
      }
      
      return this.generate( contents.toString(), source );
    }
    catch( Exception exc )
    {
      exc.printStackTrace();

      throw new GenerationException( "Failed to generate from InputStream in BasicTextGenerator due to " + exc.getMessage());      
    }
  }

  @Override
  public List<Item> generate(File inputFile, String source ) throws GenerationException
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Item> generate(String inputString, String source ) throws GenerationException
  {
    List<Item> items = new Vector<Item>();
    
    Item item = new Item();
    
    item = this.setMandatoryAspects(item, source);
    
    items.add(item);
    
    return items;
  }

  @Override
  public List<Item> generate(URL url, String source ) throws GenerationException
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setParameter(String name, String value)
      throws GenerationException
  {
    // TODO Auto-generated method stub

  }

}
