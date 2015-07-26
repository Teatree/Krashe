package game.stages;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.resources.ResourceManager;
import game.actors.*;

/**
 * Created by Teatree on 5/25/2015.
 */
public class GameStage extends Overlap2DStage {

    public GameScreenScript game;

    public GameStage getInstance() {
        return this;
    }

    public GameStage(ResourceManager resourceManager) {

        initSceneLoader(resourceManager);

        initMenu();
    }

    public void initGame() {
        clear();
        sceneLoader.loadScene("MainScene");
        game = new GameScreenScript(this);
        sceneLoader.sceneActor.addScript(game);
        addActor(sceneLoader.sceneActor);

        Flower.init(this, sceneLoader);
    }

    public void initMenu(){
        sceneLoader.loadScene("MainScene");
        MenuScreenScript menu = new MenuScreenScript(this);
        sceneLoader.sceneActor.addScript(menu);
        addActor(sceneLoader.sceneActor);
    }

    public void update() {

    }

    public void removeActor(CompositeItem item) {
        for (Actor actor : this.getActors()) {
            if (actor.equals(item)) {
                actor.remove();
            }
        }
    }
}
