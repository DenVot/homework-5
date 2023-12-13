package org.homework.reader;

import org.homework.entities.ProductEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigReader {
  public static ArrayList<ProductEntity> readFromJsonFile(String fileName) throws IOException, ParseException {
    ArrayList<ProductEntity> result = new ArrayList<>();
    JSONParser parser = new JSONParser();

    try (var file = new FileReader(fileName)) {
      JSONArray array = (JSONArray) parser.parse(file);

      for (Object object : array) {
        JSONObject token = (JSONObject) object;

        String name = (String) token.get("name");

        Long amount = (Long) token.get("amount");

        String measure = (String) token.get("measure");

        ProductEntity product = new ProductEntity(name, amount.intValue(), measure);
        result.add(product);
      }
    }
    return result;
  }
}
