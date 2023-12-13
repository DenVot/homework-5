package org.homework.services;

import org.homework.entities.Cart;
import org.homework.entities.User;
import org.homework.exceptions.NegativeProductCountException;
import org.homework.exceptions.ProductNotFoundException;
import org.homework.exceptions.UserNotFoundException;
import org.homework.repositories.ProductRepository;
import org.homework.repositories.UserRepository;

public class UserService implements UserServiceBase {
  private final UserRepository userRepository;
  private final ProductRepository productRepository;

  public UserService(UserRepository userRepository, ProductRepository productRepository) {
    this.userRepository = userRepository;
    this.productRepository = productRepository;
  }

  @Override
  public User getUser(int id) throws UserNotFoundException {
    return userRepository.getUser(id);
  }

  @Override
  public User createUser() {
    return userRepository.createUser();
  }

  @Override
  public void setCartForUser(int userId, String name, int amount) throws UserNotFoundException {
    var cart = getCart(userId);
    cart.set(name, amount);
  }

  @Override
  public void increaseCartForUser(int userId, String name, int amount) throws UserNotFoundException, ProductNotFoundException {
    var cart = getCart(userId);
    cart.increase(name, amount);
  }

  @Override
  public void decreaseCartForUser(int userId, String name, int amount)
          throws UserNotFoundException, ProductNotFoundException, NegativeProductCountException {
    var cart = getCart(userId);
    cart.decrease(name, amount);
  }

  @Override
  public void flushCartForUser(int userId) throws UserNotFoundException {
    var cart = getCart(userId);
    cart.flush();
  }

  @Override
  public synchronized void buy(int userId)
          throws UserNotFoundException, ProductNotFoundException, NegativeProductCountException {
    var cart = getCart(userId);
    var prods = cart.getProducts();

    for (String productName : prods.keySet()) {
      productRepository.decreaseProduct(productName, prods.get(productName));
    }

    cart.flush();
  }

  private Cart getCart(int userId) throws UserNotFoundException {
    var user = userRepository.getUser(userId);
    return user.getCart();
  }
}
