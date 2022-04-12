import java.awt.*;

public class linearEnemy extends NonPlayer {
    public linearEnemy(int direction, int type, int gridX, int gridY, int speed, int width, int height) {
        super(direction, type, gridX, gridY, speed, width, height);
    }
    private int moveDist = (Math.random() <= 0.5) ? 1 : 2;
    //1 is up, 2 is right, 3 is down, 4 is left
    public int getMoveDist(){
        return moveDist;
    }
    private int rotator = 0;
    public void mover(World w) {
        rotator--;
        if (moveDist == 1) {
            for (int i = 0; i < w.getNonplayerList().size(); i++) {
                if (w.getNonplayerList().get(i).getRectangle().intersects(getRectangle()) && rotator <0) {
                    if (w.getNonplayerList().get(i).getType() == Constants.WALL || w.getNonplayerList().get(i).getType() == Constants.LASER) {
                        moveDist = 3;
                        rotator =10;
                    }
                }
            }
            setRectangle(new Rectangle((int) getRectangle().getX(), (int) getRectangle().getY() - Constants.LINEAR_SPEED, (int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
        }
        if (moveDist == 3) {
            for (int i = 0; i < w.getNonplayerList().size(); i++) {
                if (w.getNonplayerList().get(i).getRectangle().intersects(getRectangle()) && rotator <0) {
                    if (w.getNonplayerList().get(i).getType() == Constants.WALL || w.getNonplayerList().get(i).getType() == Constants.LASER) {
                        moveDist = 1;
                        rotator =10;
                    }
                }
            }
            setRectangle(new Rectangle((int) getRectangle().getX(), (int) getRectangle().getY() + Constants.LINEAR_SPEED, (int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
        }
        if (moveDist == 2) {
            for (int i = 0; i < w.getNonplayerList().size(); i++) {
                if (w.getNonplayerList().get(i).getRectangle().intersects(getRectangle()) && rotator <0) {
                    if (w.getNonplayerList().get(i).getType() == Constants.WALL ||  w.getNonplayerList().get(i).getType() == Constants.LASER) {
                        moveDist = 4;
                        rotator =10;
                    }
                }
            }
            setRectangle(new Rectangle((int) getRectangle().getX() - Constants.LINEAR_SPEED, (int) getRectangle().getY(), (int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
        }
        if (moveDist == 4) {
            for (int i = 0; i < w.getNonplayerList().size(); i++) {
                if (w.getNonplayerList().get(i).getRectangle().intersects(getRectangle()) && rotator <0) {
                    if (w.getNonplayerList().get(i).getType() == Constants.WALL ||  w.getNonplayerList().get(i).getType() == Constants.LASER) {
                        moveDist = 2;
                        rotator =10;
                    }
                }
            }
            setRectangle(new Rectangle((int) getRectangle().getX() + Constants.LINEAR_SPEED, (int) getRectangle().getY(), (int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
        }
    }
}