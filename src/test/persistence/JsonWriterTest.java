package persistence;

import model.ListOfUser;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    User ut;
    User u1;

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfUser listOfUser = new ListOfUser();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyListOfUser() {
        try {
            ListOfUser listOfUser = new ListOfUser();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyListOfUser.json");
            writer.open();
            writer.write(listOfUser);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyListOfUser.json");
            listOfUser = reader.read();
            assertEquals(0, listOfUser.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralListOfUser() {
        ut = new User("x", "W", 10, 1.7, 55, 1, 0);
        u1 = new User("w", "W", 13, 1.6, 50, 1, 1);

        try {
            ListOfUser listOfUser = new ListOfUser();
            ListOfUser l1 = new ListOfUser();
            listOfUser.addUser(ut);
            listOfUser.addUser(u1);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralListOfUser.json");
            writer.open();
            writer.write(listOfUser);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralListOfUser.json");
            listOfUser = reader.read();
            assertEquals(2,listOfUser.length());
            checkUser("x", "W", 10, 1.7, 55, 1, 0,
                    listOfUser.getListOfUser(0));
            checkUser("w", "W", 13, 1.6, 50, 1, 1,
                    listOfUser.getListOfUser(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
