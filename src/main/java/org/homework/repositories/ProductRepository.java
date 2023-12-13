package org.homework.repositories;

import org.homework.exceptions.ProductNotFoundException;

public interface ProductRepository {
  void increaseProduct(String name, int amount) throws ProductNotFoundException;
  void decreaseProduct(String name, int amount) throws ProductNotFoundException;
}
