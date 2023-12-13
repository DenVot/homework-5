package org.homework.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductEntityTest {
  private ProductEntity product;

  @BeforeEach
  public void setUp() {
    product = new ProductEntity("Apple", 5, "kg");
  }

  @Test
  public void testGetName() {
    String expected = "Apple";
    String actual = product.getName();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetAmount() {
    int expected = 5;
    int actual = product.getAmount();
    assertEquals(expected, actual);
  }

  @Test
  public void testSetAmount() {
    int expected = 10;
    product.setAmount(10);
    int actual = product.getAmount();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetMeasure() {
    String expected = "kg";
    String actual = product.getMeasure();
    assertEquals(expected, actual);
  }
}