import processing.core.PApplet;

import java.util.ArrayList;

public class Asteroid{
    float xPos, yPos, rotation;
    int oHeight;
    int oWidth;
    float velX, velY;
    long lastDrawn;
    int health;
    boolean isSub;
    PApplet p;
    ArrayList<Asteroid> mainAsteroidList;

    Asteroid(PApplet p, ArrayList<Asteroid> mainAsteroidList, float x, float y, float r, int health, boolean isSub) {
        this.p = p;
        this.mainAsteroidList = mainAsteroidList;
        xPos = x;
        yPos = y;
        rotation = r;
        this.health = health;
        this.isSub = isSub;
        if(isSub){
            oHeight = 25;
            oWidth = 25;
        } else{
            oHeight = 50;
            oWidth = 50;
        }
        velX = p.sin(rotation) * 10; //px/s
        velY = -p.cos(rotation) * 10;
    }

    void update(){
        long current = p.millis();
        float timePassed = (current - lastDrawn)/(float)1000.0;

        xPos = xPos + velX * timePassed;
        yPos = yPos + velY * timePassed;

        handleWalls();

        lastDrawn = current;

        if(health == 2 && !isSub){
            mainAsteroidList.add(new Asteroid(p, mainAsteroidList, xPos + p.random(-10, 10), yPos + p.random(-10, 10), p.random(0, p.TWO_PI), 1, true ));
            isSub = true;
        }

        if(health <= 0) destroyed();
    }

    void drawMe() {
        p.pushMatrix();
        p.translate(this.xPos, this.yPos);
        p.rotate(rotation);
        p.scale((float)(health/2.0));
        p.ellipse(0, 0, 50, 50);
        p.popMatrix();
    }

    void destroyed(){
        mainAsteroidList.remove(this);
    }

    void handleWalls() {
        if (xPos<-oWidth) xPos=p.width+oWidth;
        if (xPos>p.width+oWidth) xPos=-oWidth;
        if (yPos<-oWidth) yPos=p.height+oWidth;
        if (yPos>p.height+oWidth) yPos=-oWidth;
    }
}
