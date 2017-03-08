import javafx.scene.image.Image;

/**
 * Created by envy on 3/7/17.
 */
public class AsteroidSprite extends AbstractSprite {
    private static final double MAX_X = 460;
    private static final double MIN_X = 0;
    private double minY;

    private boolean isBelowScreen;

    public AsteroidSprite(double positionX, double positionY, Image image, double minY) {

        super(positionX, positionY, image);
        this.minY = minY;

        if(getPositionY() < minY){
            isBelowScreen = true;
        }
        else{
            isBelowScreen = false;
        }


    }

    @Override
    public void updatePositionY(double time, double velocityY) {
        super.updatePositionY(time, velocityY);
        if(getPositionY() < minY){
            isBelowScreen = true;
        }
    }


}
