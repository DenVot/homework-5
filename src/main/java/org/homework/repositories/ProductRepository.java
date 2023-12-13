package org.homework.repositories;

public interface ProductRepository {
  void increaseProduct(String name, int amount);
  void decreaseProduct(String name, int amount);
}
