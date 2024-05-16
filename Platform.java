import processing.core.*;

public class Platform {
    public int x, y, w, h;
    private PApplet p;

    public Platform(PApplet p, int x, int y, int w, int h) {
        this.p = p;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void display() {
        p.fill(255);
        p.rect(x, y, w, h);
    }
}
