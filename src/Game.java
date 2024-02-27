import processing.core.PApplet;
import java.util.ArrayList;

public class Game extends PApplet{
    ArmedRocket r;
    ArrayList<Asteroid> asteroids;
    ArrayList<Missile> missiles;

    public void settings() { size(500, 500); }

    public void setup() {
        r = new ArmedRocket(this, 100, 100, PI/2);
        asteroids = new ArrayList<Asteroid>();
        for (int i=0; i<10; i++) {
            asteroids.add(new Asteroid(this, asteroids, random(0, 500), random(0, 500), random(0, TWO_PI), 2, false ));
        }

        missiles = new ArrayList<Missile>();
    }

    public void draw() {
        background(0);

        // Display controls information
        fill(255);
        textAlign(LEFT);
        text("Press W to move forward/fire thrusters", 10, 20);
        text("Press S to stop", 10, 40);
        text("Press A to turn left", 10, 60);
        text("Press D to turn right", 10, 80);
        text("Press SPACE to fire rockets", 10, 100);
        text("IF YOU GET HIT, YOU LOSE!", 10, 120);

        // Draw asteroids
        for (int i=0; i<asteroids.size() - 1; i++) {
                asteroids.get(i).drawMe();
                asteroids.get(i).update();

                // Check for collision with rocket
                if (r.isHit(asteroids.get(i))) {
                    gameOver();
                    return; // End the draw loop if game over
                }


        }

        // Draw armed rocket
        r.drawRCT();

        // Draw missiles
        for (int i=0; i<missiles.size(); i++) {
            Missile m = missiles.get(i);
            m.drawMe();
            // Check each asteroid for collision with the current missile
            for(int j =0; j < asteroids.size(); j++){
                Asteroid e = asteroids.get(j);
                m.hit(e);
            }

            // Remove missiles
            if(!m.exist) missiles.remove(i);
        }

        // Check for key presses for rocket controls
        if (keyPressed) {
            if (key == 'w'){
                r.fireThrusters();
            }
            else if (key == 's'){
                r.stopThrusters();
            }
        }
    }

    void gameOver() {
        fill(255, 0, 0); // Red color for game over message
        textAlign(CENTER);
        textSize(32);
        text("Game Over", 400, 400);
        noLoop(); // Stop the game loop
    }


    public void keyPressed() {
        // Handle rotation and firing missile
        if (key == 'a') {
            r.rotateCounterClockwise();
        } else if (key == 'd') {
            r.rotateClockwise();
        } else if (key == 'w') {
            r.fireThrusters();
        } else if (key == 's') {
            r.stopThrusters();
        }

        if (key == ' ') {
            // Fire the missile
            Missile missile = r.fire();
            missiles.add(missile);
        }
    }

}
