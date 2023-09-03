package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    User ut;

    @BeforeEach
    void runBefore() {
        ut = new User("x", "W", 10, 1.7, 55, 1, 0);

    }

    @Test
    void testAll() {
        assertEquals("x",ut.getName());

        assertEquals("W",ut.getSex());

        assertEquals(10,ut.getAge());

        assertEquals(1.7,ut.getHeight());

        assertEquals(55,ut.getWeight());

        assertEquals(1,ut.getExercisedPerWeek());

        assertEquals(0,ut.getId());
    }
}
