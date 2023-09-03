package ui.graph;

import model.Event;
import model.EventLog;
import model.ListOfUser;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.graph.admin.AdminCheckPanel;
import ui.graph.tools.PopUpPanel;
import ui.graph.user.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// this class is the main panel.
public class MainPanel extends JPanel implements ActionListener {

    private static final String JSON_STORE = "./data/ListOfUser.json";
    private static final String JSON_STORE_TEMP = "./data/ListOfUserTemp.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JsonWriter jsonWriterTemp;
    private JsonReader jsonReaderTemp;

    private ListOfUser listOfUser;

    private PopUpPanel popUpPanel;

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

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window
        JFrame frame = new JFrame("Health Manger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        MainPanel mainPane = new MainPanel();
        mainPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(mainPane);

        //addComponentsToPane(frame.getContentPane());


        //Display the window.
        frame.pack();
        frame.setSize(800, 800);
        frame.setVisible(true);



    }

    public MainPanel() {
        initJsonFile();
        initTemp();

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JPanel mainPane = new JPanel() {
            //Don't allow us to stretch vertically.
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE,
                        pref.height);
            }
        };

        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));


        buttonsCreate(mainPane);


        add(mainPane);

        popUpPanel = new PopUpPanel();

    }

    //EFFECTS: construct all buttons
    protected void buttonsCreate(Container panel) {
        //JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create Button
        addAButton("load","Load System Information",panel);
        addAButton("bnewUser", "Create new User !", panel);
        addAButton("bremove", "Remove User !", panel);
        addAButton("bforget", "Find back ID", panel);
        addAButton("bbmi", "Calculate your BMI", panel);
        addAButton("bbmr", "Calculate your BMR", panel);
        addAButton("save","Save Information to System",panel);
        addAButton("admin","Admin Tools",panel);
        addAButton("exit","EXIT !",panel);


        //Match the SpringLayout's gap, subtracting 5 to make
        //up for the default gap FlowLayout provides.
        //panel.setBorder(BorderFactory.createEmptyBorder(0, 0,
        //        GAP - 5, GAP - 5));
        //return panel;
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


    //EFFECTS: response with action
    @Override
    public void actionPerformed(ActionEvent e) {

        if ("bnewUser".equals(e.getActionCommand())) {
            //System.out.println("UserPanel open success");
            CreateUserPanel p = new CreateUserPanel();
            p.mainConnectPort();
        } else if ("bremove".equals(e.getActionCommand())) {
            //System.out.println("removeUserPanel open success");
            RemoveUserPanel p = new RemoveUserPanel();
            p.mainConnectPort();
        } else if ("bforget".equals(e.getActionCommand())) {
            //System.out.println("ForgetIdPanel open success");
            ForgetIdPanel p = new ForgetIdPanel();
            p.mainConnectPort();
        } else if ("bbmi".equals(e.getActionCommand())) {
            //System.out.println("CalculateBmiPanel open success");
            CalculateBmiPanel p = new CalculateBmiPanel();
            p.mainConnectPort();
        } else if ("bbmr".equals(e.getActionCommand())) {
            //System.out.println("CalculateBmrPanel open success");
            CalculateBmrPanel p = new CalculateBmrPanel();
            p.mainConnectPort();
        } else if ("admin".equals(e.getActionCommand())) {
            //System.out.println("AdminCheckPanel open success");
            AdminCheckPanel p = new AdminCheckPanel();
            p.mainConnectPort();
        } else {
            secondPartActionPerformed(e);
        }
    }

    //EFFECTS: response with action
    private void secondPartActionPerformed(ActionEvent e) {
        if ("exit".equals(e.getActionCommand())) {
            exitHelper();
        } else if ("load".equals(e.getActionCommand())) {
            loadHelper();
        } else if ("save".equals(e.getActionCommand())) {
            saveHelper();
        } else {
            popUpPanel.createAndShowGUI("Not Vaild","No Such Button!!!",0);
        }
    }

    //EFFECTS: close the program and initial temp json file
    private void exitHelper() {
        popUpPanel.createAndShowGUI("EXIT","See you next time !",-1);
        initTemp();
        for (Event next : EventLog.getInstance()) {
            String toString = next.toString();
            System.out.println(toString);
        }
        EventLog.getInstance().clear();
        System.exit(1);
    }

    //EFFECTS: load list of user file to temp file
    private void loadHelper() {
        popUpPanel.createAndShowGUI("System Load", "System loaded success!", 1);
        loadListOfUser(jsonReader,JSON_STORE,"Load listOfUser from " + JSON_STORE);
        saveListOfUser(jsonWriterTemp,JSON_STORE_TEMP,"Save listOfUser to " + JSON_STORE_TEMP);

    }

    //EFFECTS: save temp file to list of user file
    private void saveHelper() {
        popUpPanel.createAndShowGUI("System save", "System save success!", 1);
        loadListOfUser(jsonReaderTemp,JSON_STORE_TEMP,"Load listOfUser from " + JSON_STORE_TEMP);
        saveListOfUser(jsonWriter,JSON_STORE,"Save listOfUser to " + JSON_STORE);
    }

    //EFFECTS: initial the temp json file
    private void initJsonFile() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriterTemp = new JsonWriter(JSON_STORE_TEMP);
        jsonReaderTemp = new JsonReader(JSON_STORE_TEMP);
    }

    //******************************************* Save and Load***************************************************

    // EFFECTS: saves the listofuser to file
    private void saveListOfUser(JsonWriter jsonWriter,String path,String info) {
        try {
            jsonWriter.open();
            jsonWriter.write(listOfUser);
            jsonWriter.close();
            System.out.println(info);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + path);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads listofuser from file
    private void loadListOfUser(JsonReader jsonReader,String path,String info) {
        try {
            listOfUser = new ListOfUser();
            listOfUser = jsonReader.read();
            System.out.println(info);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + path);
        }
    }

    //EFFECTS: initial temp file as empty
    private void initTemp() {
        try {
            jsonWriterTemp.open();
            jsonWriterTemp.write(new ListOfUser());
            jsonWriterTemp.close();
            System.out.println("Initial " + JSON_STORE_TEMP + " Success!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_TEMP);
        }
    }


}
