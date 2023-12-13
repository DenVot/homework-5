package org.homework.controllers.dto;

import org.homework.entities.Cart;

public class UserResponse {
  public record CreateUser(int id) {}

  public record GetUser(int id, Cart cart) {}

  public record ErrorResponse(String message) {}
}
