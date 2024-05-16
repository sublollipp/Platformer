import processing.core.*;

public class Enemy extends GameObject{

    public Enemy(PApplet p, int x, int y, int r) {
        super(p, x, y, r);
    }

    private void moveTowards(PVector target, int speed) {
        PVector movement = new PVector(target.x - x, target.y - y);
        movement.setMag(speed);
        x += movement.x;
        y += movement.y;
    }

    public void display(PVector target, int speed) {
        super.display();
        moveTowards(target, speed);
    }
}