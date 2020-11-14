package config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Resource {
    private static String currentLanguage = "en";

    public static String getString(String key) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("src\\main\\resources\\config\\language.json");
        Object obj = jsonParser.parse(reader);
        JSONObject language = (JSONObject) obj;

        JSONObject language1 = (JSONObject) language.get(currentLanguage);
        return (String) language1.get(key);
    }

    public static void setLanguage(String language){
        currentLanguage = language;
    }
}
