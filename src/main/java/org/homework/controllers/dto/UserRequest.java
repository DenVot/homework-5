package org.homework.controllers.dto;

public class UserRequest {
  public record SetCartForUser(String name, int amount) {}

  public record IncreaseCartForUser(String name, int amount) {}

  public record DecreaseCartForUser(String name, int amount) {}
}
