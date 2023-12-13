package org.homework.repositories;

import org.homework.entities.Cart;
import org.homework.entities.ProductEntity;
import org.homework.entities.User;
import org.homework.exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryUserRepository implements UserRepository {
  private final Iterable<ProductEntity> products;
  private final ConcurrentMap<Integer, User> users;
  private static final AtomicInteger nextId = new AtomicInteger(0);

  public InMemoryUserRepository(Iterable<ProductEntity> products) {
    this.products = products;
    users = new ConcurrentHashMap<>();
  }

  @Override
  public User getUser(int id) throws UserNotFoundException {
    if (!users.containsKey(id)) {
      throw new UserNotFoundException();
    }

    return users.get(id);
  }

  @Override
  public User createUser() {
    var cart = new Cart(generateProductsDeltasTable());
    var user = new User(nextId.getAndIncrement(), cart);

    users.put(user.getId(), user);

    return user;
  }

  private Map<String, Integer> generateProductsDeltasTable() {
    var map = new HashMap<String, Integer>();

    for (ProductEntity product : products) {
      map.put(product.getName(), 0);
    }

    return map;
  }
}
