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


//this class use for calculate user's bmr
public class CalculateBmrPanel extends JPanel implements ActionListener {

    private JTextField idInputField;

    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/ListOfUserTemp.json";
    private ListOfUser listOfUser;

    private int id;

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
        JFrame frame = new JFrame("BMR Calculator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        final CalculateBmrPanel newContentPane = new CalculateBmrPanel();
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

    //EFFECTS: use for reset focus
    protected void resetFocus() {
        idInputField.requestFocusInWindow();
    }

    // Use as a port for main panel access
    public void mainConnectPort() {
        //Create and set up the window.
        JFrame frame = new JFrame("BMR Calculator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        final CalculateBmrPanel newContentPane = new CalculateBmrPanel();
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

    public CalculateBmrPanel() {
        idInputField = new JTextField(10);
        idInputField.setActionCommand("name");
        idInputField.addActionListener(this);

        JLabel label = new JLabel("Enter the User's ID: ");
        label.setLabelFor(idInputField);

        Component buttonPane = createButtonPanel();

        //Lay out everything.
        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(label);
        textPane.add(idInputField);

        add(textPane);
        add(buttonPane);
    }

    // EFFECTS: Create all buttons
    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0, 1));
        JButton removeButton = new JButton("Calculate Your BMR !");
        JButton quitButton = new JButton("Quit");

        removeButton.setActionCommand("bmr");
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

        if ("bmr".equals(e.getActionCommand())) {
            calculateBmr();
        } else if ("quit".equals(e.getActionCommand())) {
            closePanel();
        }
    }

    //EFFECTS: calculate user's bmr and response with a pop panel
    public void calculateBmr() {
        try {
            id = Integer.parseInt(idInputField.getText());
            int i = listOfUser.getBmrOfID(id);
            if (i == -1) {
                popUpPanel.createAndShowGUI("Not Found", "Doesn't find match user", 0);
            } else {
                popUpPanel.createAndShowGUI("BMR calculator", "Your BMR is: \n"
                        + i + " Calories per Day", 1);
                closePanel();
            }
        } catch (NumberFormatException e) {
            popUpPanel = new PopUpPanel();
            popUpPanel.createAndShowGUI("Input not valid",
                    "ID is not valid, please input INTEGER", 0);
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
