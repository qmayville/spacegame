import javafx.scene.image.Image;

/**
 * Created by mayvilleq on 3/9/17.
 */
public class BonusSprite extends AbstractSprite {
    private String bonusType;

    public BonusSprite(double positionX, double positionY, Image image, String bonusType) {
        super(positionX,positionY,image);
        this.bonusType = bonusType;
    }

    public String getBonusType() {
        return bonusType;
    }
}
