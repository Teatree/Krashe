package game.actors;

import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import game.actors.controllers.powerups.CocoonController;

/**
 * Created by MainUser on 12/07/2015.
 */
public class CocoonPowerUp {
    private CompositeItem item;
    private CocoonController cocoonController;

    public CocoonPowerUp(SceneLoader sceneLoader, Overlap2DStage stage) {
        this.item = sceneLoader.getLibraryAsActor("chargerBugLib");
        this.cocoonController = new CocoonController(stage);
        cocoonController.sceneLoader = sceneLoader;
        item.addScript((IScript) cocoonController);
        item.setX(1900);
        item.setY(700);
        stage.addActor(item);
    }

    public CocoonController getCocoonController() {
        return cocoonController;
    }

    public void setCocoonController(CocoonController cocoonController) {
        this.cocoonController = cocoonController;
    }

    public CompositeItem getItem() {
        return item;
    }

    public void setItem(CompositeItem item) {
        this.item = item;
    }
}
