package game.utils;

        import com.uwsoft.editor.renderer.Overlap2DStage;
        import com.uwsoft.editor.renderer.SceneLoader;
        import com.uwsoft.editor.renderer.actor.CompositeItem;
        import game.actors.Bug;
        import game.actors.controllers.*;
        import game.stages.GameScreenScript;
        import game.stages.GameStage;

        import java.lang.reflect.InvocationTargetException;
        import java.util.HashMap;
        import java.util.Map;
        import java.util.Random;

/**
 * Created by MainUser on 11/07/2015.
 */
public class BugGenerator {
    private HashMap<String, Class> libBugs = new HashMap<>();
    private Random rand = new Random();

    public BugGenerator(){
        init();
    }

    private void init() {
        libBugs.put("drunkBugLib", DrunkBugController.class);
        libBugs.put("chargerBugLib", ChargerBugController.class);
        libBugs.put("simpleBugLib", SimpleBugController.class);
        libBugs.put("beeBugLib", BeeBugController.class);
        libBugs.put("queenBeeLib", QueenBeeBugController.class);
    }

    private Map.Entry<String, Class> getType(){
        if (!GameScreenScript.isAngeredBeesMode) {
            int probabilityValue = rand.nextInt(100);
            if (probabilityValue < 10) {
                //Drunk
                return (Map.Entry<String, Class>) libBugs.entrySet().toArray()[0];
            } else if (probabilityValue >= 10 && probabilityValue < 40) {
                //Simple
                return (Map.Entry<String, Class>) libBugs.entrySet().toArray()[2];
            } else if (probabilityValue >= 41 && probabilityValue < 60) {
                //Charger
                return (Map.Entry<String, Class>) libBugs.entrySet().toArray()[1];
            } else if (probabilityValue >= 61 && probabilityValue < 70){
                return (Map.Entry<String, Class>) libBugs.entrySet().toArray()[3];
            } else {
                return (Map.Entry<String, Class>) libBugs.entrySet().toArray()[4];
            }
        }else{
            return (Map.Entry<String, Class>) libBugs.entrySet().toArray()[3];
        }
    }

    public Bug getBugUnsafe(Overlap2DStage stage, SceneLoader sceneLoader) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map.Entry<String, Class> type = getType();
        CompositeItem compI = sceneLoader.getLibraryAsActor(type.getKey());

        BugController buntroller = (BugController) type.getValue().getConstructor(Overlap2DStage.class).newInstance(stage);

        return new Bug(buntroller, compI);
    }

    public Bug getBugSafe(Overlap2DStage stage, SceneLoader sceneLoader){
        try {
            return getBugUnsafe(stage, sceneLoader);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
