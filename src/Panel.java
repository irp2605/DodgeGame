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
        setSize(1000,1000);
        addKeyListener(this);
        worlds = new ArrayList<World>();
        try {
            worlds.add(new World(new File("C:\\Users\\irp26\\Documents\\CS3\\DodgeGame\\src\\test.txt")));
            world = worlds.get(0);
            world.textConverter();
            System.out.println(world.getNonplayerList().toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void paint(Graphics g) {
        System.out.println("W");
        g.setFont(new Font("Times New Roman", Font.BOLD, 25));
        for(int c=0; c<world.getNonplayerList().size(); c++) {
            System.out.println( world.getNonplayerList().get(c).getType() + ":" + world.getNonplayerList().get(c).getGridX() + "," + world.getNonplayerList().get(c).getGridY());
            if(world.getNonplayerList().get(c).getType()==0) {
                g.setColor(Color.lightGray);
                g.fillRect(world.getNonplayerList().get(c).getGridX()*100, world.getNonplayerList().get(c).getGridY()*100, 100, 100);

            }
            if(world.getNonplayerList().get(c).getType()==Constants.WALL) {
                g.setColor(Color.RED);
                g.fillRect(world.getNonplayerList().get(c).getGridX()*100, world.getNonplayerList().get(c).getGridY()*100, 100, 100);
            }
            if(world.getNonplayerList().get(c).getType()==Constants.GOAL) {
                g.setColor(Color.GREEN);
                g.fillRect(world.getNonplayerList().get(c).getGridX()*100, world.getNonplayerList().get(c).getGridY()*100, 100, 100);
            }
            if(world.getNonplayerList().get(c).getType()==Constants.PLAYER) {
                g.setColor(Color.BLUE);
                g.fillRect(world.getNonplayerList().get(c).getGridX()*100, world.getNonplayerList().get(c).getGridY()*100, 100, 100);
            }
            g.setColor(Color.black);
            g.drawRect(world.getNonplayerList().get(c).getGridX()*100, world.getNonplayerList().get(c).getGridY()*100, 100, 100);
            g.drawString(String.valueOf(c),world.getNonplayerList().get(c).getGridX()*100, world.getNonplayerList().get(c).getGridY()*100+100);
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
