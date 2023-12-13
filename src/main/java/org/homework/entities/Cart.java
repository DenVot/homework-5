package org.homework.entities;

import org.homework.exceptions.ProductNotFoundException;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Map;

public class Cart {
  private final Map<String, Integer> products;

  public Cart(Map<String, Integer> products) {
    this.products = products;
  }

  public Map<String, Integer> getProducts() {
    return Collections.unmodifiableMap(products);
  }

  public void set(String name, int amount) {
    if (amount <= 0) {
      throw new InvalidParameterException();
    }

    products.put(name, amount);
  }

  public void increase(String name, int amount) throws ProductNotFoundException {
    if (amount <= 0) {
      throw new InvalidParameterException();
    }

    if (!products.containsKey(name)) {
      throw new ProductNotFoundException();
    }

    products.put(name, products.getOrDefault(name, 0) + amount);
  }

  public void decrease(String name, int amount) throws ProductNotFoundException {
    if (amount <= 0) {
      throw new InvalidParameterException();
    }

    if (!products.containsKey(name)) {
      throw new ProductNotFoundException();
    }

    products.put(name, products.getOrDefault(name, 0) - amount);
  }

  public void flush() {
    products.replaceAll((k, v) -> 0);
  }
}
