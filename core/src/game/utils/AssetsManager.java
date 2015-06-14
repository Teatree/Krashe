package game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.uwsoft.editor.renderer.resources.ResourceManager;

/**
 * Created by Teatree on 6/11/2015.
 */
public class AssetsManager {

    public static final ResourceManager resourceManager = new ResourceManager();

    public static final AssetManager manager = new AssetManager();

    public static void load(){
        resourceManager.initAllResources();
    }

    public static void dispose(){
        manager.dispose();
        resourceManager.dispose();
    }

}
