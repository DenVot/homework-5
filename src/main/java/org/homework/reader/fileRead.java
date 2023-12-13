package org.homework.reader;

import org.homework.entities.ProductEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class fileRead {
  public static ArrayList<ProductEntity> readFromJsonFile(String fileName) throws ParseException, IOException {
    ArrayList<ProductEntity> result = new ArrayList<>();
    JSONParser parser = new JSONParser();

    try {
      JSONArray array = (JSONArray) parser.parse(new FileReader(fileName));
    //расположение нашего файла: "src/main/java/org/homework/reader/inputFile.json"

      for (Object object : array) {
        JSONObject person = (JSONObject) object;

        String name = (String) person.get("name");
        System.out.println(name);

        String amount = (String) person.get("amount");
        System.out.println(amount);

        String measure = (String) person.get("measure");
        System.out.println(measure);

        ProductEntity product = new ProductEntity(name, Integer.parseInt(amount), measure);
        result.add(product);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return result;
  }
}
