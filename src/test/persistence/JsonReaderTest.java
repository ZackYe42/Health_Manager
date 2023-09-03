package persistence;

import model.ListOfUser;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    User ut;
    User u1;

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListOfUser listOfUser = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyListOfUser.json");

        try {
            ListOfUser listOfUser = reader.read();
            assertEquals(0, listOfUser.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralListOfUser() {

        JsonReader reader = new JsonReader("./data/testReaderGeneralListOfUser.json");
        try {
            ListOfUser listOfUser = reader.read();
            assertEquals(2,listOfUser.length());
            checkUser("x", "W", 10, 1.7, 55, 1, 0,
                    listOfUser.getListOfUser(0));
            checkUser("w", "W", 13, 1.6, 50, 1, 1,
                    listOfUser.getListOfUser(1));


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
