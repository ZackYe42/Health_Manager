package ui.graph.admin;

import ui.graph.MainPanel;
import ui.graph.user.CreateUserPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//this class use as an admin panel
public class AdminPanel extends JPanel implements ActionListener {

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
        JFrame frame = new JFrame("Admin");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        AdminPanel adminPane = new AdminPanel();
        adminPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(adminPane);

        //addComponentsToPane(frame.getContentPane());


        //Display the window.
        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);

    }

    // Use as a port for main panel access
    public void mainConnectPort() {
        //Create and set up the window
        JFrame frame = new JFrame("Admin");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        AdminPanel adminPane = new AdminPanel();
        adminPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(adminPane);

        //addComponentsToPane(frame.getContentPane());


        //Display the window.
        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    public AdminPanel() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JPanel adminPanel = new JPanel() {
            //Don't allow us to stretch vertically.
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE,
                        pref.height);
            }
        };

        adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.PAGE_AXIS));


        buttonsCreate(adminPanel);


        add(adminPanel);
    }

    //EFFECTS: construct all buttons
    protected void buttonsCreate(Container panel) {
        addAButton("list", "List of all user's information", panel);
        addAButton("quit","Back to main Panel",panel);
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
        if ("list".equals(e.getActionCommand())) {
            System.out.println("show All user's information");
            AllUser p = new AllUser();
            p.mainConnectPort();
        } else if ("quit".equals(e.getActionCommand())) {
            JComponent comp = (JComponent) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);
            win.dispose();
        }
    }
}
