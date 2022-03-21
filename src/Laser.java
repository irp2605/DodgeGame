import java.awt.*;

public class Laser extends NonPlayer {
    private Rectangle beam;
    private int charge;
    private Color laserColor;
    public Laser(int direction, int type, int gridX, int gridY, int speed, int width, int height) {
        super(direction, type, gridX, gridY, speed, width, height);
    }
    public void chargeLaser(){
        charge++;
    }
}
