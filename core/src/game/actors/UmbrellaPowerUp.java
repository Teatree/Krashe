package game.actors;

import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import game.actors.controllers.powerups.UmbrellaController;

/**
 * Created by MainUser on 12/07/2015.
 */
public class UmbrellaPowerUp {
    private CompositeItem item;
    private UmbrellaController umbrellaController;

    public UmbrellaPowerUp(SceneLoader sceneLoader, Overlap2DStage stage) {
        item = sceneLoader.getLibraryAsActor("chargerBugLib");
    }

    public void createUmbrellaController(){
        // This method was made so that
        // I could create and Umbrella Controller
        // Separately from powerup
        umbrellaController = new UmbrellaController();
        item.addScript(umbrellaController);
    }

    public UmbrellaController getUmbrellaController(){
        return umbrellaController;
    }
    public void setUmbrellaController(UmbrellaController uc){ umbrellaController = uc; }

    public CompositeItem getCompositeItem() {
        return item;
    }
}
