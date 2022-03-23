import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

public class Panel extends JPanel implements KeyListener, Runnable {
    private int currWorld =1;
    private Player player;
    private World world;
    private ArrayList<World> worlds;
    private int updatesPerSecond = 30;
    private Thread t;
    public Panel() {
        addKeyListener(this);
        worlds.add(new World(new File("test.txt")));
        world = worlds.get(1);
        world.textConverter();
    }
    public void Paint(Graphics g) {
        for(int c=0; c<world.getNonplayerList().size(); c++) {
            if(world.getNonplayerList().get(c).getType()==0) {
                g.setColor(Color.lightGray);
                g.drawRect(world.getNonplayerList().get(c).getGridX()*10, world.getNonplayerList().get(c).getGridX()*10, 10, 10);
            }

        }
    }

    public void update() {

    }

    public void reset() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

    }
}
