import java.awt.*;

public class Player {
    private int gridX;
    private int gridY;
    private Rectangle hitbox;
    private int direction;
    private int lives;


    public Player (int gridX, int gridY, int direction, int lives){
        this.gridX = gridX;
        this.gridY = gridY;
        hitbox = new Rectangle(gridX*10, gridY*10, 10, 10);
        this.direction=direction;
        this.lives=lives;
    }
}
