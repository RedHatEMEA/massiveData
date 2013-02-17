package org.elucidus.hashing;

import org.elucidus.exceptions.HashingException;

public interface IHash
{
  public String hash( String input ) throws HashingException;
  public byte[] hash( byte[] input ) throws HashingException;
}
