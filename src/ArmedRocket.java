import processing.core.PApplet;

public class ArmedRocket extends Rocket{
    ArmedRocket(PApplet p, float x, float y, float r){
        super(p, x, y, r);
    }

    Missile fire(){
        Missile m = new Missile(p, xPos, yPos, rotation);
        return m;
        //m.drawMe();
        //creates a missile
    }
}
