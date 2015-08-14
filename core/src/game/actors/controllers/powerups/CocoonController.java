package game.actors.controllers.powerups;

import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.script.IScript;
import game.actors.ButterflyPowerUp;
import game.actors.UmbrellaPowerUp;
import game.stages.GameStage;
import game.utils.GlobalConstants;

/**
 * Created by MainUser on 12/07/2015.
 */
public class CocoonController implements IScript {
    private CompositeItem item;
    private Overlap2DStage stage;
    public SceneLoader sceneLoader;
    public int health = GlobalConstants.COCOON_HEALTH;
    private int counter;
    private State state;
    private SpriterActor spriterActor;

    public CocoonController(Overlap2DStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        this.item = item;

        state = State.SPAWNING;
        spriterActor = item.getSpriterActorById("chargerBugAni");
        spriterActor.setAnimation(0);
    }

    @Override
    public void dispose() {
        item.dispose();
    }

    @Override
    public void act(float delta) {
        if(!GlobalConstants.GAME_OVER && !GlobalConstants.GAME_PAUSED) {
            counter++;
            if (state == State.SPAWNING) {
                if (counter >= GlobalConstants.COCOON_SPAWNING_DURATION) {
                    spriterActor.setAnimation(1);
                    state = State.IDLE;
                    counter = 0;
                }
            }

            if (state == State.HIT) {
                if (counter >= GlobalConstants.COCOON_HIT_DURATION) {
                    spriterActor.setAnimation(1);
                    if (health <= 0) {
                        state = State.DEAD;
                        spawnButterfly();
                        counter = 0;
                    } else {
                        state = State.IDLE;
                        counter = 0;
                    }
                }
            }

            if (state == State.DEAD) {
                ((GameStage) stage).removeActor(item);
            }
        }
    }

    public void hit() {
        if(state != State.DEAD) {
            health--;
            state = State.HIT;
            counter = 0;
            spriterActor.setAnimation(2);
        }
    }

    public enum State {
        IDLE,
        SPAWNING,
        HIT,
        DEAD;
    }

    private void spawnButterfly() {
        ((GameStage) stage).game.butterflyPowerUp = new ButterflyPowerUp(sceneLoader, stage);
        ((GameStage) stage).game.butterflyPowerUp.createUmbrellaController();
        ((GameStage) stage).game.butterflyPowerUp.getCompositeItem().setX(1900);
        ((GameStage) stage).game.butterflyPowerUp.getCompositeItem().setY(700);
        stage.addActor(((GameStage) stage).game.butterflyPowerUp.getCompositeItem());
    }
}
