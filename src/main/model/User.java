package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents the user which has name, sex, age, height, weight, exercised time per ween and id.
public class User implements Writable {
    private String name;
    private String sex; //W for Woman, M for Man
    private int age;
    private double height;
    private double weight;
    private int exercisedPerWeek;
    private int id;


    //private User user;

    //MODIFY: this.
    //EFFECTS: construct a user
    public User(String name, String sex, int age, double height, double weight, int exercisedPerWeek, int id) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.exercisedPerWeek = exercisedPerWeek;
        this.id = id;
//        if (age <= 0 || height <= 0 || weight <= 0) {
//            throw new WrongNumberException();
//        }
    }

    //EFFECTS: return user's name
    public String getName() {
        return name;
    }

    //EFFECTS: return user's name
    public String getSex() {
        return sex;
    }

    //EFFECTS: return user's age
    public int getAge() {
        return age;
    }

    //EFFECTS: return user's height
    public double getHeight() {
        return height;
    }

    //EFFECTS: return user's weight
    public double getWeight() {
        return weight;
    }

    //EFFECTS: return user's isExercised;
    public int getExercisedPerWeek() {
        return exercisedPerWeek;
    }

    //EFFECTS: return the date when the user created
    //public int getDate() {
    //    return date;
    //}

    //EFFECTS: return user's id
    public int getId() {
        return id;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("Sex", sex);
        json.put("Age", age);
        json.put("Height", height);
        json.put("Weight", weight);
        json.put("ExercisedPerWeek", exercisedPerWeek);
        json.put("Id", id);
        return json;
    }

}
