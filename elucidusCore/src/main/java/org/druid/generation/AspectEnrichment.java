package org.druid.generation;

import java.io.File;

import org.druid.currency.Item;
import org.druid.currency.StandardAspects;

/**
 * Static class containing Aspect enrichment methods for specific aspect types.
 * This allows for, for instance, Aspect enhancement for file based sources, URL sources
 * etc.
 * @author Ian Lawson (<a href="mailto:ian.lawson@redhat.com">ian.lawson@redhat.com</a>).
 *
 */
public final class AspectEnrichment
{
  // Private constructor.
  private AspectEnrichment()
  {
    
  }

  public static Item fileEnrichment( Item input, File targetFile )
  {
    // Add Aspects for file specific information.
    try
    {
      input.addString(StandardAspects.FILE_FILENAME, targetFile.getCanonicalPath());
      input.addString(StandardAspects.FILE_SIZE_BYTES, Long.toString(targetFile.getTotalSpace()));
      input.addString(StandardAspects.FILE_MODIFIED_UTC, Long.toString(targetFile.lastModified()));
    }
    catch( Exception exc )
    {
      input.addString(StandardAspects.FILE_FILENAME, "Invalid File");
    }
    
    return input;
  }
}
