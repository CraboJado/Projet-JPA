package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class InitMysql {
    public static void main(String[] args) {
        JsonParser parser = new JsonParser();
        try {
            JsonArray films = (JsonArray) parser.parse(new FileReader("films.json"));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
