Player player;
Enemy enemy;
FinishLine finishLine;

int enemySpeed = 5;
int level = 0;
int deathFrame = 0;

boolean alive = true;

ArrayList<Level> levels;

int[] level1Player = {300, 300};
int[][] level1Platforms = {{0, 800, 2000, 200}, {110, 600, 400, 20}, {750, 300, 50, 20}, {1000, 300, 50, 20}, {1500, 100, 400, 400}};
int[] level1Goal = {1700, 50};

int[] level2Player = {100, 800};
int[][] level2Platforms = {{0, 950, 100, 50}, {400, 700, 200, 50}, {0, 450, 250, 20}};
int[] level2Goal = {50, 50};

void loadLevels() {
    levels.add(new Level(this, level1Player, level1Platforms, level1Goal));
    levels.add(new Level(this, level2Player, level2Platforms, level2Goal));
}

void setup() {
    levels = new ArrayList<Level>();
    size(2000, 1000);
    noSmooth();
    loadLevels();
    player = new Player(this, levels.get(level).getPlayerSpawnX(), levels.get(level).getPlayerSpawnY(), 50);
    finishLine = new FinishLine(this, levels.get(level).getGoalX(), levels.get(level).getGoalY());
    enemy = new Enemy(this, 1000, 1000, 25);
}

void keyPressed() {
    player.keyPressed();
}

void keyReleased() {
    player.keyReleased();
}

void playerDead() {
    deathFrame = frameCount;
    player.death();
    enemySpeed = 0;
    alive = false;
}

void respawnAll() {
    player.respawn();
    enemy.respawn();
    enemySpeed = 5;
    alive = true;
}

void checkRespawn() {
    if( frameCount > deathFrame + 100 ) { respawnAll(); }
}

void nextLevel() {
    if( level + 1 < levels.size()) { level ++; } else { level = 0; }
    player.changeSpawnCoords(levels.get(level).getPlayerSpawnX(), levels.get(level).getPlayerSpawnY());
    finishLine.changeSpawnCoords(levels.get(level).getGoalX(), levels.get(level).getGoalY());
    player.respawn();
    finishLine.respawn();
    enemy.respawn();
}

void draw() {
    background(122);
    levels.get(level).displayPlatforms();
    if ( player.display(levels.get(level).getPlatforms(), enemy) ) { playerDead(); };
    enemy.display(player.getVectorPosition(), enemySpeed);
    finishLine.display();
    if(!alive) {
        checkRespawn();
    } else {
        if(finishLine.checkReached(player)) { nextLevel(); }
    }
}