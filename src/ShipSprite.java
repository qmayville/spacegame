import javafx.scene.image.Image;

/**
 * Created by envy on 3/7/17.
 */
public class ShipSprite extends AbstractSprite {
    private double fuel;
    private int lives;

    public ShipSprite(double positionX, double positionY, Image image, double fuel, int lives) {

        super(positionX, positionY, image);

        this.fuel = fuel;
    }

    private double getFuel(){

        return 0;
    }

    private int getLives(){

        return 0;
    }

}
