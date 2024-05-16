import processing.core.*;

class FinishLine extends GameObject {
    public FinishLine(PApplet p, int x, int y) {
        super(p, x, y, 50);
    }

    public void display() {
        p.fill(100, 255, 50);
        p.rect(x - r, y - r, r * 2, r * 2);
    }

    public boolean checkReached(GameObject player) {
        return checkGameObjectCollision(player);
    }
}