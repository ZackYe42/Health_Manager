package ui.graph.tools;

import java.awt.*;
import javax.swing.JFrame;

// Use to show image
public class ImageViewer extends Canvas {

    String path;

    public ImageViewer(String path) {
        this.path = path;
    }

    //EFFECTS: set the image
    public void paint(Graphics g) {

        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage(path);
        g.drawImage(i, 50, 50, this);

    }

//    public static void main(String[] args) {
//        ImageViewer m = new ImageViewer();
//        JFrame f = new JFrame();
//        f.add(m);
//        f.setSize(400, 700);
//        f.setVisible(true);
//    }



}  