package org.homework.services;

import org.homework.entities.Cart;
import org.homework.entities.User;
import org.homework.exceptions.NegativeProductCountException;
import org.homework.exceptions.ProductNotFoundException;
import org.homework.exceptions.UserNotFoundException;

public interface UserServiceBase {
  User getUser(int id) throws UserNotFoundException;

  User createUser();

  void setCartForUser(int userId, String name, int amount) throws UserNotFoundException;

  void increaseCartForUser(int userId, String name, int amount) throws UserNotFoundException, ProductNotFoundException;

  void decreaseCartForUser(int userId, String name, int amount) throws UserNotFoundException, ProductNotFoundException, NegativeProductCountException;

  void flushCartForUser(int userId) throws UserNotFoundException;

  Cart buy(int userId) throws UserNotFoundException, ProductNotFoundException, NegativeProductCountException;
}
