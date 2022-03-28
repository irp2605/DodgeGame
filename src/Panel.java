import com.sun.org.apache.bcel.internal.classfile.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

public class Panel extends JPanel implements KeyListener, Runnable {
    private int currWorld =0;
    private boolean won = false;
    private boolean gameWon = false;
    private Player player;
    private World world;
    private ArrayList<World> worlds;
    private ArrayList<linearEnemy> lins;
    private ArrayList<pattenEnemy> pats;
    private int updatesPerSecond = 30;
    private Thread t;
    private int playerWidth = 0;
    private int playerHeight = 0;
    private int playerX = 0;
    private int playerY = 0;
    private int screenX = 0;
    private int screenY = 0;
    private int screenWidth = 500;
    private int screenHeight = 500;
    private int worldWidth = 4000;
    private int worldHeight = 4000;
    private int cellHeight = 100;
    private int cellWidth = 100;
    public Panel() {
        setSize(500,500);
        addKeyListener(this);
        Thread t = new Thread(this);
        t.start();
        worlds = new ArrayList<World>();
        lins = new ArrayList<linearEnemy>();
        pats = new ArrayList<pattenEnemy>();
        try {
            worlds.add(new World(new File("C:\\Users\\irp26\\Documents\\CS3\\DodgeGame\\src\\level1.txt")));
            worlds.add(new World(new File("C:\\Users\\irp26\\Documents\\CS3\\DodgeGame\\src\\test2.txt")));
            world = worlds.get(currWorld);
            world.textConverter();
            player = new Player(-1,-1,0, 3);
            for(int c=0; c<world.getNonplayerList().size(); c++) {

                if(world.getNonplayerList().get(c).getType()== Constants.PLAYER) {
                    player.setGridX(world.getNonplayerList().get(c).getGridX());
                    player.setGridY(world.getNonplayerList().get(c).getGridY());
                }
                if(world.getNonplayerList().get(c).getType()==Constants.LINEAR_ENEMY) {

                    lins.add(new linearEnemy(Constants.UP, Constants.LINEAR_ENEMY, c%40, c/40, Constants.LINEAR_SPEED, 40, 40));
                    System.out.println(lins.get(lins.size()-1).getRectangle().getX());
                    lins.get(lins.size()-1).setRectangle(new Rectangle((int)(lins.get(lins.size()-1).getRectangle().getX()+20), (int)(lins.get(lins.size()-1).getRectangle().getY()+20), 40, 40));
                    System.out.println(lins.get(lins.size()-1).getRectangle().getX() + " " + lins.get(lins.size()-1).getRectangle().getY());
                }
            }
            player.setHitbox(new Rectangle(player.getGridX()*100, player.getGridY()*100, 40, 40));
            int playerWidth = (int)player.getHitbox().getWidth();
            int playerHeight = (int)player.getHitbox().getHeight();
            int playerX = (int)player.getHitbox().getX();
            int playerY = (int)player.getHitbox().getY();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void paint(Graphics g) {

        g.setColor(Color.black);
        playerX=(int)player.getHitbox().getX();
        playerY=(int)player.getHitbox().getY();
        int pixelsLeftRightPlayer = (screenWidth-playerWidth)/2;
        int pixelsAboveBelowPlayer = (screenHeight-playerHeight)/2;
        int maxX = worldWidth-screenWidth;
        int tempX = playerX-pixelsLeftRightPlayer;
        update();
        screenX=(tempX<0)?0:(tempX>maxX)?maxX:tempX;

        int maxY = worldHeight-screenHeight;
        int tempY = playerY-pixelsAboveBelowPlayer;
        screenY=(tempY<0)?0:(tempY>maxY)?maxY:tempY;
        int startRow = screenY/cellHeight;
        int startCol = screenX/cellWidth;
        int endRow =(int) (startRow+Math.ceil(screenHeight/cellHeight));
        int endCol = (int) (startCol+Math.ceil(screenWidth/cellWidth));
        g.fillRect(-100, -100, 999999,999999);
        g.setFont(new Font("Times New Roman", Font.BOLD, 25));
        for(int c=0; c<world.getNonplayerList().size(); c++) {
            int x = world.getNonplayerList().get(c).getGridX()*100-((int)player.getHitbox().getX());
            int y = world.getNonplayerList().get(c).getGridY()*100-(int)player.getHitbox().getY();
            if(world.getNonplayerList().get(c).getType()==0) {
                g.setColor(Color.lightGray);
                g.fillRect(world.getNonplayerList().get(c).getGridX()*100-screenX, world.getNonplayerList().get(c).getGridY()*100-screenY, 100, 100);
            }
            if(world.getNonplayerList().get(c).getType()==Constants.LINEAR_ENEMY) {
                g.setColor(Color.MAGENTA);
                g.fillRect(world.getNonplayerList().get(c).getGridX()*100-screenX, world.getNonplayerList().get(c).getGridY()*100-screenY, 100, 100);
            }
            if(world.getNonplayerList().get(c).getType()==Constants.WALL) {
                g.setColor(Color.RED);
                g.fillRect(world.getNonplayerList().get(c).getGridX()*100-screenX, world.getNonplayerList().get(c).getGridY()*100-screenY, 100, 100);
            }
            if(world.getNonplayerList().get(c).getType()==Constants.GOAL) {
                g.setColor(Color.GREEN);
                g.fillRect(world.getNonplayerList().get(c).getGridX()*100-screenX, world.getNonplayerList().get(c).getGridY()*100-screenY, 100, 100);
            }
            if(world.getNonplayerList().get(c).getType()==Constants.PLAYER) {
                g.setColor(Color.BLUE);
                g.fillRect(world.getNonplayerList().get(c).getGridX()*100-screenX, world.getNonplayerList().get(c).getGridY()*100-screenY, 100, 100);
            }
            g.setColor(Color.black);
            g.drawRect((int)world.getNonplayerList().get(c).getRectangle().getX()-screenX, (int)world.getNonplayerList().get(c).getRectangle().getY()-screenY, 100, 100);
            //g.drawString(String.valueOf(c),world.getNonplayerList().get(c).getGridX()*100, world.getNonplayerList().get(c).getGridY()*100+100);
        }
        g.setColor(Color.yellow);
        for(int c=0; c<lins.size(); c++) {
            g.fillRect((int)lins.get(c).getRectangle().getX()-screenX, (int)lins.get(c).getRectangle().getY()-screenY, 40, 40);
        }
        g.setColor(Color.pink);
        int x = 250;
        int y = 250;
        if(screenX==0)
            x=(int)player.getHitbox().getX();
        if(screenX==maxX)
            x=(int)player.getHitbox().getX()-3500;
        if(screenY==0)
            y=(int)player.getHitbox().getY();
        if(screenY==maxY)
            y=(int)player.getHitbox().getY()-3500;
        g.fillRect(x, y, 40, 40);
        player.setHurtbox(new Rectangle(x, y, 40, 40));
        g.fillRect((int)player.getHitbox().getX(), (int)player.getHitbox().getY(), 40, 40);
        if(won && currWorld== worlds.size()-1) {
            System.out.println("Detected last");
            g.setColor(Color.black);
            g.fillRect(0,0,1000,1000);
            g.setColor(Color.white);
            g.drawString("GAME IS WON !!!!", 100, 100);

        }
    }

    public void update() {
        for(int z=0; z<lins.size(); z++) {
            if(lins.get(z).getRectangle().intersects(player.getHitbox())) {
                player.setLives(player.getLives()-1);
                System.out.println(player.getLives());
            }
        }
        world.checkInteraction(player);
        if(world.levelWon(player))
            won=true;
        if(won) {
            System.out.println("Level Won!!!");
            if(currWorld!=worlds.size()-1) {
                won = false;
                currWorld++;
                world = worlds.get(currWorld);
                world.textConverter();
                for (int c = 0; c < world.getNonplayerList().size(); c++) {
                    if (world.getNonplayerList().get(c).getType() == Constants.PLAYER) {
                        player.setGridX(world.getNonplayerList().get(c).getGridX());
                        player.setGridY(world.getNonplayerList().get(c).getGridY());
                    }
                }
                player.setHitbox(new Rectangle(player.getGridX() * 100, player.getGridY() * 100, 40, 40));
            }
        }
    }

    public void reset() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char dir = e.getKeyChar();
        if (dir == 'w') {
            player.setDirection(Constants.UP);
            player.setHitbox(new Rectangle((int)player.getHitbox().getX(), (int)player.getHitbox().getY()- Constants.PLAYER_SPEED, (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
            world.checkInteraction(player);
        }
        if (dir == 'a') {
            player.setDirection(Constants.LEFT);
            player.setHitbox(new Rectangle((int)player.getHitbox().getX()- Constants.PLAYER_SPEED, (int)player.getHitbox().getY(), (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
            world.checkInteraction(player);
        }
        if (dir == 'd') {
            player.setDirection(Constants.RIGHT);
            player.setHitbox(new Rectangle((int)player.getHitbox().getX()+ Constants.PLAYER_SPEED, (int)player.getHitbox().getY(), (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
            world.checkInteraction(player);
        }
        if (dir == 's') {
            player.setDirection(Constants.DOWN);
            player.setHitbox(new Rectangle((int)player.getHitbox().getX(), (int)player.getHitbox().getY()+ Constants.PLAYER_SPEED, (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
            world.checkInteraction(player);
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
