import processing.core.PApplet;

public class Rocket {
    float xPos, yPos, rotation;
    final int oHeight = 10;
    final int oWidth = 10;
    float velX, velY;
    long lastDrawn;
    PApplet p;
    Rocket(PApplet p, float x, float y, float r) {
        this.p = p;
        xPos = x*2;
        yPos = y*2;
        rotation = r;
        velX = 0; //px/s
        velY = 0;
        lastDrawn = p.millis();
    }

    void fireThrusters(){
        //increase the velocity by 1px/sec
        velX = velX + 1 * p.sin(rotation);
        velY = velY - 1 * p.cos(rotation);
    }

    void stopThrusters(){
        //increase the velocity by 1px/sec
        velX = 0;
        velY = 0;
    }

    void drawRCT() {

        long current = p.millis();
        float timePassed = (current - lastDrawn)/(float)1000;
        xPos += velX * timePassed;
        yPos += velY * timePassed;

        p.pushMatrix();

        p.translate(xPos, yPos);
        p.rotate(rotation);

        p.fill(255, 0, 0);
        p.triangle(0, -oHeight, -oWidth, oHeight,
                oWidth, oHeight);
        p.rectMode(p.CORNERS);
        p.rect(-oWidth + 5, oHeight, -oWidth + 8,
                oHeight + 3);
        p.rect(oWidth - 8, oHeight, oWidth - 5, oHeight
                + 3);
        p.popMatrix();

        handleWalls();

        lastDrawn = current;
    }

    void rotateClockwise() {
        rotation += 0.3;
    }

    void rotateCounterClockwise() {
        rotation -= 0.3;
    }

    void handleWalls() {
        if (xPos<-oWidth/2) xPos=p.width+oWidth/2;
        if (xPos>p.width+oWidth/2) xPos=-oWidth/2;
        if (yPos<-oWidth/2) yPos=p.height+oWidth/2;
        if (yPos>p.height+oWidth/2) yPos=-oWidth/2;
    }

    boolean isHit(Asteroid asteroid) {
        // Calculate distance between rocket and asteroid
        float distance = p.dist(xPos, yPos, asteroid.xPos, asteroid.yPos);

        // Calculate sum of radii
        float sumOfRadii = (oHeight + asteroid.oHeight) / 2;

        // Check for collision
        if (distance <= sumOfRadii) {
            return true; // Collision occurred
        } else {
            return false; // No collision
        }
    }

}
