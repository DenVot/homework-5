package org.homework.repositories;

import org.homework.entities.User;
import org.homework.exceptions.UserNotFoundException;

public interface UserRepository {
  User getUser(int id) throws UserNotFoundException;

  User createUser();
}
