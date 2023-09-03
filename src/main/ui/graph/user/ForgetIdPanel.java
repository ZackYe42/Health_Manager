package ui.graph.user;

import model.ListOfUser;
import persistence.JsonReader;
import ui.graph.tools.PopUpPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

// this class use for find back user's id
public class ForgetIdPanel extends JPanel implements ActionListener {

    private JTextField nameInputField;
    private JTextField ageInputField;

    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/ListOfUserTemp.json";
    private ListOfUser listOfUser;

    private String name;
    private int age;

    ActionEvent ex; // use for close the panel !!!

    PopUpPanel popUpPanel;

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Forget ID");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        final ForgetIdPanel newContentPane = new ForgetIdPanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Make sure the focus goes to the right component
        //whenever the frame is initially given the focus.
        frame.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                newContentPane.resetFocus();
            }
        });

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    protected void resetFocus() {
        nameInputField.requestFocusInWindow();
        ageInputField.requestFocusInWindow();
    }

    // Use as a port for main panel access
    public void mainConnectPort() {
        //Create and set up the window.
        JFrame frame = new JFrame("Forget ID");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        final ForgetIdPanel newContentPane = new ForgetIdPanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Make sure the focus goes to the right component
        //whenever the frame is initially given the focus.
        frame.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                newContentPane.resetFocus();
            }
        });

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public ForgetIdPanel() {
        //Create everything.
        nameInputField = new JTextField(10);
        nameInputField.setActionCommand("name");
        nameInputField.addActionListener(this);

        ageInputField = new JTextField(10);
        ageInputField.setActionCommand("age");
        ageInputField.addActionListener(this);

        JLabel label = new JLabel("Enter the User's Name: ");
        label.setLabelFor(nameInputField);

        JLabel label1 = new JLabel("Enter the User's Age: ");
        label.setLabelFor(ageInputField);

        JComponent buttonPane = createButtonPanel();

        //Lay out everything.
        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(label);
        textPane.add(nameInputField);
        textPane.add(label1);
        textPane.add(ageInputField);

        add(textPane);
        add(buttonPane);
    }

    //EFFECTS: construct all buttons
    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0,1));
        JButton removeButton = new JButton("Find back ID !");
        JButton quitButton = new JButton("Quit");

        removeButton.setActionCommand("find");
        quitButton.setActionCommand("quit");
        removeButton.addActionListener(this);
        quitButton.addActionListener(this);

        p.add(removeButton);
        p.add(quitButton);

        return p;
    }

    //EFFECTS: response with action
    @Override
    public void actionPerformed(ActionEvent e) {
        jsonReader = new JsonReader(JSON_STORE);
        listOfUser = new ListOfUser();
        loadListOfUser();

        ex = e;

        popUpPanel = new PopUpPanel();

        if ("find".equals(e.getActionCommand())) {
            findBackID();
        } else if ("quit".equals(e.getActionCommand())) {
            closePanel();
        }

    }

    //EFFECTS: use for find back user's id
    private void findBackID() {
        try {
            name = nameInputField.getText();
            age = Integer.parseInt(ageInputField.getText());
            if (listOfUser.findIdOfNameAndAge(name,age) == -7355608) {
                popUpPanel.createAndShowGUI("Not Found","Doesn't find match user",0);
            } else {
                popUpPanel.createAndShowGUI("ID Founded","Your ID is: \n"
                        + listOfUser.findIdOfNameAndAge(name,age) + "\nKeep it!",1);
                closePanel();
            }
        } catch (NumberFormatException e) {
            popUpPanel = new PopUpPanel();
            popUpPanel.createAndShowGUI("Input not valid",
                    "Age is not valid, please input INTEGER", 0);

        }
    }

    //dispose the panel
    public void closePanel() {
        JComponent comp = (JComponent) ex.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
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
}
