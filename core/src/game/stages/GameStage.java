package game.stages;

        import com.badlogic.gdx.Gdx;
        import com.sun.org.apache.xpath.internal.SourceTree;
        import com.uwsoft.editor.renderer.Overlap2DStage;
        import com.uwsoft.editor.renderer.actor.CompositeItem;
        import com.uwsoft.editor.renderer.resources.ResourceManager;
        import game.actors.Bug;
        import game.actors.controllers.FlowerController;
        import game.utils.BugGenerator;
        import game.utils.GlobalConstants;
        import game.utils.MrSpawner;

        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.List;
        import static game.utils.GlobalConstants.WAVE_DURATION;
        import static game.utils.GlobalConstants.WAVE_PAUSE_DURATION;

/**
 * Created by Teatree on 5/25/2015.
 */
public class GameStage extends Overlap2DStage {

    public FlowerController flowerController;
//    public List<BugController> bugs = new LinkedList<>();
    public List<Bug> bugs = new LinkedList<>();
    private int spawnInterval = 200;

    public GameStage getInstance(){return this;}
    private int intervalTimer;
    private int pauseTimer;
    private int waveTimer;
    final MrSpawner spawner = new MrSpawner();
    public BugGenerator bugGenerator = new BugGenerator();
    public static boolean isAngeredBeesMode = false;
    public static int angeredBeesTimer = 0;

    public GameStage(ResourceManager resourceManager) {

        initSceneLoader(resourceManager);

        sceneLoader.loadScene("MainScene");

        addActor(sceneLoader.getRoot());

        initFlower();
    }

    public void update(){
        if (pauseTimer <= 0) {
            intervalTimer++;
            waveTimer++;

            if (intervalTimer >= spawnInterval) {
                bugs.add(spawner.spawn(bugGenerator.getBugSafe(getInstance(), sceneLoader), getInstance()));
                intervalTimer = 0;
            }
            if (isAngeredBeesMode) {
                isAngeredBeesMode = angeredBeesTimer-- >= 0;
                spawnInterval = isAngeredBeesMode ? 50 : 200;
            }

            if (waveTimer >= WAVE_DURATION){
                pauseTimer = WAVE_PAUSE_DURATION;
                waveTimer = 0;
                System.out.println("SWITCHING TO PAUSE");
            }
        }else{
            pauseTimer--;
        }
        if (Gdx.input.isTouched() && isGameOver()) {
            getActors().removeRange(2, getActors().size - 1);
            reloadBugs();
            isAngeredBeesMode = false;
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

    public int getIntervalTimer() {
        return intervalTimer;
    }

    public void setIntervalTimer(int intervalTimer) {
        this.intervalTimer = intervalTimer;
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
}
