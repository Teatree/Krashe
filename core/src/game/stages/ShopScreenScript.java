package game.stages;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

/**
 * Created by Teatree on 7/25/2015.
 */
public class ShopScreenScript implements IScript {

    private GameStage stage;
    private CompositeItem menuItem;

//    private CompositeItem groundRotator;

    public ShopScreenScript(GameStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        menuItem = item;

        final CompositeItem btnBack = menuItem.getCompositeById("btn_back");
        btnBack.setLayerVisibilty("normal", true); // fix this with Overlap2D
        btnBack.setLayerVisibilty("pressd", false);
        CompositeItem shopItem = menuItem.getCompositeById("item_element_1");
        

        btnBack.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                btnBack.setLayerVisibilty("normal", false);
                btnBack.setLayerVisibilty("pressed", true);
                stage.initMenu();

                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                btnBack.setLayerVisibilty("normal", true);
                btnBack.setLayerVisibilty("pressed", false);
            }
        });
    }

    @Override
    public void dispose() {

    }

    @Override
    public void act(float delta) {

    }
}
