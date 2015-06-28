package game.utils;

import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import game.actors.BugController;
import game.actors.DrunkBugController;
import game.actors.SimpleBugController;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by MainUser on 21/06/2015.
 */
public class MrSpawner {

    private HashMap<String, Class> libBugs = new HashMap<>();
    Random rand = new Random();
    private static int MIN_X;
    private static int MAX_X;
    private static int MIN_Y;
    private static int MAX_Y;

    public MrSpawner(){
        init();
    }

    private void init(){
        libBugs.put("drunkBugLib", DrunkBugController.class);
        libBugs.put("chargerBugLib", DrunkBugController.class);
        libBugs.put("simpleBugLib", DrunkBugController.class);

        MIN_X = -600;
        MIN_Y = 300;
        MAX_X = -300;
        MAX_Y = 1200;
    }

    private Vector2 getPos(){
        float x = rand.nextInt(MAX_X-MIN_X)+MIN_X;
        float y = rand.nextInt(MAX_Y-MIN_Y)+MIN_Y;

        return new Vector2(x,y);
    }

    private Map.Entry<String, Class> getType(){
        return (Map.Entry<String, Class>) libBugs.entrySet().toArray()[rand.nextInt(libBugs.size())];
    }

    public BugController spawnUnsafe(Overlap2DStage stage, SceneLoader sceneLoader) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Map.Entry<String, Class> type = getType();
        CompositeItem compI;
            compI = sceneLoader.getLibraryAsActor(type.getKey());

//          BugController buntroller = (BugController) type.getValue()
//                    .getConstructor(Overlap2DStage.class).newInstance(stage);
        BugController buntroller = (BugController) SimpleBugController.class
                    .getConstructor(Overlap2DStage.class).newInstance(stage);
            compI.addScript((IScript) buntroller);

        Vector2 pos = getPos();

        stage.addActor(compI);
        compI.setPosition(pos.x,pos.y);
        buntroller.startYPosition = pos.y;

        return buntroller;
    }

    public BugController spawn(Overlap2DStage stage, SceneLoader sceneLoader){
        try {
            return spawnUnsafe(stage, sceneLoader);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
