package ui.graph.admin;

import ui.graph.user.RemoveUserPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

//this class use for check admin password
public class AdminCheckPanel extends JPanel implements ActionListener {
    private static String OK = "ok";
    private static String Quit = "quit";

    private JFrame controllingFrame; //needed for dialogs
    private JPasswordField passwordField;

    ActionEvent ex; // use for close the panel !!!

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

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Admin Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        final AdminCheckPanel newContentPane = new AdminCheckPanel();
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

    //Must be called from the event dispatch thread.
    protected void resetFocus() {
        passwordField.requestFocusInWindow();
    }

    // Use as a port for main panel access
    public void mainConnectPort() {
        //Create and set up the window.
        JFrame frame = new JFrame("Admin Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        final AdminCheckPanel newContentPane = new AdminCheckPanel();
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



    public AdminCheckPanel() {
        //Use the default FlowLayout.

        //Create everything.
        passwordField = new JPasswordField(10);
        passwordField.setActionCommand(OK);
        passwordField.addActionListener(this);

        JLabel label = new JLabel("Enter the password: ");
        label.setLabelFor(passwordField);

        JComponent buttonPane = createButtonPanel();

        //Lay out everything.
        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(label);
        textPane.add(passwordField);

        add(textPane);
        add(buttonPane);
    }

    //EFFECTS: create new button
    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0, 1));
        JButton okButton = new JButton("OK");
        JButton helpButton = new JButton("Quit");

        okButton.setActionCommand(OK);
        helpButton.setActionCommand(Quit);
        okButton.addActionListener(this);
        helpButton.addActionListener(this);

        p.add(okButton);
        p.add(helpButton);

        return p;
    }

    //EFFECTS: response with action
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        ex = e;

        if (OK.equals(cmd)) { //Process the password.
            char[] input = passwordField.getPassword();
            if (isPasswordCorrect(input)) {
                JOptionPane.showMessageDialog(controllingFrame,
                        "Success! Welcome Administrator!");
                closePanel();
                AdminPanel p = new AdminPanel();
                p.mainConnectPort();


            } else {
                JOptionPane.showMessageDialog(controllingFrame,
                        "Invalid password. Try again.",
                        "Error Message",
                        JOptionPane.ERROR_MESSAGE);
            }

            //Zero out the possible password, for security.
            Arrays.fill(input, '0');

            passwordField.selectAll();
            resetFocus();
        } else { //The user has asked for help.
            closePanel();
        }
    }

    /**
     * Checks the passed-in array against the correct password.
     * After this method returns, you should invoke eraseArray
     * on the passed-in array.
     */
    private static boolean isPasswordCorrect(char[] input) {
        boolean isCorrect = true;
        char[] correctPassword = {'4', '2'};

        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals(input, correctPassword);
        }

        //Zero out the password.
        Arrays.fill(correctPassword, '0');

        return isCorrect;
    }



    //dispose the panel
    public void closePanel() {
        JComponent comp = (JComponent) ex.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
    }
}
