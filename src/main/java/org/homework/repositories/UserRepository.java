package org.homework.repositories;

import org.homework.entities.User;

public interface UserRepository {
  User getUser(int id);
  User createUser();
}
