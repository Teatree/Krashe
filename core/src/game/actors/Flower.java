package game.actors;

import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import game.actors.controllers.FlowerController;
import game.stages.GameStage;
import game.utils.GlobalConstants;

/**
 * Created by MainUser on 21/07/2015.
 */
public class Flower {

    public long pointsAmount = 0L;

    private int maxHp = GlobalConstants.DEFAULT_MAX_HP;
    private int curHp = maxHp;

    private FlowerController controller;
    private CompositeItem flowerLib;

    public Flower(FlowerController controller, CompositeItem item) {
        this.controller = controller;
        flowerLib = item;
        flowerLib.addScript(controller);

        controller.saFlower = flowerLib.getSpriterActorById("floweridle_ani");
        controller.saHead = flowerLib.getSpriterActorById("flower_head2");
        controller.saHead.setVisible(false);
        controller.itemPeduncleImg = flowerLib.getImageById("flower_peduncle");
        controller.itemPeduncleImg.setVisible(false);
    }

    public static void init(GameStage stage, SceneLoader loader){
        Flower flower = new Flower(new FlowerController(stage),
                                    loader.getLibraryAsActor("flowerLib2"));
        stage.game.flower = flower;

        flower.setPosition(1800, -585);

        stage.addActor(flower.getFlowerLib());
    }

    public int getCurHp() {
        return curHp;
    }

    public void setCurHp(int curHp) {
        this.curHp = curHp;
    }

    public void addCurHp(int hpToAdd){
        setCurHp(getCurHp()+hpToAdd);
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
