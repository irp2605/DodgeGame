import com.sun.org.apache.bcel.internal.Const;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class World {
    public ArrayList<NonPlayer> getNonplayerList() {
        return NonplayerList;
    }

    private ArrayList<NonPlayer> NonplayerList;
    private File f;
    private Scanner fileReader;
    private boolean won;

    public World(File f) {
        NonplayerList = new ArrayList<NonPlayer>();
        try {
            this.f = f;
            fileReader = new Scanner(f);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        won = false;
    }
    public void textConverter() {
        try{
            int curr = -1;
            int c=0;
            System.out.println(fileReader.hasNextInt());
            while(fileReader.hasNextInt()) {
                curr=fileReader.nextInt();


                if(curr==0) {
                    NonplayerList.add(new NonPlayer(0, 0, c%40, c/40, 0, 100, 100));
                }
                if(curr== Constants.LINEAR_ENEMY) {
                    NonplayerList.add(new NonPlayer(0, 1, c%40, c/40, 0, 100, 100));
                }
                if(curr==4) {
                    NonplayerList.add(new Wall(0, Constants.WALL, c%40, c/40, 0, 100, 100));
                }

                if(curr==5) {
                    NonplayerList.add(new Goal(0, Constants.GOAL, c%40, c/40, 0, 100, 100));
                }
                if(curr==6) {
                    NonplayerList.add(new NonPlayer(0, Constants.PLAYER, c%40, c/40, 0, 100, 100));
                }
                c++;
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void checkInteraction(Player p) {
        for(int i =0; i<NonplayerList.size(); i++) {
            if(NonplayerList.get(i).getRectangle().intersects(p.getHitbox())) {
                if(NonplayerList.get(i).getType()==Constants.WALL) {
                    if (p.getDirection() == Constants.UP)
                        p.setHitbox(new Rectangle((int) p.getHitbox().getX(), (int) p.getHitbox().getY() + Constants.PLAYER_SPEED, (int) p.getHitbox().getWidth(), (int) p.getHitbox().getHeight()));
                    else if (p.getDirection() == Constants.DOWN)
                        p.setHitbox(new Rectangle((int) p.getHitbox().getX(), (int) p.getHitbox().getY() - Constants.PLAYER_SPEED, (int) p.getHitbox().getWidth(), (int) p.getHitbox().getHeight()));
                    else if (p.getDirection() == Constants.RIGHT)
                        p.setHitbox(new Rectangle((int) p.getHitbox().getX() - Constants.PLAYER_SPEED, (int) p.getHitbox().getY(), (int) p.getHitbox().getWidth(), (int) p.getHitbox().getHeight()));
                    else if (p.getDirection() == Constants.LEFT)
                        p.setHitbox(new Rectangle((int) p.getHitbox().getX() + Constants.PLAYER_SPEED, (int) p.getHitbox().getY(), (int) p.getHitbox().getWidth(), (int) p.getHitbox().getHeight()));
                }
            }
        }
    }

    public boolean levelWon(Player p) {
        for(int i =0; i<NonplayerList.size(); i++) {
            if(NonplayerList.get(i).getType()==Constants.GOAL){
                if (NonplayerList.get(i).getRectangle().intersects(p.getHitbox())) {
                    return true;
                }
            }
        }
        return false;
    }

}
