package game.stages;

        import com.uwsoft.editor.renderer.Overlap2DStage;
        import com.uwsoft.editor.renderer.actor.CompositeItem;
        import com.uwsoft.editor.renderer.resources.ResourceManager;
        import game.actors.BugController;
        import game.actors.DrunkBugController;
        import game.actors.FlowerController;

        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.List;

/**
 * Created by Teatree on 5/25/2015.
 */
public class GameStage extends Overlap2DStage {

    public static List<BugController> bugs = new LinkedList<>();

    public GameStage(ResourceManager resourceManager) {

        initSceneLoader(resourceManager);

        sceneLoader.loadScene("MainScene");

        addActor(sceneLoader.getRoot());

        DrunkBugController drunkBugController = new DrunkBugController(this);
        DrunkBugController drunkBugController2 = new DrunkBugController(this);
        FlowerController flowerController = new FlowerController(this);
//        sceneLoader.getRoot().getCompositeById("drunkBug").addScript(drunkBug);
        CompositeItem drunkBug = sceneLoader.getLibraryAsActor("drunkBugLib");
        CompositeItem drunkBug2 = sceneLoader.getLibraryAsActor("drunkBugLib");
        bugs.add(drunkBugController);
        bugs.add(drunkBugController2);
        CompositeItem flowerL = sceneLoader.getLibraryAsActor("flowerLib");

//            drunkBug.setX(-drunkBug.getWidth());
//            drunkBug.setX(-drunkBug.getWidth());
//            drunkBug.addItem(drunkBug);
        drunkBug.addScript(drunkBugController);
        drunkBug.setX(100);
        drunkBug2.addScript(drunkBugController2);
        drunkBug2.setX(400);
        drunkBug2.setY(1900);
        flowerL.addScript(flowerController);
        flowerL.setX(2200);

        flowerController.saIdle = flowerL.getSpriterActorById("floweridle_ani");
        flowerController.saClose = flowerL.getSpriterActorById("flowerattack_ani");
        flowerController.itemHeadC = flowerL.getCompositeById("flower_headC");
        flowerController.itemPeduncleImg = flowerL.getImageById("flower_peduncle");

        addActor(flowerL);
        addActor(drunkBug);
        addActor(drunkBug2);

//        for(int i = 0;  i < 3; i++) {
//            CompositeItem pipe = sceneLoader.getLibraryAsActor("pipeGroup");
//            pipe.setX(-pipe.getWidth());
//            game.addItem(pipe);
//
//            pipes.add(pipe);
//        }

//        FlowerController flowerController = new FlowerController(this);
//        sceneLoader.getRoot().getCompositeById("drunkBug").addScript(flowerController);
    }


}
