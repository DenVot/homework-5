package org.homework.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.homework.entities.Cart;
import org.homework.exceptions.NegativeProductCountException;
import org.homework.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {
  private Cart cart;

  @BeforeEach
  public void setUp() {
    Map<String, Integer> products = new HashMap<>();
    products.put("Apple", 3);
    products.put("Banana", 2);
    cart = new Cart(products);
  }

  @Test
  public void testGetProducts() {
    Map<String, Integer> products = cart.getProducts();
    assertNotNull(products);
    assertEquals(3, products.get("Apple"));
    assertEquals(2, products.get("Banana"));
  }

  @Test
  public void testSetValid() {
    cart.set("Orange", 4);

    Map<String, Integer> products = cart.getProducts();
    assertEquals(4, products.get("Orange"));
  }

  @Test
  public void testSetInvalidAmount() {
    assertThrows(InvalidParameterException.class, () -> {
      cart.set("Orange", -1);
    });
  }

  @Test
  public void testIncreaseValid() throws ProductNotFoundException {
    cart.increase("Apple", 2);

    Map<String, Integer> products = cart.getProducts();
    assertEquals(5, products.get("Apple"));
  }

  @Test
  public void testIncreaseInvalidAmount() {
    assertThrows(InvalidParameterException.class, () -> {
      cart.increase("Apple", -1);
    });
  }

  @Test
  public void testIncreaseProductNotFound() {
    assertThrows(ProductNotFoundException.class, () -> {
      cart.increase("Orange", 2);
    });
  }

  @Test
  public void testDecreaseValid() throws ProductNotFoundException, NegativeProductCountException {
    cart.decrease("Apple", 1);

    Map<String, Integer> products = cart.getProducts();
    assertEquals(2, products.get("Apple"));
  }

  @Test
  public void testDecreaseInvalidAmount() {
    assertThrows(InvalidParameterException.class, () -> {
      cart.decrease("Apple", -1);
    });
  }

  @Test
  public void testDecreaseProductNotFound() {
    assertThrows(ProductNotFoundException.class, () -> {
      cart.decrease("Orange", 1);
    });
  }

  @Test
  public void testFlush() {
    cart.flush();

    Map<String, Integer> products = cart.getProducts();
    assertEquals(0, products.get("Apple"));
    assertEquals(0, products.get("Banana"));
  }
}
