import processing.core.*;
import java.util.*;

public class Player extends GameObject {
    private Animation a;
    private PVector acceleration, velocity;
    private boolean onGround = true, bonkedHead = false, sideCollided = false, tryingJump = false;
    private int jumpStrength = 25;
    private Platform platformImOn, bonkedPlatform;

    boolean alive = true;

    public Player(PApplet p, int x, int y, int r) {
        super(p, x, y, r);
        a = new Animation(p, 50);
        acceleration = new PVector(0, 0);
        velocity = new PVector(0, 0);
    }

    public PVector getVectorPosition() {
        return(new PVector(x, y));
    }

    public void keyPressed() {
        if( p.key == 'd' ) { moveRight = true; }
        if( p.key == 'a' ) { moveLeft = true; }
        if( p.key == 'w' ) { tryingJump = true; }
    }

    public void keyReleased() {
        if( p.key == 'd' ) { moveRight = false; }
        if( p.key == 'a' ) { moveLeft = false; }
        if( p.key == 'w' ) { tryingJump = false; }
    }

    private void jump() {
        if(onGround) { velocity.y = -jumpStrength; acceleration.y = 0;}
    }

    private boolean checkIfIsOnGround(ArrayList<Platform> platforms) {
        boolean returnValue = false;
        for(Platform platform : platforms) {
            if((y + r >= platform.y && y + r <= platform.y + platform.h && x > platform.x - r && x < platform.x + platform.w + r) && !(y - r >= platform.y && y + r <= platform.y + platform.h && y - velocity.y + acceleration.y > platform.y)) { platformImOn = platform; groundY = platform.y; return true; }
        }
        return returnValue;
    }

    private boolean checkHeadBonk(ArrayList<Platform> platforms) {
        for(Platform platform : platforms) {
            if(y - r <= platform.y + platform.h && y - r >= platform.y && x + r > platform.x && x - r < platform.x + platform.w) {
                if(velocity.y < 0 && y - velocity.y > platform.y + platform.h + r) { velocity.y *= -1; bonkedPlatform = platform; y = platform.y + platform.h + r; p.println("BONK");}
                p.println(bonkedPlatform.y);
                return true;
            }
        }
        bonkedPlatform = new Platform(p, 10, 10, 10, 10);
        return false;
    }

    
    private boolean checkSideCollision(ArrayList<Platform> platforms) {
        for(Platform platform : platforms) {
            if(y > platform.y - r && y < platform.y + platform.h + r && x >= platform.x - r && x <= platform.x + platform.w + r && !sideCollided) {
                p.println("DEMOCOLLISION SEEN");
                if(onGround && platform != platformImOn && platform != bonkedPlatform || !onGround && platform != bonkedPlatform) {
                    p.println("Side Collided");
                    velocity.x *= -1;
                    if(velocity.x > 0) { x = platform.x + platform.w + r; }
                    if(velocity.x < 0) { x = platform.x - r; }
                    return true;
                }
            }
        }
        return false;
    }
    

    private void handleGravity() {
        if(!onGround) {
            velocity.y += g;
        } else {
            velocity.y = 0;
            y = groundY - r;
        }
    }

    private void calcMovement() {
        if (tryingJump) { jump(); }
        if(moveRight == moveLeft) {
            acceleration.x = 0;
            if(onGround) {
                a.setState("standing");
            }
        } else {
            if(onGround) {
                if(moveRight) {
                    acceleration.x = 1;
                    a.setState("walking", "right");
                }

                if (moveLeft) {
                    acceleration.x = -1;
                    a.setState("walking", "left");
                }
            }
        }

        if(onGround) {
            if(velocity.x > 0) {
                acceleration.x -= (velocity.x * velocity.x) / (maxSpeed * maxSpeed);
            } else {
                acceleration.x += (velocity.x * velocity.x) / (maxSpeed * maxSpeed);
            }

            velocity.x += acceleration.x;

            if(velocity.x * acceleration.x < 0) {
                if(velocity.x > -(p.sqrt(maxSpeed)) * 2 && velocity.x < p.sqrt(maxSpeed) * 2) { velocity.x = 0; }
            }

        } else { 
            if(alive) { a.setState("jumping"); acceleration.x = 0; } else { a.setState("dying"); }
        }

        /*
        p.println("Velocity: " + velocity.x);
        p.println("Acceleration: " + acceleration.x);
        */

        x += velocity.x;
        y += velocity.y;
    }

    public void death() {
        onGround = false;
        acceleration.set(0, 0);
        velocity.set(0, -25);
        alive = false;
    }

    public void respawn() {
        super.respawn();
        acceleration.set(0, 0);
        velocity.set(0, 0);
        alive = true;
        a.setState("stand_forward");
    }

    public boolean display(ArrayList<Platform> platforms, Enemy enemy) {
        if(alive) {
            onGround = checkIfIsOnGround(platforms);
            bonkedHead = checkHeadBonk(platforms);
            sideCollided = checkSideCollision(platforms);
        }
        handleGravity();
        calcMovement();
        a.display(x, y);
        if(alive) {
            if ( checkGameObjectCollision(enemy) || y > 1050) { return true; } else { return false; }
        } else { return false; }
    }
}