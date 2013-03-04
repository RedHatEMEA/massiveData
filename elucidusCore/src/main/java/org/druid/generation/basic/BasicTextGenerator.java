package org.druid.generation.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import org.druid.currency.Item;
import org.druid.exceptions.GenerationException;
import org.druid.generation.AspectEnrichment;
import org.druid.generation.Generator;
import org.druid.generation.IGenerator;
import org.druid.generation.utils.FileValidation;
import org.druid.utils.TextUtils;

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

      throw new GenerationException( exc );      
    }
  }

  @Override
  public List<Item> generate(File inputFile, String source ) throws GenerationException,IOException
  {
    FileValidation.standardChecks(inputFile);
    
    List<Item> generated = this.generate( new FileInputStream( inputFile), source );
    List<Item> enriched = new Vector<Item>();
    
    for( Item item : generated )
    {
      enriched.add(AspectEnrichment.fileEnrichment(item, inputFile));
    }
    
    return enriched;
  }

  @Override
  public List<Item> generate(String inputString, String source ) throws GenerationException
  {
    List<Item> items = new Vector<Item>();
    
    Item item = new Item();
    
    item = this.setMandatoryAspects(item, source);
    
    // TODO
    // Add use of utils to remove punctuation and tidy textual content
    inputString = TextUtils.clean(inputString);
    
    String[] tokens = inputString.split("[ ]");
    item.addString( "text_token_count", Integer.toString(tokens.length));
    item.addString( "text_content", inputString);
    
    Vector<String> uniqueTokens = new Vector<String>();
    
    for( String token : tokens )
    {
      if( !uniqueTokens.contains(token)) uniqueTokens.add(token);
    }
    
    item.addString("text_unique_token_count", Integer.toString( uniqueTokens.size()));
    
    StringBuffer uniques = null;
    
    for( String token : uniqueTokens )
    {
      if( uniques == null )
      {
        uniques = new StringBuffer( token );
      }
      else
      {
        uniques.append( " " + token );
      }
    }
    
    item.addString( "text_unique_tokens", uniques.toString());
    
    items.add(item);
    
    return items;
  }

  @Override
  public List<Item> generate(URL url, String source ) throws GenerationException
  {
    try
    {
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      
      List<Item> generated = this.generate( connection.getInputStream(), source );
      List<Item> enriched = new Vector<Item>();
      
      for( Item item : generated )
      {
        enriched.add(AspectEnrichment.urlEnrichment(item, connection));
      }
      
      return enriched;
    }
    catch( Exception exc )
    {
      throw new GenerationException( exc );
    }
  }

  @Override
  public void setParameter(String name, String value) throws GenerationException
  {
  }
}
