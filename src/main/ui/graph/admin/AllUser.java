package ui.graph.admin;

import model.ListOfUser;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.graph.tools.PopUpPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

//this class is use for show all user's information
public class AllUser extends JPanel implements ActionListener {
    private String name;
    private String sex; //W for Woman, M for Man
    private int age;
    private double height;
    private double weight;
    private int exercisedPerWeek;
    private int id;

    PopUpPanel popUpPanel;

    JFrame saveOrNotFrame;


    private boolean deBUG = false;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/ListOfUserTemp.json";
    private ListOfUser listOfUser;

    ListOfUser update;

    ActionEvent ex; // use for close the panel !!!

    private DefaultTableModel tableModel;

    JTable table;


    int i1 = 0;


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("All user's data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        AllUser newContentPane = new AllUser();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // Use as a port for main panel access
    public void mainConnectPort() {
        //Create and set up the window.
        JFrame frame = new JFrame("All user's data");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        AllUser newContentPane = new AllUser();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

//    public AllUser() {
//        super(new GridLayout(1, 0));
//
//        jsonReader = new JsonReader(JSON_STORE);
//        listOfUser = new ListOfUser();
//        loadListOfUser();
//
//        String[] columnNames = {"ID", "Name", "Gender", "Age", "Height", "Weight", "ExercisedPerWeek"};
//
//        Object[][] obj = getObjects(columnNames);
//
//        table = new JTable(obj, columnNames);
//        table.setPreferredScrollableViewportSize(new Dimension(900, 100));
//        table.setFillsViewportHeight(true);
//
//        //tableModel = (DefaultTableModel) table.getModel();
//
//        if (deBUG) {
//            table.addMouseListener(new MouseAdapter() {
//                public void mouseClicked(MouseEvent e) {
//                    printDebugData(table);
//                }
//            });
//        }
//
//        //Create the scroll pane and add the table to it.
//        JScrollPane scrollPane = new JScrollPane(table);
//
//        JComponent buttonPane = createButtonPanel();
//
//        //Add the scroll pane to this panel.
//        add(scrollPane);
//        //add(buttonPane);
//    }

    public AllUser() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        listOfUser = new ListOfUser();
        loadListOfUser();

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JPanel panel = new JPanel() {
            //Don't allow us to stretch vertically.
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE,
                        pref.height);
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        constructTabel();

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        JComponent buttonPane = createButtonPanel();


        panel.add(scrollPane);
        panel.add(buttonPane);

        add(panel);
    }

    private void constructTabel() {
        String[] columnNames = {"ID", "Name", "Gender", "Age", "Height", "Weight", "ExercisedPerWeek"};

        Object[][] obj = getObjects(columnNames);

        table = new JTable(obj, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(900, 100));
        table.setFillsViewportHeight(true);

        //tableModel = (DefaultTableModel) table.getModel();

        if (deBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }
    }

    //EFFECTS: create a table for all user's information
    private Object[][] getObjects(String[] columnNames) {
        Object[][] obj = new Object[listOfUser.length()][columnNames.length];
        for (int i = 0; i < listOfUser.length(); i++) {
            for (int j = 0; j < columnNames.length; j++) {
                if (j == 0) {
                    obj[i][j] = String.valueOf(listOfUser.getListOfUser(i).getId());
                } else if (j == 1) {
                    obj[i][j] = String.valueOf(listOfUser.getListOfUser(i).getName());
                } else if (j == 2) {
                    obj[i][j] = String.valueOf(listOfUser.getListOfUser(i).getSex());
                } else if (j == 3) {
                    obj[i][j] = String.valueOf(listOfUser.getListOfUser(i).getAge());
                } else if (j == 4) {
                    obj[i][j] = String.valueOf(listOfUser.getListOfUser(i).getHeight());
                } else if (j == 5) {
                    obj[i][j] = String.valueOf(listOfUser.getListOfUser(i).getWeight());
                } else {
                    obj[i][j] = String.valueOf(listOfUser.getListOfUser(i).getExercisedPerWeek());
                }
            }
        }
        return obj;
    }

    //EFFECTS: construct all buttons
    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new FlowLayout());
        //addAButton("check","Check all value",p);
        addAButton("update", "Update System Information", p);
        addAButton("quit", "Quit", p);

