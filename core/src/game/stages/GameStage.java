package game.stages;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.resources.ResourceManager;
import game.actors.*;
import game.utils.GlobalConstants;

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
        GlobalConstants.CUR_SCREEN = "GAME";
    }

    public void initMenu(){
        sceneLoader.loadScene("OtherScene");
        MenuScreenScript menu = new MenuScreenScript(this);
        sceneLoader.sceneActor.addScript(menu);
        addActor(sceneLoader.sceneActor);
        GlobalConstants.CUR_SCREEN = "MENU";

        GameScreenScript.isAngeredBeesMode = false;
    }

    public void initShopMenu(){
        sceneLoader.loadScene("ShopScene");
        ShopScreenScript shop = new ShopScreenScript(this);
        sceneLoader.sceneActor.addScript(shop);
        addActor(sceneLoader.sceneActor);
        GlobalConstants.CUR_SCREEN = "SHOP";
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
