import processing.core.PApplet;

public class Missile {
    float xPos, yPos, rotation;
    final int oHeight = 10;
    final int oWidth = 10;
    float velX, velY;
    long lastDrawn;
    boolean exist;
    PApplet p;

    Missile(PApplet p, float x, float y, float r) {
        this.p = p;
        xPos = x;
        yPos = y;
        rotation = r;
        velX = p.sin(rotation) * 50; //px/s
        velY = -p.cos(rotation) * 50;
        lastDrawn = p.millis();
        exist = true;
    }

    void handleWalls(){
        if(p.abs(xPos-p.width/2)>p.width/2 || p.abs(yPos-p.height/2)>p.height/2) exist = false;
    }

    void drawMe() {
        long current = p.millis();
        float timePassed = (current - lastDrawn)/(float)1000;
        xPos = xPos + velX * timePassed;
        yPos = yPos + velY * timePassed;
        p.pushMatrix();
        p.translate(xPos, yPos);
        p.rotate(rotation);
        p.ellipse(0, 0, oWidth, oHeight);
        p.popMatrix();

        lastDrawn = current;
    }

    void hit(Asteroid e){
        if(p.dist(xPos, yPos, e.xPos, e.yPos) < oWidth + e.oWidth){
            exist = false;
            if(e.health != 0) e.health--;
        }
    }
}
