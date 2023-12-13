package org.homework.services;

import org.homework.entities.User;

public interface UserServiceBase {
  User getUser(int id);
  User createUser();
  void setCartForUser(int userId, String name, int amount);
  void increaseCartForUser(int userId, String name, int amount);
  void decreaseCartForUser(int userId, String name, int amount);
  void flushCartForUser(int userId);
}
