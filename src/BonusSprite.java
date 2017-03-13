import javafx.scene.image.Image;

/**
 * Sprite class for bonus objects
 *
 * Created by mayvilleq on 3/9/17.
 */

public class BonusSprite extends SpaceObjectSprite {
    private String bonusType;


    public BonusSprite(double positionX, double positionY, double velocityY,Image image, double maxY, String bonusType) {
        super(positionX,positionY,velocityY,image,maxY);
        this.bonusType = bonusType;
    }

    public String getBonusType() {
        return bonusType;
    }
}
