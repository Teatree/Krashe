package game.stages;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.scenes.scene2d.Actor;
        import com.uwsoft.editor.renderer.Overlap2DStage;
        import com.uwsoft.editor.renderer.actor.CompositeItem;
        import com.uwsoft.editor.renderer.resources.ResourceManager;
        import game.actors.Bug;
        import game.actors.DandelionPowerUp;
        import game.actors.controllers.DandelionController;
        import game.actors.controllers.FlowerController;
        import game.utils.BugGenerator;
        import game.utils.MrSpawner;

        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.List;

/**
 * Created by Teatree on 5/25/2015.
 */
public class GameStage extends Overlap2DStage {

    public FlowerController flowerController;
//    public List<BugController> bugs = new LinkedList<>();
    public List<Bug> bugs = new LinkedList<>();
    private int spawnInterval = 200;

    public GameStage getInstance(){return this;}
    private int timer;
    final MrSpawner spawner = new MrSpawner();
    public BugGenerator bugGenerator = new BugGenerator();
    public static boolean isAngeredBeesMode = false;
    public static int angeredBeesTimer = 0;
    public DandelionPowerUp dandelionPowerup;

    public GameStage(ResourceManager resourceManager) {

        initSceneLoader(resourceManager);

        sceneLoader.loadScene("MainScene");

        addActor(sceneLoader.getRoot());

        initFlower();

        dandelionPowerup = new DandelionPowerUp(sceneLoader, this);
    }

    public void update(){
        timer++;

        if (timer >= spawnInterval){
                bugs.add(spawner.spawn(bugGenerator.getBugSafe(getInstance(), sceneLoader), getInstance()));
            timer = 0;
        }
        if (Gdx.input.isTouched() && isGameOver()){
            getActors().removeRange(2, getActors().size-1);
            reloadBugs();
            isAngeredBeesMode = false;
        }
        if (isAngeredBeesMode){
            isAngeredBeesMode = angeredBeesTimer-- >= 0;
            spawnInterval = isAngeredBeesMode ? 50 : 200;
        }
    }

    private void reloadBugs() {
        bugs = new ArrayList<>();
    }

    private void initFlower() {

        flowerController = new FlowerController(this);
        CompositeItem flowerL = sceneLoader.getLibraryAsActor("flowerLib");

        flowerL.addScript(flowerController);
        flowerL.setX(1800);
        flowerL.setY(-410);

        flowerController.saIdle = flowerL.getSpriterActorById("floweridle_ani");
        flowerController.saClose = flowerL.getSpriterActorById("flowerattack_ani");
        flowerController.itemHeadC = flowerL.getImageById("flower_head");
        flowerController.itemPeduncleImg = flowerL.getImageById("flower_peduncle");

        addActor(flowerL);
    }


    public List<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public MrSpawner getSpawner() {
        return spawner;
    }

    public boolean isGameOver(){
        for(Bug bug : bugs){
            if (bug.getController().isOutOfBounds()){
                return true;
            }
        }
        return false;
    }

    public void removeActor(CompositeItem item) {
        for (Actor actor : this.getActors()) {
            if (actor.equals(item)) {
                actor.remove();
            }
        }
    }
}
