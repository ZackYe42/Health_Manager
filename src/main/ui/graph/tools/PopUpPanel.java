package ui.graph.tools;

import javax.swing.*;

// THe class is use for show a popup panel
public class PopUpPanel extends JPanel {

    /**
     * Message Type

    public static final int  ERROR_MESSAGE = 0;
     Used for information messages.
    public static final int  INFORMATION_MESSAGE = 1;
     Used for warning messages.
    public static final int  WARNING_MESSAGE = 2;
     Used for questions.
    public static final int  QUESTION_MESSAGE = 3;
     No icon is used.
    public static final int   PLAIN_MESSAGE = -1;
    */

    //Effects: Create new popupPanel
    public void createAndShowGUI(String topBar, String notice, int messageType) {
        JFrame frame = new JFrame(topBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(frame,notice,topBar,messageType);


//        PopUpPanel popUpPanel = new PopUpPanel();
//        popUpPanel.setOpaque(true);
//        frame.setContentPane(popUpPanel);
//
//        frame.pack();
//        frame.setVisible(false);
    }

    //Effects: Create new popupPanel then show an image
    public void createAndShowGUI(String topBar, String notice, int messageType,String path) {
        JFrame frame = new JFrame(topBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(frame,notice,topBar,messageType);


        ImageViewer m = new ImageViewer(path);
        frame.add(m);
        frame.setSize(400, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    }

}
