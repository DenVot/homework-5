package org.homework.repositories;

import org.homework.entities.ProductEntity;
import org.homework.exceptions.ProductNotFoundException;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {
  private final Map<String, ProductEntity> products;

  public InMemoryProductRepository(Iterable<ProductEntity> productsArr) {
    products = new HashMap<>();

    for (var product : productsArr) {
      products.put(product.getName(), product);
    }
  }

  @Override
  public void increaseProduct(String name, int amount) throws ProductNotFoundException {
    if (amount <= 0) {
      throw new InvalidParameterException();
    }

    changeOnDeltaProduct(name, amount);
  }

  @Override
  public void decreaseProduct(String name, int amount) throws ProductNotFoundException {
    if (amount <= 0) {
      throw new InvalidParameterException();
    }

    changeOnDeltaProduct(name, -amount);
  }

  private void changeOnDeltaProduct(String name, int delta) throws ProductNotFoundException {
    if (!products.containsKey(name)) {
      throw new ProductNotFoundException();
    }

    var product = products.get(name);
    var newAmount = products.get(name).getAmount() + delta;

    product.setAmount(newAmount);
  }
}
