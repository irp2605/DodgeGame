import com.sun.org.apache.bcel.internal.classfile.Constant;
import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Panel extends JPanel implements KeyListener, Runnable {
    private int currWorld =0;
    private boolean won = false;
    private boolean gameWon = false;
    private Player player;
    private World world;
    private ArrayList<World> worlds;
    private ArrayList<linearEnemy> lins;
    private ArrayList<pattenEnemy> pats;
    private boolean playerHit;
    int invunCount;
    private Thread t;
    private int playerWidth = 0;
    private int playerHeight = 0;
    private int playerX = 0;
    private int playerY = 0;
    private int screenX = 0;
    private int screenY = 0;
    private boolean gameOver;
    public Panel() {
        playerHit=false;
        invunCount = 0;
        gameOver=false;
        setSize(500,500);
        addKeyListener(this);
        Thread t = new Thread(this);
        worlds = new ArrayList<World>();
        lins = new ArrayList<linearEnemy>();
        pats = new ArrayList<pattenEnemy>();
        try {
            worlds.add(new World(new File("C:\\Users\\B1003721\\Desktop\\DodgeGame\\src\\level1.txt")));
            worlds.add(new World(new File("C:\\Users\\B1003721\\Desktop\\DodgeGame\\src\\level2.txt")));
            worlds.add(new World(new File("C:\\Users\\B1003721\\Desktop\\DodgeGame\\src\\level3.txt")));
            worlds.add(new World(new File("C:\\Users\\B1003721\\Desktop\\DodgeGame\\src\\level4.txt")));
            worlds.add(new World(new File("C:\\Users\\B1003721\\Desktop\\DodgeGame\\src\\level5.txt")));
            world = worlds.get(currWorld);
            world.textConverter();
            player = new Player(-1,-1,0, 5);
            for(int c=0; c<world.getNonplayerList().size(); c++) {
                if(world.getNonplayerList().get(c).getType()== Constants.PLAYER) {
                    player.setGridX(world.getNonplayerList().get(c).getGridX());
                    player.setGridY(world.getNonplayerList().get(c).getGridY());
                }
                if(world.getNonplayerList().get(c).getType()==Constants.LINEAR_ENEMY) {
                    lins.add(new linearEnemy(Constants.UP, Constants.LINEAR_ENEMY, c%40, c/40, Constants.LINEAR_SPEED, 40, 40));

                    lins.get(lins.size()-1).setRectangle(new Rectangle((int)(lins.get(lins.size()-1).getRectangle().getX()+30), (int)(lins.get(lins.size()-1).getRectangle().getY()+30), 40, 40));

                }
                if(world.getNonplayerList().get(c).getType()==Constants.PATTERN_ENEMY) {
                    pats.add(new pattenEnemy(Constants.UP, Constants.PATTERN_ENEMY, c%40, c/40, Constants.PATTERN_SPEED, 40, 40));
                    pats.get(pats.size()-1).setRectangle(new Rectangle((int)(pats.get(pats.size()-1).getRectangle().getX()+30), (int)(pats.get(pats.size()-1).getRectangle().getY()+30), 40, 40));

                }
            }
            player.setHitbox(new Rectangle(player.getGridX()*100, player.getGridY()*100, 40, 40));
            int playerWidth = (int)player.getHitbox().getWidth();
            int playerHeight = (int)player.getHitbox().getHeight();
            int playerX = (int)player.getHitbox().getX();
            int playerY = (int)player.getHitbox().getY();
            t.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void paint(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0,0,500,500);
        g.setColor(Color.black);
        playerX=(int)player.getHitbox().getX();
        playerY=(int)player.getHitbox().getY();
        int pixelsLeftRightPlayer = (500-playerWidth)/2;
        int pixelsAboveBelowPlayer = (500-playerHeight)/2;
        int maxX = 4000-500;
        int tempX = playerX-pixelsLeftRightPlayer;

        screenX=(tempX<0)?0:(tempX>maxX)?maxX:tempX;

        int maxY = 4000-500;
        int tempY = playerY-pixelsAboveBelowPlayer;
        screenY=(tempY<0)?0:(tempY>maxY)?maxY:tempY;
        int startRow = screenY/100;
        int startCol = screenX/100;
        int endRow =(int) (startRow+Math.ceil(500/100));
        int endCol = (int) (startCol+Math.ceil(500/100));
        g.fillRect(-100, -100, 999999,999999);
        g.setFont(new Font("Times New Roman", Font.BOLD, 25));
        for(int c=0; c<world.getNonplayerList().size(); c++) {
            if(c>world.getNonplayerList().size()-1)
                break;
            int x = world.getNonplayerList().get(c).getGridX()*100-((int)player.getHitbox().getX());
            int y = world.getNonplayerList().get(c).getGridY()*100-(int)player.getHitbox().getY();
            if(world.getNonplayerList().get(c).getType()==0) {
                g.setColor(Color.lightGray);
                g.fillRect(world.getNonplayerList().get(c).getGridX()*100-screenX, world.getNonplayerList().get(c).getGridY()*100-screenY, 100, 100);
            }
            if(c>world.getNonplayerList().size()-1)
                break;
            if(world.getNonplayerList().get(c).getType()==Constants.LINEAR_ENEMY) {
                g.setColor(Color.lightGray);
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
        g.setColor(Color.WHITE);
        for(int c=0; c<pats.size(); c++) {
            g.fillRect((int)pats.get(c).getRectangle().getX()-screenX, (int)pats.get(c).getRectangle().getY()-screenY, 40, 40);
        }
        for(int c=0; c<world.getLaserList().size(); c++) {
            g.setColor(world.getLaserList().get(c).getLaserColor());
            g.fillRect((int)world.getLaserList().get(c).getRectangle().getX()-screenX, (int)world.getLaserList().get(c).getRectangle().getY()-screenY, 50, 50);
            if(world.getLaserList().get(c).isFiring()) {
                g.setColor(Color.red);
                g.fillRect((int)world.getLaserList().get(c).getBeam().getX()- screenX, (int)world.getLaserList().get(c).getBeam().getY()-screenY, (int)world.getLaserList().get(c).getBeam().getWidth(), (int)world.getLaserList().get(c).getBeam().getHeight());
            }
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
        //g.fillRect((int)player.getHitbox().getX(), (int)player.getHitbox().getY(), 40, 40);
        if(won && currWorld== worlds.size()-1) {
            System.out.println("Detected last");
            gameOver=true;
            g.setColor(Color.black);
            g.fillRect(0,0,1000,1000);
            g.setColor(Color.white);
            g.drawString("GAME IS WON !!!!", 150, 200);

        }
        if(player.getLives()==0) {
            gameOver=true;
            g.setColor(Color.black);
            g.fillRect(0,0,1000,1000);
            g.setColor(Color.white);
            g.drawString("GAME OVER, YA IDIOT YUK YUK YUK", 20, 100);
        }
        if(!gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Times New Roman", Font.BOLD, 20));
            g.drawString("Lives: " + player.getLives(), 10, 20);
        }
    }

    public void update() {

        if (player.isUp()) {
            Player tempPleyer = new Player(0,0,0,0);
            tempPleyer.setHitbox(new Rectangle((int)player.getHitbox().getX(), (int)player.getHitbox().getY()- Constants.PLAYER_SPEED, (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
            boolean worldCheck = false;
            for(int c=0;c<world.getNonplayerList().size();c++) {
                if(world.getNonplayerList().get(c).getType()==Constants.WALL || world.getNonplayerList().get(c).getType()==Constants.LASER) {
                    if(world.getNonplayerList().get(c).getRectangle().intersects(tempPleyer.getHitbox()))
                        worldCheck=true;
                }
            }
            if(!worldCheck) {
                player.setHitbox(new Rectangle((int)player.getHitbox().getX(), (int)player.getHitbox().getY()- Constants.PLAYER_SPEED, (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
                world.checkInteraction(player);
            }

        }
        if (player.isLeft()) {
            Player tempPleyer = new Player(0,0,0,0);
            tempPleyer.setHitbox(new Rectangle((int)player.getHitbox().getX()- Constants.PLAYER_SPEED, (int)player.getHitbox().getY(), (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
            boolean worldCheck = false;
            for(int c=0;c<world.getNonplayerList().size();c++) {
                if(world.getNonplayerList().get(c).getType()==Constants.WALL || world.getNonplayerList().get(c).getType()==Constants.LASER) {
                    if(world.getNonplayerList().get(c).getRectangle().intersects(tempPleyer.getHitbox()))
                        worldCheck=true;
                }
            }
            if(!worldCheck) {
                player.setHitbox(new Rectangle((int)player.getHitbox().getX()- Constants.PLAYER_SPEED, (int)player.getHitbox().getY(), (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
                world.checkInteraction(player);
            }

        }
        if (player.isRight()) {
            Player tempPleyer = new Player(0,0,0,0);
            tempPleyer.setHitbox(new Rectangle((int)player.getHitbox().getX()+ Constants.PLAYER_SPEED, (int)player.getHitbox().getY(), (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
            boolean worldCheck = false;
            for(int c=0;c<world.getNonplayerList().size();c++) {
                if(world.getNonplayerList().get(c).getType()==Constants.WALL || world.getNonplayerList().get(c).getType()==Constants.LASER) {
                    if(world.getNonplayerList().get(c).getRectangle().intersects(tempPleyer.getHitbox()))
                        worldCheck=true;
                }
            }
            if(!worldCheck) {
                player.setHitbox(new Rectangle((int)player.getHitbox().getX()+ Constants.PLAYER_SPEED, (int)player.getHitbox().getY(), (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));
                world.checkInteraction(player);
            }

        }
        if (player.isDown()) {
            Player tempPleyer = new Player(0,0,0,0);
            tempPleyer.setHitbox(new Rectangle((int)player.getHitbox().getX(), (int)player.getHitbox().getY()+ Constants.PLAYER_SPEED, (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight() ));
            boolean worldCheck = false;
            for(int c=0;c<world.getNonplayerList().size();c++) {
                if(world.getNonplayerList().get(c).getType()==Constants.WALL || world.getNonplayerList().get(c).getType()==Constants.LASER) {
                    if(world.getNonplayerList().get(c).getRectangle().intersects(tempPleyer.getHitbox()))
                        worldCheck=true;
                }
            }
            if(!worldCheck) {
                player.setHitbox(new Rectangle((int) player.getHitbox().getX(), (int) player.getHitbox().getY() + Constants.PLAYER_SPEED, (int) player.getHitbox().getWidth(), (int) player.getHitbox().getHeight()));
                world.checkInteraction(player);
            }
        }




        for(int z=0; z<lins.size(); z++) {
            if(!playerHit) {
                if (lins.get(z).getRectangle().intersects(player.getHitbox())) {
                    player.setLives(player.getLives() - 1);
                    for(int c=0; c<world.getNonplayerList().size(); c++) {
                        if (world.getNonplayerList().get(c).getType() == Constants.PLAYER) {
                            player.setGridX(world.getNonplayerList().get(c).getGridX());
                            player.setGridY(world.getNonplayerList().get(c).getGridY());
                            player.setHitbox(new Rectangle(player.getGridX()*100, player.getGridY()*100, 40, 40));
                        }
                    }
                    playerHit = true;
                    System.out.println(player.getLives());
                }
            }
        }
        for(int z=0; z<pats.size(); z++) {
            if(!playerHit) {
                if (pats.get(z).getRectangle().intersects(player.getHitbox())) {
                    player.setLives(player.getLives() - 1);
                    for(int c=0; c<world.getNonplayerList().size(); c++) {
                        if (world.getNonplayerList().get(c).getType() == Constants.PLAYER) {
                            player.setGridX(world.getNonplayerList().get(c).getGridX());
                            player.setGridY(world.getNonplayerList().get(c).getGridY());
                            player.setHitbox(new Rectangle(player.getGridX()*100, player.getGridY()*100, 40, 40));
                        }
                    }
                    playerHit = true;
                    System.out.println(player.getLives());
                }
            }
        }
        for(int z=0; z<world.getLaserList().size(); z++) {
            if(!playerHit) {
                if (world.getLaserList().get(z).getBeam().intersects(player.getHitbox()) && world.getLaserList().get(z).isFiring()) {

                    player.setLives(player.getLives() - 1);
                    for(int c=0; c<world.getNonplayerList().size(); c++) {
                        if (world.getNonplayerList().get(c).getType() == Constants.PLAYER) {
                            player.setGridX(world.getNonplayerList().get(c).getGridX());
                            player.setGridY(world.getNonplayerList().get(c).getGridY());
                            player.setHitbox(new Rectangle(player.getGridX()*100, player.getGridY()*100, 40, 40));
                        }
                    }
                    playerHit = true;
                    System.out.println(player.getLives());
                }
            }
        }
        for(int c=0; c<lins.size(); c++) {
            lins.get(c).mover(world);
        }
        for(int c=0;c<pats.size();c++) {
            pats.get(c).mover(world);
        }
        for(int c=0; c<world.getLaserList().size(); c++) {
            world.getLaserList().get(c).chargeLaser();
        }

        world.checkInteraction(player);
        if(world.levelWon(player)) {
            won = true;
            try {
                Thread.sleep(100);
            }
            catch (Exception e){

            }
        }
        if(won) {
            System.out.println("Level Won!!!");
            if(currWorld!=worlds.size()-1) {
                won = false;
                currWorld++;
                world = worlds.get(currWorld);
                player.setLives(player.getLives()+1);
                world.textConverter();
                lins = new ArrayList<>();
                pats = new ArrayList<>();
                for (int c = 0; c < world.getNonplayerList().size(); c++) {
                    if (world.getNonplayerList().get(c).getType() == Constants.PLAYER) {
                        player.setGridX(world.getNonplayerList().get(c).getGridX());
                        player.setGridY(world.getNonplayerList().get(c).getGridY());
                    }
                    if(world.getNonplayerList().get(c).getType()==Constants.LINEAR_ENEMY) {
                        lins.add(new linearEnemy(Constants.UP, Constants.LINEAR_ENEMY, c%40, c/40, Constants.LINEAR_SPEED, 40, 40));

                        lins.get(lins.size()-1).setRectangle(new Rectangle((int)(lins.get(lins.size()-1).getRectangle().getX()+30), (int)(lins.get(lins.size()-1).getRectangle().getY()+30), 40, 40));

                    }
                    if(world.getNonplayerList().get(c).getType()==Constants.PATTERN_ENEMY) {
                        pats.add(new pattenEnemy(Constants.UP, Constants.PATTERN_ENEMY, c%40, c/40, Constants.PATTERN_SPEED, 40, 40));
                        pats.get(pats.size()-1).setRectangle(new Rectangle((int)(pats.get(pats.size()-1).getRectangle().getX()+30), (int)(pats.get(pats.size()-1).getRectangle().getY()+30), 40, 40));

                    }
                }
                player.setHitbox(new Rectangle(player.getGridX() * 100, player.getGridY() * 100, 40, 40));
            }
        }
    }

    public void reset() {
        gameOver=false;
        lins = new ArrayList<linearEnemy>();
        pats = new ArrayList<pattenEnemy>();
        try {
            world = worlds.get(0);
            currWorld=0;
            won=false;
            gameWon=false;
            player = new Player(-1,-1,0, 5);
            for(int c=0; c<world.getNonplayerList().size(); c++) {
                if(world.getNonplayerList().get(c).getType()== Constants.PLAYER) {
                    player.setGridX(world.getNonplayerList().get(c).getGridX());
                    player.setGridY(world.getNonplayerList().get(c).getGridY());
                }
                if(world.getNonplayerList().get(c).getType()==Constants.LINEAR_ENEMY) {
                    lins.add(new linearEnemy(Constants.UP, Constants.LINEAR_ENEMY, c%40, c/40, Constants.LINEAR_SPEED, 40, 40));
                    lins.get(lins.size()-1).setRectangle(new Rectangle((int)(lins.get(lins.size()-1).getRectangle().getX()+30), (int)(lins.get(lins.size()-1).getRectangle().getY()+30), 40, 40));
                }
                if(world.getNonplayerList().get(c).getType()==Constants.PATTERN_ENEMY) {
                    pats.add(new pattenEnemy(Constants.UP, Constants.PATTERN_ENEMY, c%40, c/40, Constants.PATTERN_SPEED, 40, 40));
                    pats.get(pats.size()-1).setRectangle(new Rectangle((int)(pats.get(pats.size()-1).getRectangle().getX()+30), (int)(pats.get(pats.size()-1).getRectangle().getY()+30), 40, 40));

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
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char dir = e.getKeyChar();
        if(dir=='r') {
            if(gameOver)
                reset();
        }
        if(dir=='`')
            won = true;
        if (dir == 'w') {
            player.setUp(true);
            // player.setHitbox(new Rectangle((int)player.getHitbox().getX(), (int)player.getHitbox().getY()- Constants.PLAYER_SPEED, (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));

        }
        if (dir == 'a') {
            player.setLeft(true);
            // player.setHitbox(new Rectangle((int)player.getHitbox().getX()- Constants.PLAYER_SPEED, (int)player.getHitbox().getY(), (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));

        }
        if (dir == 'd') {
            player.setRight(true);
            //player.setHitbox(new Rectangle((int)player.getHitbox().getX()+ Constants.PLAYER_SPEED, (int)player.getHitbox().getY(), (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));

        }
        if (dir == 's') {
            player.setDown(true);
            // player.setHitbox(new Rectangle((int)player.getHitbox().getX(), (int)player.getHitbox().getY()+ Constants.PLAYER_SPEED, (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char dir = e.getKeyChar();
        if (dir == 'w') {
            player.setUp(false);
            // player.setHitbox(new Rectangle((int)player.getHitbox().getX(), (int)player.getHitbox().getY()- Constants.PLAYER_SPEED, (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));

        }
        if (dir == 'a') {
            player.setLeft(false);
            // player.setHitbox(new Rectangle((int)player.getHitbox().getX()- Constants.PLAYER_SPEED, (int)player.getHitbox().getY(), (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));

        }
        if (dir == 'd') {
            player.setRight(false);
            //player.setHitbox(new Rectangle((int)player.getHitbox().getX()+ Constants.PLAYER_SPEED, (int)player.getHitbox().getY(), (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));

        }
        if (dir == 's') {
            player.setDown(false);
            // player.setHitbox(new Rectangle((int)player.getHitbox().getX(), (int)player.getHitbox().getY()+ Constants.PLAYER_SPEED, (int)player.getHitbox().getWidth(), (int)player.getHitbox().getHeight()));

        }
    }

    @Override
    public void run() {

        while(true) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(!gameOver) {
                repaint();
                update();
                if (playerHit) {
                    invunCount++;
                }
                if (invunCount == 150) {
                    playerHit = false;
                    invunCount = 0;
                }
                try {
                    Thread.sleep(5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void addNotify(){
        super.addNotify();
        requestFocus();
    }
}