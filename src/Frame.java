import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    Frame(String frameName) {
        super(frameName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();

        Panel p = new Panel();
        Insets frameInsets = getInsets();

        int frameWidth = p.getWidth() + (frameInsets.left + frameInsets.right);
        int frameHeight = p.getHeight() + (frameInsets.top + frameInsets.bottom);

        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setLayout(null);

        add(p);
        pack();

        setVisible(true);
    }
}

