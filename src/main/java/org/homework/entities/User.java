package org.homework.entities;

public class User {
  private final int id;
  private final Cart cart;

  public User(int id, Cart cart) {
    this.id = id;
    this.cart = cart;
  }

  public int getId() {
    return id;
  }

  public Cart getCart() {
    return cart;
  }
}
