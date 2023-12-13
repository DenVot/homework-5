package org.homework.entities;

public class ProductEntity {
  private final String name;
  private int amount;
  private final String measure;

  public ProductEntity(String name, int amount, String measure) {
    this.name = name;
    this.amount = amount;
    this.measure = measure;
  }

  public String getName() {
    return name;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public String getMeasure() {
    return measure;
  }
}
