package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfUserTest {
    User u1;

    User u20;
    User u21;
    User u23;
    User u25;
    User u29;

    User u30;
    User u32;
    User u34;
    User u36;
    User u38;

    User u3;
    User ut;

    User uw1;
    User uw2;

    ListOfUser l1;
    ListOfUser l2;
    ListOfUser l3;
    ListOfUser lt;
    ListOfUser l0;
    ListOfUser lt1;


    @BeforeEach
    void runBefore() {
        u1 = new User("w", "W", 13, 1.6, 50, 1, 1);

        u20 = new User("m0", "M", 18, 1.8, 85, 0, 20);
        u21 = new User("m1", "M", 18, 1.8, 85, 1, 21);
        u23 = new User("m3", "M", 18, 1.8, 85, 3, 23);
        u25 = new User("m5", "M", 18, 1.8, 85, 5, 25);
        u29 = new User("m9", "M", 18, 1.8, 85, 9, 29);

        u30 = new User("m0", "M", 18, 1.8, 85, 0, 20);
        u32 = new User("m1", "M", 18, 1.8, 85, 2, 21);
        u34 = new User("m3", "M", 18, 1.8, 85, 4, 23);
        u36 = new User("m5", "M", 18, 1.8, 85, 6, 25);
        u38 = new User("m9", "M", 18, 1.8, 85, 8, 29);

        u3 = new User("wrong", "S", 18, 1.8, 75, 1, 3);

        ut = new User("x", "W", 10, 1.7, 55, 1, 0);

        uw1 = new User("x", "W", 10, 1.7, 55, 0, 0);
        uw2 = new User("x", "W", 10, 1.7, 55, -1, 0);


        l1 = new ListOfUser();
        l1.addUser(u1);

        l2 = new ListOfUser();
        l2.addUser(u20);
        l2.addUser(u21);
        l2.addUser(u23);
        l2.addUser(u25);
        l2.addUser(u29);
        l2.addUser(u3);
        l2.addUser(u30);
        l2.addUser(u32);
        l2.addUser(u34);
        l2.addUser(u36);

        l3 =new ListOfUser();
        l3.addUser(u30);
        l3.addUser(u32);
        l3.addUser(u34);
        l3.addUser(u36);
        l3.addUser(u38);

        lt = new ListOfUser();
        lt.addUser(ut);

        l0 = new ListOfUser();

        lt1 =new ListOfUser();
        lt1.addUser(ut);
        lt1.addUser(u20);


    }

    @Test
    void testAddUser() {
        assertTrue(l1.addUser(u20));
        assertFalse(l2.addUser(u1));
    }

    @Test
    void testRemoveUserOfID(){
        assertTrue(l1.removeUserOfID(1));
        assertTrue(l2.removeUserOfID(20));
        assertTrue(l2.removeUserOfID(21));
        assertFalse(l1.removeUserOfID(2));
        assertFalse(l0.removeUserOfID(1));

        lt1.removeUserOfID(20);
        assertEquals(lt1.getListOfUser(0),lt.getListOfUser(0));
    }

    @Test
    void testFindIdOfNameAndAge() {
        assertEquals(1,l1.findIdOfNameAndAge("w",13));
        assertEquals(20,l2.findIdOfNameAndAge("m0",18));

        assertEquals(-7355608,l2.findIdOfNameAndAge("x",18));
        assertEquals(-7355608,l2.findIdOfNameAndAge("m0",1));
        assertEquals(-7355608,l2.findIdOfNameAndAge("x",1));

        assertEquals(0,lt.findIdOfNameAndAge("x",10));
    }

    @Test
    void testGetBmiOfID() {
        assertEquals(26, l2.getBmiOfID(20));
        assertEquals(19, l1.getBmiOfID(1));
        assertEquals(-1, l2.getBmiOfID(12));

        assertEquals(19,lt.getBmiOfID(0));
    }

    @Test
    void testGetBmrOfID(){
        assertEquals(1479,l1.getBmrOfID(1));

        assertEquals(-1,l2.getBmrOfID(3));
        assertEquals(-1,l2.getBmrOfID(10));

        assertEquals(1340,l2.getBmrOfID(20));
        assertEquals(1535,l2.getBmrOfID(21));
        assertEquals(1731,l2.getBmrOfID(23));
        assertEquals(1926,l2.getBmrOfID(25));
        assertEquals(2122,l2.getBmrOfID(29));

        assertEquals(1340,l3.getBmrOfID(20));
        assertEquals(1535,l3.getBmrOfID(21));
        assertEquals(1731,l3.getBmrOfID(23));
        assertEquals(1926,l3.getBmrOfID(25));
        assertEquals(2122,l3.getBmrOfID(29));

        assertEquals(1566,lt.getBmrOfID(0));
    }



    @Test
    void testIdIsUsed() {
        assertTrue(l1.idIsUsed(1));
        assertFalse(l1.idIsUsed(2));
    }

    @Test
    void testGetUserOfList() {
        assertEquals(l2.getListOfUser(0),u20);
        assertEquals(l2.getListOfUser(1),u21);
    }

    @Test
    void testIsEmpty() {
        assertFalse(l1.isEmpty());
        assertTrue(l0.isEmpty());
    }

    @Test
    void testBmrHelper() {
        assertEquals(1.2,l2.bmrHelper(u20,1));
        assertEquals(1.375,l2.bmrHelper(u21,1));
        assertEquals(1.55,l2.bmrHelper(u23,1));
        assertEquals(1.725,l2.bmrHelper(u25,1));
        assertEquals(1.9,l2.bmrHelper(u29,1));

        assertEquals(1.2,l2.bmrHelper(uw1,1));
        assertEquals(1.2,l2.bmrHelper(uw2,1));
    }

}
