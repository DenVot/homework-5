package org.homework.services;

import org.homework.exceptions.NegativeProductCountException;
import org.homework.exceptions.ProductNotFoundException;

public interface ProductServiceBase {
  void increaseProduct(String name, int amount) throws ProductNotFoundException, NegativeProductCountException;

  void decreaseProduct(String name, int amount) throws ProductNotFoundException, NegativeProductCountException;
}
