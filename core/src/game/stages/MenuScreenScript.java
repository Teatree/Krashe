package game.stages;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.ImageItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.script.IScript;

/**
 * Created by Teatree on 7/25/2015.
 */
public class MenuScreenScript implements IScript {

    private GameStage stage;
    private CompositeItem menuItem;

//    private CompositeItem groundRotator;

    public MenuScreenScript(GameStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        menuItem = item;
        CompositeItem playBtn = menuItem.getCompositeById("flower_ply_ci");
        final CompositeItem btnSettings = menuItem.getCompositeById("btn_settings");
        final CompositeItem btnNoAds = menuItem.getCompositeById("btn_noAds");
        final CompositeItem btnShop = menuItem.getCompositeById("btn_shop");


        // Adding a Click listener to playButton so we can start game when clicked
        playBtn.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                // when finger is up, ask stage to load the game
                stage.initGame();
            }
        });
        btnSettings.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                btnSettings.setLayerVisibilty("normal", false);
                btnSettings.setLayerVisibilty("pressed", true);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                btnSettings.setLayerVisibilty("normal", true);
                btnSettings.setLayerVisibilty("pressed", false);
            }
        });
        btnNoAds.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                btnNoAds.setLayerVisibilty("normal", false);
                btnNoAds.setLayerVisibilty("pressed", true);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                btnNoAds.setLayerVisibilty("normal", true);
                btnNoAds.setLayerVisibilty("pressed", false);
            }
        });
        btnShop.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                btnShop.setLayerVisibilty("normal", false);
                btnShop.setLayerVisibilty("pressed", true);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                btnShop.setLayerVisibilty("normal", true);
                btnShop.setLayerVisibilty("pressed", false);
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
