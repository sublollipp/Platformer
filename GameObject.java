import processing.core.*;
import java.lang.Math;

public abstract class GameObject{
    protected int x, y, r, startx, starty, groundY, maxSpeed = 10;
    protected float g = 1f;
    protected PApplet p;
    protected boolean moveRight = false, moveLeft = false;

    public GameObject(PApplet p, int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.p = p;
        startx = x;
        starty = y;
    }

    public int getInnerHitboxRadius() {
        return (int)Math.round(Math.cos(Math.toRadians(45))*r);
    }

    public void changeSpawnCoords(int x, int y) {
        startx = x;
        starty = y;
    }

    public boolean checkGameObjectCollision(GameObject obj) {
        int qh = obj.getInnerHitboxRadius();
        if(x <= obj.x + qh + r && x >= obj.x - qh  - r && y <= obj.y + qh + r && y >= obj.y - qh - r) {
            return true;
        } else { return false; }
    }

    public void respawn() {
        x = startx;
        y = starty;
    }

    public void display() {
        p.fill(255, 0, 0);
        p.circle(x, y, r);
    }
}