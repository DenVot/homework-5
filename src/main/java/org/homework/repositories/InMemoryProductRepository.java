package org.homework.repositories;

import org.homework.entities.ProductEntity;
import org.homework.exceptions.NegativeProductCountException;
import org.homework.exceptions.ProductNotFoundException;

import java.security.InvalidParameterException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryProductRepository implements ProductRepository {
  private final ConcurrentMap<String, ProductEntity> products;

  public InMemoryProductRepository(Iterable<ProductEntity> productsArr) {
    products = new ConcurrentHashMap<>();

    for (var product : productsArr) {
      products.put(product.getName(), product);
    }
  }

  @Override
  public void increaseProduct(String name, int amount)
          throws ProductNotFoundException, NegativeProductCountException {
    if (amount <= 0) {
      throw new InvalidParameterException();
    }

    changeOnDeltaProduct(name, amount);
  }

  @Override
  public void decreaseProduct(String name, int amount)
          throws ProductNotFoundException, NegativeProductCountException {
    if (amount < 0) {
      throw new InvalidParameterException();
    }

    changeOnDeltaProduct(name, -amount);
  }

  private void changeOnDeltaProduct(String name, int delta)
          throws ProductNotFoundException, NegativeProductCountException {
    if (!products.containsKey(name)) {
      throw new ProductNotFoundException();
    }

    var product = products.get(name);
    var newAmount = products.get(name).getAmount() + delta;

    if (newAmount < 0) {
      throw new NegativeProductCountException();
    }

    product.setAmount(newAmount);
  }
}
