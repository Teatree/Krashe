package game.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import game.actors.controllers.UmbrellaController;

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
        umbrellaController.pushUmbrellaFor(-350f);
        item.addScript(umbrellaController);
    }

    public CompositeItem getCompositeItem() {
        return item;
    }
}
