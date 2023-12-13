package org.homework.repositories;

import org.homework.entities.ProductEntity;
import org.homework.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProductRepositoryTest {

  private ProductRepository repository;

  @BeforeEach
  void setUp(){
    var product1 = new ProductEntity("Product1", 5, "kg");
    var product2 = new ProductEntity("Product2", 10, "kg");
    List<ProductEntity> products = Arrays.asList(
            product1,
            product2
    );
    repository = new InMemoryProductRepository(products);
  }

  @Test
  void increaseProduct_invalidName() {
    String productName = "InvalidProduct";
    int amount = 3;

    assertThrows(ProductNotFoundException.class, () -> repository.increaseProduct(productName, amount));
  }

  @Test
  void increaseProduct_negativeAmount() {
    String productName = "Product2";
    int amount = -3;

    assertThrows(InvalidParameterException.class, () -> repository.increaseProduct(productName, amount));
  }

  @Test
  void decreaseProduct_validNameAndAmount() {
    var product1 = new ProductEntity("Product1", 5, "kg");
    var product2 = new ProductEntity("Product2", 10, "kg");
    List<ProductEntity> products = Arrays.asList(
            product1,
            product2
    );
    repository = new InMemoryProductRepository(products);
    int amount = 1;
    assertDoesNotThrow(() -> repository.decreaseProduct(product1.getName(), amount));
    assertEquals(4, product1.getAmount());
  }


  @Test
  void decreaseProduct_invalidName() {
    String productName = "InvalidProduct";
    int amount = 1;

    assertThrows(ProductNotFoundException.class, () -> repository.decreaseProduct(productName, amount));
  }

  @Test
  void increaseProduct_validNameAndAmount() {
    var product1 = new ProductEntity("Product1", 5, "kg");
    var product2 = new ProductEntity("Product2", 10, "kg");
    List<ProductEntity> products = Arrays.asList(
            product1,
            product2
    );
    repository = new InMemoryProductRepository(products);
    int amount = 3;

    assertDoesNotThrow(() -> repository.increaseProduct(product1.getName(), amount));
    assertEquals(8, product1.getAmount());
  }
}