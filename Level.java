import processing.core.*;
import java.util.*;

public class Level {
    public int[] playerSpawn, finishCoords;
    private ArrayList<Platform> platforms;

    public Level(PApplet p, int[] ps, int[][] pa, int[] goal) {
        platforms = new ArrayList<Platform>();
        playerSpawn = ps;
        finishCoords = goal;
        generatePlatformList(p, pa);
    }

    private void generatePlatformList(PApplet p, int[][] pa) {
        for(int i = 0; i < pa.length; i++) {
            platforms.add(new Platform(p, pa[i][0], pa[i][1], pa[i][2], pa[i][3]));
        }
        platforms.add(new Platform(p, -10, -1000, 10, p.height + 1000));
        platforms.add(new Platform(p, p.width, -1000, 10, p.height + 1000));
    }

    public void displayPlatforms() {
        for(Platform platform : platforms) {
            platform.display();
        }
    }

    public int getPlayerSpawnX() {
        return playerSpawn[0];
    }

    public int getPlayerSpawnY() {
        return playerSpawn[1];
    }

    public int getGoalX() {
        return finishCoords[0];
    }

    public int getGoalY() {
        return finishCoords[1];
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }
}