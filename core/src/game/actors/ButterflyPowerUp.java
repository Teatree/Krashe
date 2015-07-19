package game.actors;

import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import game.actors.controllers.powerups.ButterflyController;

/**
 * Created by MainUser on 12/07/2015.
 */
public class ButterflyPowerUp {
    private CompositeItem item;
    private ButterflyController butterflyController;

    public ButterflyPowerUp(SceneLoader sceneLoader, Overlap2DStage stage) {
        item = sceneLoader.getLibraryAsActor("chargerBugLib");
    }

    public void createUmbrellaController(){
        butterflyController = new ButterflyController();
        item.addScript(butterflyController);
    }

    public ButterflyController getButterflyController(){
        return butterflyController;
    }
    public void setButterflyController(ButterflyController uc){ butterflyController = uc; }

    public CompositeItem getCompositeItem() {
        return item;
    }
}
