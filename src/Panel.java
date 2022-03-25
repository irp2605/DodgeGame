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
        Thread t = new Thread(this);
        t.start();
        worlds = new ArrayList<World>();
        try {
            worlds.add(new World(new File("C:\\Users\\B1003721\\Desktop\\DodgeGame\\src\\test.txt")));
            world = worlds.get(0);
            world.textConverter();
            System.out.println(world.getNonplayerList().toString());
            player = new Player(-1,-1,0, 3);
            for(int c=0; c<world.getNonplayerList().size(); c++) {
                if(world.getNonplayerList().get(c).getType()== Constants.PLAYER) {
                    player.setGridX(world.getNonplayerList().get(c).getGridX());
                    player.setGridY(world.getNonplayerList().get(c).getGridY());
                }
            }
            player.setHitbox(new Rectangle(player.getGridX()*100, player.getGridY()*100, 100, 100));
            System.out.println(player.getHitbox().getX() + " " + player.getHitbox().getY());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(-100, -100, 99999,99999);
        g.setFont(new Font("Times New Roman", Font.BOLD, 25));
        for(int c=0; c<world.getNonplayerList().size(); c++) {
            int x = world.getNonplayerList().get(c).getGridX()*100-((int)player.getHitbox().getX());
            int y = world.getNonplayerList().get(c).getGridY()*100-(int)player.getHitbox().getY();
            if(player.getHitbox().getX()<=0)
                x=world.getNonplayerList().get(c).getGridX()*100;
            if(player.getHitbox().getX()>=800)
                x=world.getNonplayerList().get(c).getGridX()*100-800;
            if(player.getHitbox().getY()<=0)
                y=world.getNonplayerList().get(c).getGridY()*100;
            if(player.getHitbox().getY()>=800)
                y=world.getNonplayerList().get(c).getGridY()*100-800;
            if(world.getNonplayerList().get(c).getType()==0) {
                g.setColor(Color.lightGray);
                g.fillRect(x, y, 100, 100);
            }
            if(world.getNonplayerList().get(c).getType()==Constants.WALL) {
                g.setColor(Color.RED);
                g.fillRect(x, y, 100, 100);
            }
            if(world.getNonplayerList().get(c).getType()==Constants.GOAL) {
                g.setColor(Color.GREEN);
                g.fillRect(x, y, 100, 100);
            }
            if(world.getNonplayerList().get(c).getType()==Constants.PLAYER) {
                g.setColor(Color.BLUE);
                g.fillRect(x, y, 100, 100);
            }
            //g.setColor(Color.black);
            //g.drawRect(world.getNonplayerList().get(c).getGridX()*100, world.getNonplayerList().get(c).getGridY()*100, 100, 100);
            //g.drawString(String.valueOf(c),world.getNonplayerList().get(c).getGridX()*100, world.getNonplayerList().get(c).getGridY()*100+100);
        }
        g.setColor(Color.pink);
        System.out.println(player.getHitbox().getX() + " "  + player.getHitbox().getY());
        int x = 0;
        int y = 0;
        if(player.getHitbox().getX()<0)
            x=(int)player.getHitbox().getX();
        if(player.getHitbox().getX()>800)
            x=(int)player.getHitbox().getX()-800;
        if(player.getHitbox().getY()<0)
            y=(int)player.getHitbox().getY();
        if(player.getHitbox().getY()>800)
            y=(int)player.getHitbox().getY()-800;
        g.fillOval(x+500, y+500, 50, 50);
        player.setHurtbox(new Rectangle(x+500, y+500, 50, 50));
        g.fillRect((int)player.getHitbox().getX(), (int)player.getHitbox().getY(), 10, 10);

        repaint();
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
        char dir = e.getKeyChar();
        System.out.println("w");
        if (dir == 'w') {
            System.out.println("w");
            player.setHitbox(new Rectangle((int)player.getHitbox().getX(), (int)player.getHitbox().getY()- Constants.PLAYER_SPEED, (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
        }
        if (dir == 'a') {
            player.setHitbox(new Rectangle((int)player.getHitbox().getX()- Constants.PLAYER_SPEED, (int)player.getHitbox().getY(), (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
        }
        if (dir == 'd') {
            player.setHitbox(new Rectangle((int)player.getHitbox().getX()+ Constants.PLAYER_SPEED, (int)player.getHitbox().getY(), (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
        }
        if (dir == 's') {
            player.setHitbox(new Rectangle((int)player.getHitbox().getX(), (int)player.getHitbox().getY()+ Constants.PLAYER_SPEED, (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

        int waitToUpdate = (1000/updatesPerSecond);

        long startTime = System.nanoTime();

        int updateCount = 0;
        while(true){
            boolean shouldRepaint = false;

            long currentTime = System.nanoTime();

            long updatesNeeded = ((currentTime-startTime)/1000000)/waitToUpdate;
            for(long x = updateCount; x<updatesNeeded; x++){
                update();
                shouldRepaint = true;
                updateCount++;
            }

            if(shouldRepaint)
                repaint();

            try{
                Thread.sleep(5);
            }
            catch(Exception e){
                System.out.println("Error sleeping in run method: " +e.getMessage());
            }
        }
    }
    @Override
    public void addNotify(){
        super.addNotify();
        requestFocus();
    }
}
