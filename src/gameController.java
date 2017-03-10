/**
 * Created by Ethan Cassel-Mace, Hannah Barnstone, Quinn Mayville, && Michael Vue
 */
public class gameController {
    private GameModel model;
    private gameViewRevised view;

    public gameController(GameModel model) {
        this.model = model;
        view = new gameViewRevised(this, model);
        try {
            view.start(gameViewRevised.gameStage);

        } catch (Exception error) {
            error.printStackTrace();
        }
        model.initialize();
        model.setView(view);
        model.runTimer();
    }


    public void rightArrowKey() {
        model.startAccelerationPositive();
    }

    public void leftArrowKey() {
        model.startAccelerationNegative();
    }

    public void rightArrowKeyReleased() { model.stopAccelerationPositive(); }

    public void leftArrowKeyReleased() { model.stopAccelerationNegative(); }

}