        return p;
    }

    //EFFECTS: create new button
    private void addAButton(String listenerName, String buttonName, Container panel) {
        // Create Button
        JButton button = new JButton(buttonName);
        //Set buttonName location
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Listen for actions on buttons.
        button.addActionListener(this);
        button.setActionCommand(listenerName);

        panel.add(button);
        panel.add(Box.createVerticalStrut(40));
    }


    //EFFECTS: help debug the table
    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }


    //EFFECTS: allow admin update system information by jTable
    public int update() {
        //DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        update = new ListOfUser();
        int rowCount = table.getRowCount();
        int check = 0;

        try {
            for (int i = 0; i < rowCount; i++) {
                i1 = i + 1;
                updateHelper(i);
                if (checkAll(i + 1)) {
                    User user = new User(name, sex, age, height, weight, exercisedPerWeek, id);
                    update.addUser(user);
                    check += 1;
                } else {
                    check -= 1;
                }
            }
        } catch (NumberFormatException e) {
            popUpPanel.createAndShowGUI("Input not valid", "Your input in row " + i1 + " is not valid", 0);
            check -= 1;
        }
        return check;



    }

    private void updateHelper(int i) {
        id = Integer.parseInt((String) table.getValueAt(i, 0));
        name = (String) table.getValueAt(i, 1);
        sex = (String) table.getValueAt(i, 2);
        age = Integer.parseInt((String) table.getValueAt(i, 3));
        height = Double.parseDouble((String) table.getValueAt(i, 4));
        weight = Double.parseDouble((String) table.getValueAt(i, 5));
        exercisedPerWeek = Integer.parseInt((String) table.getValueAt(i, 6));
    }

    //***************************************************Add User Helper******************************

    // EFFECTS: check is input valid
    private boolean checkAll(int i) {
        if (!checkName()) {
            popUpPanel.createAndShowGUI("Row " + i + " Input Invalid!!", "Name can't be empty!!", 0);
            return false;
        } else if (!checkSex()) {
            popUpPanel.createAndShowGUI("Row " + i + " Input Invalid!!", "Please in put valid gender!!", 0);
            return false;
        } else if (!checkAge()) {
            popUpPanel.createAndShowGUI("Row " + i + " Input Invalid!!", "Age can't be negative or 0!!", 0);
            return false;
        } else if (!checkDouble(height) || !checkDouble(weight)) {
            popUpPanel.createAndShowGUI("Row " + i + " Input Invalid!!",
                    "Height or weight can't be negative or 0!!", 0);
            return false;
        } else if (!checkExciseTime()) {
            popUpPanel.createAndShowGUI("Row " + i + " Input Invalid!!",
                    "Exercise time can't be negative!!", 0);
            return false;
        } else if (!checkID(i)) {
            popUpPanel.createAndShowGUI("Row " + i + " Input Invalid!!", "This ID already be used", 0);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkID(int i) {
        if (id == listOfUser.getListOfUser(i - 1).getId()) {
            return true;
        } else if (listOfUser.idIsUsed(id)) {
            return false;
        }
        return true;
    }

    private boolean checkName() {
        if ((name.length() == 0) || (Character.isSpace(name.charAt(0)))) {
            return false;
        }
        return true;
    }

    private boolean checkAge() {
        if (age > 0) {
            return true;
        }
        return false;
    }

    private boolean checkDouble(Double d) {
        if (d > 0) {
            return true;
        }
        return false;
    }

    private boolean checkSex() {
        if (sex.equals("M") | sex.equals("W")) {
            return true;
        }
        return false;
    }

    private boolean checkExciseTime() {
        if (exercisedPerWeek >= 0) {
            return true;
        }
        return false;
    }


    //***************************************************Add User Helper******************************


    //EFFECTS: response with action
    @Override
    public void actionPerformed(ActionEvent e) {

        ex = e;

        popUpPanel = new PopUpPanel();


        if ("update".equals(e.getActionCommand())) {
            if (update() == update.length()) {
                updateOrNot();
            }

        } else if ("quit".equals(e.getActionCommand())) {
            closePanel();
        }

    }

    //dispose the panel
    public void closePanel() {
        JComponent comp = (JComponent) ex.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
    }

    // Let admin choose update or not
    public void updateOrNot() {
        int n = JOptionPane.showConfirmDialog(
                saveOrNotFrame, "Do you want to UPDATE System Information?",
                "Update?",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            saveListOfUser();
            popUpPanel.createAndShowGUI("Success", "Update success !", 1);
        } else if (n == JOptionPane.NO_OPTION) {
            System.out.println("User not save");
            popUpPanel.createAndShowGUI("Wrong", "Information Not Saved !", 2);
        } else {
            System.out.println("create user not success");
        }
    }


    //******************************************* Save and Load***************************************************
    // MODIFIES: this
    // EFFECTS: loads listofuser from file
    private void loadListOfUser() {
        try {
            listOfUser = new ListOfUser();
            listOfUser = jsonReader.read();
            System.out.println("Loaded Success!");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the listofuser to file
    private void saveListOfUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(update);
            jsonWriter.close();
            System.out.println("Save Success!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}
