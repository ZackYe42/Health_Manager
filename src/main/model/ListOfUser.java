package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list include user
public class ListOfUser implements Writable {
    public static final int MAX_SIZE = 10;


    private ArrayList<User> listOfUser;

    public ListOfUser() {
        this.listOfUser = new ArrayList<>();
    }

    //MODIFY:    this
    //EFFECTS:   if the list is not full, adds it to the end of the queue and returns true.
    //           If the list is full, the method returns false.
    public boolean addUser(User user) {
        if (isFull()) {
            EventLog.getInstance().logEvent(new Event("Add user Fail, system full"));
            return false;
        } else {
            listOfUser.add(user);
            EventLog.getInstance().logEvent(new Event("Add user with ID: " + user.getId() + " Success"));
            return true;
        }
    }

    //EFFECTS: if the list contain user with this id, remove it and return true
    //         if not contain, return false
    public boolean removeUserOfID(int id) {
        //EFFECTS: find the user by id and remove
        for (int i = 0; i < listOfUser.size(); i++) {
            if (id == listOfUser.get(i).getId()
            ) {
                listOfUser.remove(listOfUser.get(i));
                EventLog.getInstance().logEvent(new Event("Remove user with ID: " + id + " Success"));
                return true;
            }
        }
        EventLog.getInstance().logEvent(new Event("Remove User with ID: " + id + " Fail"));
        return false;
    }

    //EFFECTS: return the id with name and age
    //         return -1 if it does not found.
    public int findIdOfNameAndAge(String name, int age) {
        for (User user : listOfUser) {
            if (user.getName().equals(name) && age == user.getAge()
            ) {
                EventLog.getInstance().logEvent(new Event("Find user with name: " + name + " Success"));
                return user.getId();
            }
        }
        EventLog.getInstance().logEvent(new Event("Find user with name: " + name + " Fail"));
        return -7355608;
    }

    //EFFECTS: return user's BMI with id
    public int getBmiOfID(int id) {
        for (int i = 0; i < listOfUser.size(); i++) {
            if (id == listOfUser.get(i).getId()
            ) {
                EventLog.getInstance().logEvent(new
                        Event("Calculate User with ID: " + id + " BMI is " + bmiCalculator(listOfUser.get(i))));
                return bmiCalculator(listOfUser.get(i));
            }
        }
        EventLog.getInstance().logEvent(new Event("Calculate User with ID: " + id + " BMI Fail"));
        return -1;

    }

    //REQUIRES: user's weight and height not negative
    //EFFECTS: return user's BMI
    public int bmiCalculator(User user) {
        return (int) (user.getWeight() / (user.getHeight() * user.getHeight()));
    }



    //EFFECTS: return user's BMR with id, if sex is wrong, return negative value
    public int getBmrOfID(int id) {
        for (int i = 0; i < listOfUser.size(); i++) {
            if (id == listOfUser.get(i).getId()
            ) {
                EventLog.getInstance().logEvent(new
                        Event("Calculate User with ID: " + id + " BMR is " + bmrCalculator(listOfUser.get(i))));
                return bmrCalculator(listOfUser.get(i));
            }
        }
        EventLog.getInstance().logEvent(new Event("Calculate User with ID: " + id + " BMR Fail"));
        return -1;
    }


    //REQUIRES: user's weight, height, exercisedPerWeek are not negative
    //EFFECTS: return user's BMR, if sex is wrong, return negative value
    public int bmrCalculator(User user) {
        int bmr;
        if (user.getSex().equals("W")) {
            bmr = (int) (655 + 9.6 * user.getWeight() + 1.8 * user.getHeight() - 4.7 * user.getAge());
        } else if (user.getSex().equals("M")) {
            bmr = (int) (66 + 13.7 * user.getWeight() + 5 * user.getHeight() - 6.8 * user.getAge());
        } else {
            bmr = -1;
        }

        bmr = (int) bmrHelper(user, bmr);
        return bmr;
    }

    //EFFECTS: help bmr calculator
    public double bmrHelper(User user,int i) {
        double i0 = 1.0;


        if (user.getExercisedPerWeek() > 7) {
            i0 = (i * 1.9);
        } else if ((5 <= user.getExercisedPerWeek())) {
            i0 = (i * 1.725);
        } else if ((3 <= user.getExercisedPerWeek())) {
            i0 = (i * 1.55);
        } else if ((1 <= user.getExercisedPerWeek())) {
            i0 = (i * 1.375);
        } else  {
            i0 = (i * 1.2);
        }
        return i0;
    }

    //EFFECTS: return weather the id be used
    public boolean idIsUsed(int id) {
        for (int i = 0; i < listOfUser.size(); i++) {
            if (id == listOfUser.get(i).getId()
            ) {
                EventLog.getInstance().logEvent(new Event("Id " + id + " exist in System"));
                return true;
            }
        }
        EventLog.getInstance().logEvent(new Event("Id " + id + " Not exist in System"));
        return false;
    }

    //EFFECTS: return user
    public User getListOfUser(int i) {
        return listOfUser.get(i);
    }



    //EFFECTS: returns an int that represents the number of incidents currently in the queue.
    public int length() {
        return listOfUser.size();
    }

    public boolean isEmpty() {
        return listOfUser.isEmpty();
    }


    ///EFFECTS: returns a boolean:true if the queue is full, false otherwise
    public boolean isFull() {
        return MAX_SIZE <= length();
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ListOfUser",listOfUserToJson());
        return json;
    }

    //EFFECTS: returns ListOfUser as a JSON array
    private JSONArray listOfUserToJson() {
        JSONArray jsonArray = new JSONArray();

        for (User user : listOfUser) {
            jsonArray.put(user.toJson());
        }

        return jsonArray;
    }
}
