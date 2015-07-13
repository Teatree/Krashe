package game.actors.controllers;

import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.script.IScript;
import game.actors.UmbrellaPowerUp;
import game.stages.GameStage;

/**
 * Created by MainUser on 12/07/2015.
 */
public class DandelionController implements IScript {
    private CompositeItem item;
    private Overlap2DStage stage;
    private int counter;
    private State state;
    private SpriterActor spriterActor;
    private UmbrellaPowerUp umbrellaPowerUp;

    public DandelionController(Overlap2DStage stage) {this.stage = stage;}

    @Override
    public void init(CompositeItem item) {
        this.item = item;

        state = State.GROWING;
        spriterActor = item.getSpriterActorById("chargerBugAni");
        spriterActor.setAnimation(0);
    }

    @Override
    public void dispose() {
        item.dispose();
    }

    @Override
    public void act(float delta) {
        counter++;
        if (state == State.GROWING) {
            if (counter >= 200){
                spriterActor.setAnimation(1);
                state = State.IDLE;
                counter = 0;
            }
        }
        if (state == State.IDLE) {
            if (counter >= 200) {
                spriterActor.setAnimation(2);
                state = State.DYING;
                counter = 0;
            }
        }
        if (state == State.DYING) {
            if (counter == 200){
                spawnUmbrella();
            }else if(counter >= 400) {
                state = State.DEAD;
                ((GameStage)stage).removeActor(item);
            }
        }
    }

    public enum State {
        IDLE,
        GROWING,
        DYING,
        DEAD;
    }

    public void setUmbrellaPowerUp(UmbrellaPowerUp umbrellaPowerUp) {
        this.umbrellaPowerUp = umbrellaPowerUp;
    }

    private void spawnUmbrella(){
        umbrellaPowerUp.createUmbrellaController();
        umbrellaPowerUp.getCompositeItem().setX(1300);
        umbrellaPowerUp.getCompositeItem().setY(210);
        stage.addActor(umbrellaPowerUp.getCompositeItem());
    }
}
