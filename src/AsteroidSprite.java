import javafx.scene.image.Image;

/**
 * Created by envy on 3/7/17.
 */
public class AsteroidSprite extends AbstractSprite {
    private static final double MAX_X = 460;
    private static final double MIN_X = 0;
    private double maxY;

    private boolean isBelowScreen;

    public AsteroidSprite(double positionX, double positionY, double velocityY, Image image, double maxY) {

        super(positionX, positionY, image);
        setVelocityY(velocityY);
        this.maxY = maxY;
        this.isBelowScreen = getPositionY() > maxY;


    }

    @Override
    public void updatePositionY(double time) {
        super.updatePositionY(time);
        if(getPositionY() > maxY){
            isBelowScreen = true;
        }
    }

    public boolean isBelowScreen() {
        return isBelowScreen;
    }


}
