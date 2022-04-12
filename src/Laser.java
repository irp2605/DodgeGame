

import java.awt.*;
import java.util.ArrayList;

public class Laser extends NonPlayer {
    public Rectangle getBeam() {
        return beam;
    }

    private Rectangle beam;
    private int charge;

    public Color getLaserColor() {
        return laserColor;
    }

    private Color laserColor;


    public boolean isFiring() {
        return firing;
    }

    private boolean firing;
    public Laser(int direction, int type, int gridX, int gridY, int speed, int width, int height) {
        super(direction, type, gridX, gridY, speed, width, height);
        setRectangle(new Rectangle((int)getRectangle().getX()+25, (int)getRectangle().getY()+25,50,50));
        firing=false;
        charge= (int)(Math.random()*99);
    }
    public void setBeam(ArrayList<NonPlayer> nonPlayerList) {
        //System.out.println("settin da beam " +  getGridY());
        int closestStopper=99999;
        if(getDirection()==Constants.RIGHT) {
            for(int c=0; c<nonPlayerList.size(); c++) {
                if(nonPlayerList.get(c).getGridY()==getGridY() && (nonPlayerList.get(c).getGridX()-getGridX()<closestStopper) && (nonPlayerList.get(c).getGridX()-getGridX()>0) && !(nonPlayerList.get(c).getType() == Constants.LINEAR_ENEMY || nonPlayerList.get(c).getType() == Constants.PATTERN_ENEMY || nonPlayerList.get(c).getType() == 0)) {
                    closestStopper = nonPlayerList.get(c).getGridX() - getGridX();
                }
            }
            beam = new Rectangle((int)getRectangle().getX()+50, (int)getRectangle().getY()+10, closestStopper*100, 30);
        }
        else if(getDirection()==Constants.DOWN) {

            for(int c=0; c<nonPlayerList.size(); c++) {
                if(nonPlayerList.get(c).getGridX()==getGridX() && (nonPlayerList.get(c).getGridY()-getGridY()<closestStopper) && (nonPlayerList.get(c).getGridY()-getGridY()>0) && !(nonPlayerList.get(c).getType() == Constants.LINEAR_ENEMY || nonPlayerList.get(c).getType() == Constants.PATTERN_ENEMY || nonPlayerList.get(c).getType() == 0)) {
                    System.out.println(nonPlayerList.get(c).getGridY() + " " + getGridY());
                    closestStopper = nonPlayerList.get(c).getGridY() - getGridY();
                }
            }
            beam = new Rectangle((int)getRectangle().getX()+10, (int)getRectangle().getY()+50, 30, closestStopper*100);
        }
    }
    public int fixDirection(ArrayList<NonPlayer> nonPlayerList) {
        Boolean tempBool=true;
        //checking if can shoot right
        for(int c=0; c<nonPlayerList.size(); c++) {
            if(getGridX()+1==nonPlayerList.get(c).getGridX() && getGridY()==nonPlayerList.get(c).getGridY() && !(nonPlayerList.get(c).getType() == Constants.LINEAR_ENEMY || nonPlayerList.get(c).getType() == Constants.PATTERN_ENEMY || nonPlayerList.get(c).getType() == 0)) {
                tempBool=false;
                break;
            }
        }
        if(tempBool) {
            System.out.println("Right dir ");
            return Constants.RIGHT;
        }
        //check if can shoot down
        tempBool=true;
        for(int c=0; c<nonPlayerList.size(); c++) {
            if(getGridY()+1==nonPlayerList.get(c).getGridY() && getGridX()==nonPlayerList.get(c).getGridX() && !(nonPlayerList.get(c).getType() == Constants.LINEAR_ENEMY || nonPlayerList.get(c).getType() == Constants.PATTERN_ENEMY || nonPlayerList.get(c).getType() == 0)) {

                tempBool=false;
                break;
            }
        }
        if(tempBool)
            return Constants.DOWN;
        //checking if can shoot left
        tempBool=true;

        for(int c=0; c<nonPlayerList.size(); c++) {
            if(getGridX()-1==nonPlayerList.get(c).getGridX() && getGridY()==nonPlayerList.get(c).getGridY() && !(nonPlayerList.get(c).getType() == Constants.LINEAR_ENEMY || nonPlayerList.get(c).getType() == Constants.PATTERN_ENEMY || nonPlayerList.get(c).getType() == 0)) {
                tempBool=false;
                break;
            }
        }
        if(tempBool)
            return Constants.LEFT;
        return Constants.UP;

    }
    public void chargeLaser(){
        if(!firing)
            charge++;
        if(firing) {
            charge--;
            if(charge<=0)
                firing=false;
        }
        if(!firing)
            laserColor= new Color((int)(charge*0.432), 0, 0);
        if(firing)
            laserColor = Color.gray;
        if(charge >=500) {
            firing=true;
        }

    }
}