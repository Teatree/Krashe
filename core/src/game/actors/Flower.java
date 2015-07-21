package game.actors;

import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import game.actors.controllers.FlowerController;
import game.stages.GameStage;

/**
 * Created by MainUser on 21/07/2015.
 */
public class Flower {

    public long pointsAmount = 0L;

    private FlowerController controller;
    private CompositeItem flowerLib;

    public Flower(FlowerController controller, CompositeItem item) {
        this.controller = controller;
        flowerLib = item;
        flowerLib.addScript(controller);

        controller.saIdle = flowerLib.getSpriterActorById("floweridle_ani");
        controller.saClose = flowerLib.getSpriterActorById("flowerattack_ani");
        controller.itemHeadC = flowerLib.getImageById("flower_head");
        controller.itemPeduncleImg = flowerLib.getImageById("flower_peduncle");

    }

    public static void init(GameStage stage, SceneLoader loader){
        Flower flower = new Flower(new FlowerController(stage),
                                    loader.getLibraryAsActor("flowerLib"));
        stage.flower = flower;

        flower.setPosition(1800, -410);

        stage.addActor(flower.getFlowerLib());
    }

    public void setPosition(int x, int y) {
        flowerLib.setPosition(x, y);
    }

    public Rectangle getBounds(){
       return controller.headBoundsRect;
    }

    public FlowerController getController() {
        return controller;
    }

    public void setController(FlowerController controller) {
        this.controller = controller;
    }

    public CompositeItem getFlowerLib() {
        return flowerLib;
    }

    public void setFlowerLib(CompositeItem flowerLib) {
        this.flowerLib = flowerLib;
    }
}
