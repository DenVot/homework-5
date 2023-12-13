package org.homework.services;

import org.homework.exceptions.ProductNotFoundException;

public interface ProductServiceBase {
  void increaseProduct(String name, int amount) throws ProductNotFoundException;

  void decreaseProduct(String name, int amount) throws ProductNotFoundException;
}
