package org.elucidus.generation;

import org.elucidus.currency.Item;
import org.elucidus.currency.StandardAspects;

public abstract class Generator implements IGenerator
{
  protected Item setMandatoryAspects( Item item, String source )
  {
    item.addString(StandardAspects.GENERATOR, this.getClass().getCanonicalName());
    item.addString(StandardAspects.SOURCE, source );
    item.addString(StandardAspects.CREATED, Long.toString(item.getCreationUTC()));
    
    return item;
  }
}
