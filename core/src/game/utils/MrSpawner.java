package game.utils;

import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import game.actors.*;
import game.actors.controllers.BugController;
import game.actors.controllers.ChargerBugController;
import game.actors.controllers.DrunkBugController;
import game.actors.controllers.SimpleBugController;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by MainUser on 21/06/2015.
 */
public class MrSpawner {

    Random rand = new Random();
    private static int MIN_X;
    private static int MAX_X;
    private static int MIN_Y;
    private static int MAX_Y;

    public MrSpawner(){
        init();
    }

    private void init(){
        MIN_X = -400;
        MIN_Y = 300;
        MAX_X = -200;
        MAX_Y = 1200;
    }

    private Vector2 getPos(){
        float x = rand.nextInt(MAX_X-MIN_X)+MIN_X;
        float y = rand.nextInt(MAX_Y-MIN_Y)+MIN_Y;

        return new Vector2(x,y);
    }

    public Bug spawnUnsafe(Bug bug, Overlap2DStage stage) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        bug.setPosition(getPos());
        stage.addActor(bug.getCompositeItem());
        return bug;
    }

    public Bug spawn(Bug bug, Overlap2DStage stage){
        try {
            return spawnUnsafe(bug, stage);
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
