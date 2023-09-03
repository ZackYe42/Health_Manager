package ui.graph.user;

import model.Event;
import model.EventLog;
import model.ListOfUser;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.graph.tools.PopUpPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// this class is use for create new user
public class CreateUserPanel extends JPanel
        implements ActionListener, FocusListener {

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/ListOfUserTemp.json";
    private User user;
    private ListOfUser listOfUser;



    int fieldNum;
    JTextField nameField;
    JTextField sexField;
    JTextField ageField;
    JTextField heightField;
    JTextField weightField;
    JTextField exercisedTimesField;
    JTextField idField;

    JFrame userPanel;

    ActionEvent ex; // use for close the panel !!!

    PopUpPanel popUpPanel;

    private String name;
    private String sex; //W for Woman, M for Man
    private int age;
    private double height;
    private double weight;
    private int exercisedPerWeek;
    private int id;


    JFrame saveOrNotFrame;

    static final int GAP = 10;

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new CreateUserPanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //frame.add(new CreateUserPanel());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // Use as a port for main panel access
    public void mainConnectPort() {

        //Create and set up the window.
        userPanel = new JFrame("Welcome");
        userPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new CreateUserPanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        userPanel.setContentPane(newContentPane);

        //Display the window.
        userPanel.pack();
        userPanel.setVisible(true);


    }


    public CreateUserPanel() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JPanel topPart = new JPanel() {
            //Don't allow us to stretch vertically.
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE,
                        pref.height);
            }
        };
        topPart.setLayout(new BoxLayout(topPart, BoxLayout.PAGE_AXIS));

        topPart.add(createEntryFields());
        topPart.add(createButtons());

        add(topPart);


    }

    // EFFECTS: create fields that let user input their information
    protected JComponent createEntryFields() {
        JPanel panel = new JPanel(new SpringLayout());

        String[] labelStrings = {
                "Name: ",
                "Sex (W for Women, M for Men) : ",
                "Age (int): ",
                "Height (m) : ",
                "Weight (kg) : ",
                "Exercised Time Per Week (int): ",
                "ID you prefer (int): "
        };

        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];
        this.fieldNum = 0;

        //Create the text field and set it up.
        initJT();

        textFieldHelper(nameField, fields);

        textFieldHelper(sexField, fields);

        textFieldHelper(ageField, fields);

        textFieldHelper(heightField, fields);

        textFieldHelper(weightField, fields);

        textFieldHelper(exercisedTimesField, fields);

        textFieldHelper(idField, fields);

        componentHelper(panel, labelStrings, labels, fields);

        return panel;


    }

    // initial JTextField
    private void initJT() {
        nameField = new JTextField();
        sexField = new JTextField();
        ageField = new JTextField();
        heightField = new JTextField();
        weightField = new JTextField();
        exercisedTimesField = new JTextField();
        idField = new JTextField();
    }

    // create entry filed helper
    private void componentHelper(JPanel panel, String[] labelStrings, JLabel[] labels, JComponent[] fields) {
        //Associate label/field pairs, add everything,
        //and lay it out.
        for (int i = 0; i < labelStrings.length; i++) {
            labels[i] = new JLabel(labelStrings[i],
                    JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            panel.add(labels[i]);
            panel.add(fields[i]);

            //Add listeners to each field.
            JTextField tf = null;
            if (fields[i] instanceof JSpinner) {
                tf = getTextField((JSpinner) fields[i]);
            } else {
                tf = (JTextField) fields[i];
            }
            tf.addActionListener(this);
            tf.addFocusListener(this);
        }

        SpringUtilities.makeCompactGrid(panel,
                labelStrings.length, 2,
                GAP, GAP, //init x,y
                GAP, GAP / 2);//xpad, ypad
    }

    //EFFECTS: easy way to construct a text field
    protected void textFieldHelper(JTextField jf, JComponent[] fields) {
        int fn = fieldNum;
        //jf = new JTextField();
        jf.setColumns(10);
        fields[fn++] = jf;
        this.fieldNum = fn;
        //add(Box.createVerticalStrut(40));
    }

    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            return ((JSpinner.DefaultEditor) editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: "
                    + spinner.getEditor().getClass()
                    + " isn't a descendant of DefaultEditor");
            return null;
        }
    }

    //create buttons
    protected JComponent createButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JButton save = new JButton("Save!");
        save.addActionListener(this);
        save.setActionCommand("save");
        panel.add(save);

        JButton quit = new JButton("Quit !");
        quit.addActionListener(this);
        quit.setActionCommand("quit");
        panel.add(quit);

        //Match the SpringLayout's gap, subtracting 5 to make
        //up for the default gap FlowLayout provides.
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0,
                GAP - 5, GAP - 5));
        return panel;
    }

    //EFFECTS: response with action
    @Override
    public void actionPerformed(ActionEvent e) {

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        listOfUser = new ListOfUser();
        loadListOfUser();

        ex = e;

        popUpPanel = new PopUpPanel();

        if ("save".equals(e.getActionCommand())) {
            addUser();
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

    // EFFECTS: add user to the list
    public void addUser() {
        try {
            name = nameField.getText();
            sex = sexField.getText();
            age = Integer.parseInt(ageField.getText());
            height = Double.parseDouble(heightField.getText());
            weight = Double.parseDouble(weightField.getText());
            exercisedPerWeek = Integer.parseInt(exercisedTimesField.getText());
            id = Integer.parseInt(idField.getText());
            if (checkAll()) {
                user = new User(name, sex, age, height, weight, exercisedPerWeek, id);
                boolean b = listOfUser.addUser(user);
                if (b) {
                    saveOrNot();
                } else {
                    popUpPanel.createAndShowGUI("System full",
                            "The system is full, please remove a user !", 0);
                }
            }
        } catch (NumberFormatException e) {
            popUpPanel = new PopUpPanel();
            popUpPanel.createAndShowGUI("Input not valid",
                    "Your input is not valid, please try again", 0);

        }

    }

    //***************************************************Add User Helper******************************

    // EFFECTS: check is input valid
    private boolean checkAll() {
        if (!checkName()) {
            popUpPanel.createAndShowGUI("Input Invalid!!", "Name can't be empty!!", 0);
            return false;
        } else if (!checkSex()) {
            popUpPanel.createAndShowGUI("Input Invalid!!", "Please in put valid gender!!", 0);
            return false;
        } else if (!checkAge()) {
            popUpPanel.createAndShowGUI("Input Invalid!!", "Age can't be negative or 0!!", 0);
            return false;
        } else if (!checkDouble(height) || !checkDouble(weight)) {
            popUpPanel.createAndShowGUI("Input Invalid!!", "Height or weight can't be negative or 0!!", 0);
            return false;
        } else if (!checkExciseTime()) {
            popUpPanel.createAndShowGUI("Input Invalid!!", "Exercise time can't be negative!!", 0);
            return false;
        } else if (!checkID()) {
            popUpPanel.createAndShowGUI("ID is Used", "This ID already be used, please try another one", 0);
            return false;
        } else {
            return true;
        }
    }

    // EFFECTS: check the id valid or not
    private boolean checkID() {
        if (listOfUser.idIsUsed(id)) {
            return false;
        }
        return true;
    }

    // EFFECTS: check the name valid or not
    private boolean checkName() {
        if ((name.length() == 0) || (Character.isSpace(name.charAt(0)))) {
            return false;
        }
        return true;
    }

    // EFFECTS: check the age valid or not
    private boolean checkAge() {
        if (age > 0) {
            return true;
        }
        return false;
    }

    // EFFECTS: check the height or weight valid or not
    private boolean checkDouble(Double d) {
        if (d > 0) {
            return true;
        }
        return false;
    }

    // EFFECTS: check the gender valid or not
    private boolean checkSex() {
        if (sex.equals("M") | sex.equals("W")) {
            return true;
        }
        return false;
    }

    // EFFECTS: check the ExciseTime valid or not
    private boolean checkExciseTime() {
        if (exercisedPerWeek >= 0) {
            return true;
        }
        return false;
    }


    //***************************************************Add User Helper******************************


    // Let user choose save or not
    public void saveOrNot() {
        int n = JOptionPane.showConfirmDialog(
                saveOrNotFrame, "Do you want to save your Information?",
                "Save?",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            saveListOfUser();
            popUpPanel.createAndShowGUI("Welcome", "Save success ! \nWelcome !", 1);
            closePanel();
        } else if (n == JOptionPane.NO_OPTION) {
            System.out.println("User not save");
            popUpPanel.createAndShowGUI("Wrong", "Information Not Saved !", 2);
        } else {
            System.out.println("create user not success");
        }
    }


    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */


    //******************************************* Save and Load***************************************************

    // EFFECTS: saves the listofuser to file
    private void saveListOfUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(listOfUser);
            jsonWriter.close();
            System.out.println("Save Success!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

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
}
