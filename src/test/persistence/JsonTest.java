package persistence;

import model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkUser(String name, String sex, int age,
                             double height, double weight, int exercisedPerWeek, int id, User user) {
        assertEquals(name, user.getName());
        assertEquals(sex, user.getSex());
        assertEquals(age, user.getAge());
        assertEquals(height, user.getHeight());
        assertEquals(weight, user.getWeight());
        assertEquals(exercisedPerWeek, user.getExercisedPerWeek());
        assertEquals(id, user.getId());
    }
}
