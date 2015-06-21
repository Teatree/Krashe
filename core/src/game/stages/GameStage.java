package game.stages;

        import com.badlogic.gdx.utils.Timer;
        import com.uwsoft.editor.renderer.Overlap2DStage;
        import com.uwsoft.editor.renderer.actor.CompositeItem;
        import com.uwsoft.editor.renderer.resources.ResourceManager;
        import game.actors.BugController;
        import game.actors.DrunkBugController;
        import game.actors.FlowerController;
        import game.utils.MrSpawner;

        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.List;

/**
 * Created by Teatree on 5/25/2015.
 */
public class GameStage extends Overlap2DStage {

    public static volatile List<BugController> bugs = new LinkedList<>();
    public GameStage getInstance(){return this;}

    public GameStage(ResourceManager resourceManager) {

        initSceneLoader(resourceManager);

        sceneLoader.loadScene("MainScene");

        addActor(sceneLoader.getRoot());

        final MrSpawner spawner = new MrSpawner();
//        bugs.add(spawner.spawn(this, sceneLoader));
//        bugs.add(spawner.spawn(this, sceneLoader));

        synchronized (bugs){
            Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                bugs.add(spawner.spawn(getInstance(), sceneLoader));
            }
        }, 0.5F, 2F);}

        initFlower();
    }

    private void initFlower() {
        FlowerController flowerController = new FlowerController(this);
        CompositeItem flowerL = sceneLoader.getLibraryAsActor("flowerLib");

        flowerL.addScript(flowerController);
        flowerL.setX(1800);
        flowerL.setY(-500);

        flowerController.saIdle = flowerL.getSpriterActorById("floweridle_ani");
        flowerController.saClose = flowerL.getSpriterActorById("flowerattack_ani");
        flowerController.itemHeadC = flowerL.getImageById("flower_head");
        flowerController.itemPeduncleImg = flowerL.getImageById("flower_peduncle");

        addActor(flowerL);
    }


}
