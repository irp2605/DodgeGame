import java.awt.*;

public class NonPlayer {
    private int direction;
    private int type;
    private int gridX;
    private int gridY;
    private int speed;
    private Rectangle rectangle;
    public NonPlayer(int direction, int type, int gridX, int gridY, int width, int height){
        this.direction = direction;
        this.type = type;
        this.gridX = gridX;
        this.gridY = gridY;
        rectangle = new Rectangle(gridX, gridY, width, height);
    }

    public int getType() {
        return type;
    }

    public int getDirection() {
        return direction;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }
}
