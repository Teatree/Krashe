package game.actors;

import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import game.actors.controllers.DandelionController;

/**
 * Created by MainUser on 12/07/2015.
 */
public class DandelionPowerUp {
    private CompositeItem item;
    private DandelionController dandelionController;

    public DandelionPowerUp(SceneLoader sceneLoader, Overlap2DStage stage) {
        this.item = sceneLoader.getLibraryAsActor("chargerBugLib");
        this.dandelionController = new DandelionController(stage);
        dandelionController.sceneLoader = sceneLoader;
        item.addScript((IScript) dandelionController);
        item.setX(1700);
        item.setY(110);
        stage.addActor(item);
    }
}
