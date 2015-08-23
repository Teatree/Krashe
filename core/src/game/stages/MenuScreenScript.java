package game.stages;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import game.utils.SaveManager;

import static game.utils.Animator.*;
/**
 * Created by Teatree on 7/25/2015.
 */
public class MenuScreenScript implements IScript {

    private GameStage stage;
    private CompositeItem menuItem;

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
                touchDownButton(btnSettings);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                touchUpButton(btnSettings);
            }
        });
        btnNoAds.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                touchDownButton(btnNoAds);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                touchUpButton(btnNoAds);
            }
        });
        btnShop.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                touchDownButton(btnShop);
                stage.initShopMenu();

                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                touchUpButton(btnShop);
            }
        });
    }

    @Override
    public void dispose() {
//        SaveManager.saveProperties();
    }

    @Override
    public void act(float delta) {

    }
}
