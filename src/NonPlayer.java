import java.awt.*;

public class NonPlayer {
    private int direction;
    private int type;
    private int gridX;
    private int gridY;
    private int speed;
    private Rectangle rectangle;
    public NonPlayer(int direction, int type, int gridX, int gridY, int speed, int width, int height){
        this.direction = direction;
        this.type = type;
        this.gridX = gridX;
        this.gridY = gridY;
        this.speed = speed;
        rectangle = new Rectangle(gridX*100, gridY*100, width, height);
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

    public Rectangle getRectangle(){

        return rectangle;
    }
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
