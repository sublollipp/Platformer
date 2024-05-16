import processing.core.*;

public class Animation{
    PApplet p;

    private String state = "stand_forward", prevState = "stand_forward";
    private String facing = "right", flipped = "right";
    private int frame = 0, reverseFactor = 1, startFrame = 0, framewidth, r;
    private PImage spritesheet, sprite;

    private int[] walkCycle = {3, 2, 4, 2};
    private int walkPoint = 0;

    public Animation(PApplet p, int r) {
        this.p = p;
        this.r = r;
        spritesheet = p.loadImage("HovedloseHolger.png");
        framewidth = spritesheet.width / 11;
        p.imageMode(p.CENTER);
        sprite = spritesheet.get(framewidth * frame, 0, framewidth, framewidth);
    }

    public void setState(String targetState, String targetDirection) {
        state = targetState;
        facing = targetDirection;
    }

    public void setState(String targetState) {
        state = targetState;
    }

    public void display(int x, int y) {

        if(state != prevState) { prevState = state; startFrame = p.frameCount; }

        if((p.frameCount - startFrame + 7) % 7 == 0) {

            if(state == "walking") {
                frame = walkCycle[walkPoint % 4];
                walkPoint += 1;
                if(facing != flipped) { reverseFactor *= -1; flipped = facing; }
            }
            if(state == "standing") {
                frame = 2;
                walkPoint = 0;
            }
            if(state == "jumping") {
                frame = 9;
            }
            if(state == "stand_forward") { frame = 0; }

            if(state == "dying") { frame = 8; }

            sprite = spritesheet.get(framewidth * frame, 0, framewidth, framewidth);

        }

        p.pushMatrix();
        p.scale(reverseFactor, 1);

        p.image(sprite, reverseFactor*x, y, r*2, r*2);

        p.popMatrix();
    }
}