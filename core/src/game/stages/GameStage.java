package game.stages;

        import com.uwsoft.editor.renderer.Overlap2DStage;
        import com.uwsoft.editor.renderer.actor.CompositeItem;
        import com.uwsoft.editor.renderer.resources.ResourceManager;
        import game.actors.DrunkBugController;
        import game.actors.FlowerController;
        import game.utils.AssetsManager;

/**
 * Created by Teatree on 5/25/2015.
 */
public class GameStage extends Overlap2DStage {

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
        CompositeItem drunkBug3 = sceneLoader.getLibraryAsActor("flowerLib");
//            drunkBug.setX(-drunkBug.getWidth());
//            drunkBug.addItem(drunkBug);
        drunkBug.addScript(drunkBugController);
        drunkBug.setX(200);
        drunkBug2.addScript(drunkBugController2);
        drunkBug2.setX(300);
        drunkBug3.addScript(flowerController);
        drunkBug3.setX(500);

        flowerController.saIdle = drunkBug3.getSpriterActorById("blueBugAni");
        flowerController.saClose = drunkBug3.getSpriterActorById("drunkBugAni");
        addActor(drunkBug);
        addActor(drunkBug2);
        addActor(drunkBug3);

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
