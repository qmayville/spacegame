import javafx.scene.image.Image;

/**
 * Created by envy on 3/7/17.
 */
public class ShipSprite extends AbstractSprite {
    private double fuel;
    private int lives;
    private static final double MAX_X = 460;
    private static final double MIN_X = 10;

    public ShipSprite(double positionX, double positionY, Image image, double fuel, int lives) {

        super(positionX, positionY, image);

        this.fuel = fuel;
    }

    public double getFuel(){

        return fuel;
    }

    public void updateFuel(double changeValue){
        fuel += changeValue;
        if (fuel > 100){
            fuel = 100;
        }
    }

    public void setFuel(double fuelValue){
        fuel += fuelValue;
        if (fuel > 100){
            fuel = 100;
        }
    }

    public int getLives(){
        return lives;
    }



    private void boundX() {
        if (getPositionX() > MAX_X) {
            setPositionX(MAX_X);
        }
        if (getPositionX() < MIN_X) {
            setPositionX(MIN_X);
        }
    }

}
