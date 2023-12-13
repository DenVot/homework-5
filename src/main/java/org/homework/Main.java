package org.homework;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.homework.controllers.UserController;
import org.homework.entities.ProductEntity;
import org.homework.reader.ConfigReader;
import org.homework.repositories.InMemoryProductRepository;
import org.homework.repositories.InMemoryUserRepository;
import org.homework.repositories.ProductRepository;
import org.homework.repositories.UserRepository;
import org.homework.services.UserService;
import org.json.simple.parser.ParseException;
import spark.Service;

public class Main {
  public static void main(String[] args) throws IOException, ParseException {
    var initialStorage = ConfigReader.readFromJsonFile("src/main/resources/storage_config.json");

    Service service = Service.ignite();
    ObjectMapper objectMapper = new ObjectMapper();

    List<ProductEntity> products = new ArrayList<>(initialStorage);

    UserRepository userRepository = new InMemoryUserRepository(products);
    ProductRepository productRepository = new InMemoryProductRepository(products);

    var userService =
        new UserService(userRepository, productRepository);

    Application application =
        new Application(List.of(new UserController(service, objectMapper, userService)));

    application.start();
  }
}
