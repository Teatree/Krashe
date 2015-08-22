package game.actors.controllers.powerups;

import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.script.IScript;
import game.actors.UmbrellaPowerUp;
import game.stages.GameStage;
import game.utils.GlobalConstants;
import static game.stages.GameScreenScript.*;
/**
 * Created by MainUser on 12/07/2015.
 */
public class DandelionController implements IScript {
    private CompositeItem item;
    private Overlap2DStage stage;
    public SceneLoader sceneLoader;
    private int counter;
    private State state;
    private SpriterActor spriterActor;

    public DandelionController(Overlap2DStage stage) {this.stage = stage;}

    @Override
    public void init(CompositeItem item) {
        this.item = item;

        state = State.GROWING;
        spriterActor = item.getSpriterActorById("dandelion_ani");
        spriterActor.setAnimation(0);
    }

    @Override
    public void dispose() {
        item.dispose();
    }

    @Override
    public void act(float delta) {
        if(isGameAlive() && GlobalConstants.CUR_SCREEN == "GAME") {
            counter++;
            if (state == State.GROWING) {
                if (counter >= GlobalConstants.DANDELION_GROWING_DURATION) {
                    spriterActor.setAnimation(1);
                    state = State.IDLE;
                    counter = 0;
                }
            }
            if (state == State.IDLE) {
                if (counter >= GlobalConstants.DANDELION_IDLE_DURATION) {
                    spriterActor.setAnimation(2);
                    state = State.DYING;
                    counter = 0;
                }
            }
            if (state == State.DYING) {
                if (counter == GlobalConstants.DANDELION_UMBRELLA_DUYING_POINT) {
                    spawnUmbrella();
                } else if (counter >= GlobalConstants.DANDELION_DUYING_DURATION) {
                    state = State.DEAD;
                    ((GameStage) stage).removeActor(item);
                    ((GameStage) stage).game.dandelionPowerup = null;
                }
            }
        }
    }

    public enum State {
        IDLE,
        GROWING,
        DYING,
        DEAD;
    }

    private void spawnUmbrella(){
        ((GameStage)stage).game.umbrellaPowerUp = new UmbrellaPowerUp(sceneLoader, stage);
        ((GameStage)stage).game.umbrellaPowerUp.createUmbrellaController();
        ((GameStage)stage).game.umbrellaPowerUp.getCompositeItem().setX(1300);
        ((GameStage)stage).game.umbrellaPowerUp.getCompositeItem().setY(210);
        stage.addActor(((GameStage)stage).game.umbrellaPowerUp.getCompositeItem());
    }
}
