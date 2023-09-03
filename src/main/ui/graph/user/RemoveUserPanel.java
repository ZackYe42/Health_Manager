package ui.graph.user;

import model.ListOfUser;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.graph.tools.PopUpPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// this class is use for remove user
public class RemoveUserPanel extends JPanel implements ActionListener {

    private JTextField idInputField;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/ListOfUserTemp.json";
    private ListOfUser listOfUser;

    private int id;

    ActionEvent ex; // use for close the panel !!!

    PopUpPanel popUpPanel;

    JFrame saveOrNotFrame;

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
        JFrame frame = new JFrame("RemoveUser");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        final RemoveUserPanel newContentPane = new RemoveUserPanel();
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

    // Use as a port for main panel access
    public void mainConnectPort() {
        //Create and set up the window.
        JFrame frame = new JFrame("RemoveUser");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        final RemoveUserPanel newContentPane = new RemoveUserPanel();
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

    public RemoveUserPanel() {
        //Use the default FlowLayout.
        //controllingFrame = jf;

        //Create everything.
        idInputField = new JTextField(10);
        idInputField.setActionCommand("Remove");
        idInputField.addActionListener(this);

        JLabel label = new JLabel("Enter the User's ID to remove user: ");
        label.setLabelFor(idInputField);

        JComponent buttonPane = createButtonPanel();

        //Lay out everything.
        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(label);
        textPane.add(idInputField);

        add(textPane);
        add(buttonPane);
    }

    //EFFECTS: construct all buttons
    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0,1));
        JButton removeButton = new JButton("Remove User !");
        JButton quitButton = new JButton("Quit");

        removeButton.setActionCommand("remove");
        quitButton.setActionCommand("quit");
        removeButton.addActionListener(this);
        quitButton.addActionListener(this);

        p.add(removeButton);
        p.add(quitButton);

        return p;
    }

    //EFFECTS: reaction with button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        listOfUser = new ListOfUser();
        loadListOfUser();

        ex = e;

        popUpPanel = new PopUpPanel();

        if ("remove".equals(e.getActionCommand())) {
            removeUser();
        } else if ("quit".equals(e.getActionCommand())) {
            closePanel();
        }
        
    }

    //EFFECTS: remove user by id
    public void removeUser() {
        try {
            id = Integer.parseInt(idInputField.getText());
            if (listOfUser.idIsUsed(id)) {
                doubleCheckRemove();
            } else {
                popUpPanel.createAndShowGUI("Not Found", "Doesn't find match user", 0);
            }
        } catch (NumberFormatException e) {
            popUpPanel = new PopUpPanel();
            popUpPanel.createAndShowGUI("Input not valid",
                    "ID is not valid, please input INTEGER", 0);

        }
    }

    //EFFECTS: ask user whether save data
    public void doubleCheckRemove() {
        int n = JOptionPane.showConfirmDialog(
                saveOrNotFrame, "Corresponding User Found!\n Remove User and save to system?",
                "Save?",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            listOfUser.removeUserOfID(id);
            saveListOfUser();
            popUpPanel.createAndShowGUI("Removed!", "User removed! \nSave success !", 1);
            closePanel();
        } else if (n == JOptionPane.NO_OPTION) {
            System.out.println("User not save");
            popUpPanel.createAndShowGUI("Undo", "User not remove !", 2);
        } else {
            System.out.println("ERROR in save or not");
        }
    }

    //dispose the panel
    public void closePanel() {
        JComponent comp = (JComponent) ex.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
    }





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
