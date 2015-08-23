package game.utils;

        import com.sun.org.apache.xpath.internal.SourceTree;
        import com.uwsoft.editor.renderer.Overlap2DStage;
        import com.uwsoft.editor.renderer.SceneLoader;
        import com.uwsoft.editor.renderer.actor.CompositeItem;
        import game.actors.Bug;
        import game.actors.controllers.*;
        import game.stages.GameScreenScript;
        import game.stages.GameStage;

        import java.lang.reflect.InvocationTargetException;
        import java.util.*;

/**
 * Created by MainUser on 11/07/2015.
 */
public class BugGenerator {
    private HashMap<String, Class> libBugs = new HashMap<>();
    private Random rand = new Random();

    public BugGenerator(SceneLoader sceneLoader) {
        init(sceneLoader);
    }

    Stack<CompositeItem> availableBugCompItems = new Stack<>();

    private void init(SceneLoader sceneLoader) {
        libBugs.put("Drunk_Bug_layer", DrunkBugController.class);
        libBugs.put("Charger_Bug_layer", ChargerBugController.class);
        libBugs.put("Simple_Bug_layer", SimpleBugController.class);
        libBugs.put("Bee_layer", BeeBugController.class);
        libBugs.put("Queen_layer", QueenBeeBugController.class);

        availableBugCompItems.add(sceneLoader.getCompositeElementById("bug_1"));
        availableBugCompItems.add(sceneLoader.getCompositeElementById("bug_2"));
        availableBugCompItems.add(sceneLoader.getCompositeElementById("bug_3"));
        availableBugCompItems.add(sceneLoader.getCompositeElementById("bug_4"));
        availableBugCompItems.add(sceneLoader.getCompositeElementById("bug_5"));
        availableBugCompItems.add(sceneLoader.getCompositeElementById("bug_6"));
        availableBugCompItems.add(sceneLoader.getCompositeElementById("bug_7"));
        availableBugCompItems.add(sceneLoader.getCompositeElementById("bug_8"));
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
            } else if (probabilityValue >= 61 && probabilityValue < 70 && !hasQueen()){
                return (Map.Entry<String, Class>) libBugs.entrySet().toArray()[4];
            } else {
                return (Map.Entry<String, Class>) libBugs.entrySet().toArray()[3];
            }
        }else{
            return (Map.Entry<String, Class>) libBugs.entrySet().toArray()[3];
        }
    }

    public Bug getBugUnsafe(Overlap2DStage stage, SceneLoader sceneLoader) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map.Entry<String, Class> type = getType();
        CompositeItem compI = availableBugCompItems.pop();
        compI.setLayerVisibilty(type.getKey(), true);
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

    public boolean hasQueen(){
        for(Bug bug : GameScreenScript.bugs){
            if(bug.getController() instanceof QueenBeeBugController){
                return true;
            }
        }
        return false;
    }
}
