package persistence;

import model.ListOfUser;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ListOfUser from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfUser read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfUser(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ListOfUser from JSON object and returns it
    private ListOfUser parseListOfUser(JSONObject jsonObject) {
        ListOfUser listOfUser = new ListOfUser();
        addListOfUser(listOfUser, jsonObject);
        return listOfUser;
    }

    // MODIFIES: listofuser
    // EFFECTS: parses user from JSON object and adds them to listofuser
    private void addListOfUser(ListOfUser listOfUser, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("ListOfUser");
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addUser(listOfUser, nextUser);
        }
    }

    // MODIFIES: listofuser
    // EFFECTS: parses user from JSON object and adds it to listofuser
    private void addUser(ListOfUser listOfUser, JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        String sex = jsonObject.getString("Sex");
        int age = jsonObject.getInt("Age");
        double height = jsonObject.getDouble("Height");
        double weight = jsonObject.getDouble("Weight");
        int exercisedPerWeek = jsonObject.getInt("ExercisedPerWeek");
        int id = jsonObject.getInt("Id");

        User user = new User(name,sex,age,height,weight,exercisedPerWeek,id);
        listOfUser.addUser(user);

    }
}
