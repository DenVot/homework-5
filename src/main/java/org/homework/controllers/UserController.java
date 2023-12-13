package org.homework.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.InvalidParameterException;
import org.eclipse.jetty.http.HttpStatus;
import org.homework.controllers.dto.UserRequest;
import org.homework.controllers.dto.UserResponse;
import org.homework.exceptions.NegativeProductCountException;
import org.homework.exceptions.ProductNotFoundException;
import org.homework.exceptions.UserNotFoundException;
import org.homework.services.UserServiceBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Service;

public class UserController implements Controller{
  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
  private final Service service;
  private final ObjectMapper objectMapper;
  private final UserServiceBase userService;

  public UserController(
      Service service, ObjectMapper objectMapper, UserServiceBase commentService) {
    this.service = service;
    this.objectMapper = objectMapper;
    this.userService = commentService;
  }

  @Override
  public void initializeEndpoints() {
    createUser();
    getUser();
    setCartForUser();
    increaseCartForUser();
    decreaseCartForUser();
    flushCartForUser();
    buyCart();
  }

  private void createUser() {
    service.post(
        "/api/user",
        (Request request, Response response) -> {
          response.type("application/json");

          try {
            var user = userService.createUser();
            response.status(HttpStatus.CREATED_201);
            LOG.debug("Comment successfully added");
            return objectMapper.writeValueAsString(new UserResponse.CreateUser(user.getId()));
          } catch (RuntimeException e) {
            LOG.error("Unhandled error", e);
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Internal server error"));
          }
        });
  }

  private void getUser() {
    service.get(
        "/api/user/:id",
        (Request request, Response response) -> {
          response.type("application/json");
          String id = request.params(":id");

          try {
            var userId = Integer.parseInt(id);
            var user = userService.getUser(userId);

            response.status(HttpStatus.OK_200);
            LOG.debug("Comment successfully added");
            return objectMapper.writeValueAsString(
                new UserResponse.GetUser(user.getId(), user.getCart()));
          } catch (UserNotFoundException e) {
            LOG.warn("Cannot get user", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("User not with id " + id + " not found"));
          } catch (RuntimeException e) {
            LOG.error("Unhandled error", e);
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Internal server error"));
          }
        });
  }

  private void setCartForUser() {
    service.patch(
        "/api/user/set-cart/:id",
        (Request request, Response response) -> {
          response.type("application/json");
          String id = request.params(":id");

          try {
            var userId = Integer.parseInt(id);
            var userRequest =
                objectMapper.readValue(request.body(), UserRequest.SetCartForUser.class);
            userService.setCartForUser(userId, userRequest.name(), userRequest.amount());
            response.status(HttpStatus.NO_CONTENT_204);
          } catch (UserNotFoundException e) {
            LOG.warn("Cannot find user", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("User not with id " + id + " not found"));
          } catch (InvalidParameterException e) {
            LOG.warn("Invalid product amount", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Invalid product amount"));
          } catch (Exception e) {
            LOG.error("Unhandled error", e);
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Internal server error"));
          }

          return "";
        });
  }

  private void increaseCartForUser() {
    service.patch(
        "/api/user/increase-cart/:id",
        (Request request, Response response) -> {
          response.type("application/json");
          String id = request.params(":id");

          try {
            var userId = Integer.parseInt(id);
            var userRequest =
                objectMapper.readValue(request.body(), UserRequest.IncreaseCartForUser.class);
            userService.increaseCartForUser(userId, userRequest.name(), userRequest.amount());
            response.status(HttpStatus.NO_CONTENT_204);
          } catch (UserNotFoundException e) {
            LOG.warn("Cannot find user", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("User not with id " + id + " not found"));
          } catch (ProductNotFoundException e) {
            LOG.warn("Cannot find product", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Product not found"));
          } catch (InvalidParameterException e) {
            LOG.warn("Invalid product amount", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Invalid product amount"));
          } catch (RuntimeException e) {
            LOG.error("Unhandled error", e);
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Internal server error"));
          }
          return "";
        });
  }

  private void decreaseCartForUser() {
    service.patch(
        "/api/user/decrease-cart/:id",
        (Request request, Response response) -> {
          response.type("application/json");
          String id = request.params(":id");

          try {
            var userId = Integer.parseInt(id);
            var userRequest =
                objectMapper.readValue(request.body(), UserRequest.DecreaseCartForUser.class);
            userService.decreaseCartForUser(userId, userRequest.name(), userRequest.amount());
            response.status(HttpStatus.NO_CONTENT_204);
          } catch (UserNotFoundException e) {
            LOG.warn("Cannot find user", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("User not with id " + id + " not found"));
          } catch (ProductNotFoundException e) {
            LOG.warn("Cannot find product", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Product not found"));
          } catch (InvalidParameterException e) {
            LOG.warn("Invalid product amount", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Invalid product amount"));
          } catch (NegativeProductCountException e) {
            LOG.warn("Not enough products in cart", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Not enough products in cart"));
          } catch (RuntimeException e) {
            LOG.error("Unhandled error", e);
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Internal server error"));
          }
          return "";
        });
  }

  private void flushCartForUser() {
    service.patch(
        "/api/user/flush-cart/:id",
        (Request request, Response response) -> {
          response.type("application/json");
          String id = request.params(":id");

          try {
            var userId = Integer.parseInt(id);
            userService.flushCartForUser(userId);
            response.status(HttpStatus.NO_CONTENT_204);
          } catch (UserNotFoundException e) {
            LOG.warn("Cannot find user", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("User not with id " + id + " not found"));
          } catch (RuntimeException e) {
            LOG.error("Unhandled error", e);
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Internal server error"));
          }
          return "";
        });
  }

  private void buyCart() {
    service.patch(
        "/api/user/buy/:id",
        (Request request, Response response) -> {
          response.type("application/json");
          String id = request.params(":id");

          try {
            var userId = Integer.parseInt(id);
            userService.buy(userId);
            response.status(HttpStatus.NO_CONTENT_204);
          } catch (UserNotFoundException e) {
            LOG.warn("Cannot find user", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("User not with id " + id + " not found"));
          } catch (ProductNotFoundException e) {
            LOG.warn("Cannot find product", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Product not found"));
          } catch (NegativeProductCountException e) {
            LOG.warn("Not enough products in the storage", e);
            response.status(HttpStatus.BAD_REQUEST_400);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Not enough products in the storage"));
          } catch (RuntimeException e) {
            LOG.error("Unhandled error", e);
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return objectMapper.writeValueAsString(
                new UserResponse.ErrorResponse("Internal server error"));
          }
          return "";
        });
  }
}
