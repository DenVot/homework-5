package org.homework.services;

import org.homework.exceptions.ProductNotFoundException;
import org.homework.repositories.ProductRepository;

public class ProductService implements ProductServiceBase {
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  private final ProductRepository productRepository;

  @Override
  public void increaseProduct(String name, int amount) throws ProductNotFoundException {
    productRepository.increaseProduct(name, amount);
  }

  @Override
  public void decreaseProduct(String name, int amount) throws ProductNotFoundException {
    productRepository.decreaseProduct(name, amount);
  }
}
