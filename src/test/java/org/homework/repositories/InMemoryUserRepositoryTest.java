package org.homework.repositories;

import org.homework.entities.ProductEntity;
import org.homework.entities.User;
import org.homework.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryUserRepositoryTest {

  private InMemoryUserRepository userRepository;

  @BeforeEach
  public void setUp() {
    List<ProductEntity> products = new ArrayList<>();
    products.add(new ProductEntity("product1", 10, "kg"));
    products.add(new ProductEntity("product2", 20, "kg"));
    products.add(new ProductEntity("product3", 30, "kg"));
    userRepository = new InMemoryUserRepository(products);
  }
  @Test
  public void testCreateUser() {
    User user = userRepository.createUser();
    User user1 = userRepository.createUser();
    assertNotNull(user);
    assertNotNull(user1);
    assertEquals(0, user.getId());
    assertEquals(1, user1.getId());
  }

  @Test
  public void testGetUse() {
    User createdUser = userRepository.createUser();
    try {
      User retrievedUser = userRepository.getUser(createdUser.getId());
      assertNotNull(retrievedUser);
      assertEquals(createdUser.getId(), retrievedUser.getId());
    } catch (UserNotFoundException e) {
      fail("User not found");
    }
  }

  @Test
  public void testGetUserThrowsUserNotFoundException() {
    assertThrows(UserNotFoundException.class,() -> {
      userRepository.getUser(100);
    });
  }
}