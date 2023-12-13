package org.homework.entities;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

public class Cart {
  private final Map<String, Integer> products;

  public Cart(Map<String, Integer> products) {
    this.products = products;
  }

  public void set(String name, int amount) {
    throw new NotImplementedException();
  }

  public void increase(String name, int amount) {
    throw new NotImplementedException();
  }

  public void decrease(String name, int amount) {
    throw new NotImplementedException();
  }

  public void flush() {
    throw new NotImplementedException();
  }
}
