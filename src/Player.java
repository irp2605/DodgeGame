import java.awt.*;

public class Player {
    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public Rectangle getHurtbox() {
        return hurtbox;
    }

    public void setHurtbox(Rectangle hurtbox) {
        this.hurtbox = hurtbox;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    private int gridX;
    private int gridY;
    private Rectangle hitbox;
    private Rectangle hurtbox;
    private int direction;
    private int lives;


    public Player (int gridX, int gridY, int direction, int lives){
        this.gridX = gridX;
        this.gridY = gridY;
        hitbox = new Rectangle(gridX*40, gridY*40, 40, 40);
        this.direction=direction;
        this.lives=lives;
    }
}
