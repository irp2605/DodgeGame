import java.awt.*;

public class pattenEnemy extends NonPlayer {
    public pattenEnemy(int direction, int type, int gridX, int gridY, int speed, int width, int height) {
        super(direction, type, gridX, gridY, speed, width, height);
    }

    private int moveDist = 3;//(Math.random() <= 0.5) ? 1 : 2;
    //1 is up, 2 is right, 3 is down, 4 is left
    public int getMoveDist(){
        return moveDist;
    }
    private int rotator = 0;
    public void mover(World w) {
        rotator--;
        if (moveDist == 1) {
            for (int i = 0; i < w.getNonplayerList().size(); i++) {
                if (rotator <0) {
                    //if (w.getNonplayerList().get(i).getType() == Constants.WALL) {
                    moveDist = 2;
                    rotator =120;
                    //}
                }
            }
            setRectangle(new Rectangle((int) getRectangle().getX(), (int) getRectangle().getY() - Constants.LINEAR_SPEED, (int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
        }
        if (moveDist == 3) {
            for (int i = 0; i < w.getNonplayerList().size(); i++) {
                if (rotator <0) {
                    //if (w.getNonplayerList().get(i).getType() == Constants.WALL) {
                    moveDist = 4;
                    rotator =120;
                    //}
                }
            }
            setRectangle(new Rectangle((int) getRectangle().getX(), (int) getRectangle().getY() + Constants.LINEAR_SPEED, (int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
        }
        if (moveDist == 2) {
            for (int i = 0; i < w.getNonplayerList().size(); i++) {
                if (rotator <0) {
                    // if (w.getNonplayerList().get(i).getType() == Constants.WALL) {
                    moveDist = 3;
                    rotator =120;
                    // }
                }
            }
            setRectangle(new Rectangle((int) getRectangle().getX() - Constants.LINEAR_SPEED, (int) getRectangle().getY(), (int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
        }
        if (moveDist == 4) {
            for (int i = 0; i < w.getNonplayerList().size(); i++) {
                if (rotator <0) {
                    // if (w.getNonplayerList().get(i).getType() == Constants.WALL) {
                    moveDist = 1;
                    rotator =120;
                    //  }
                }
            }
            setRectangle(new Rectangle((int) getRectangle().getX() + Constants.LINEAR_SPEED, (int) getRectangle().getY(), (int) getRectangle().getWidth(), (int) getRectangle().getHeight()));
        }
    }
}