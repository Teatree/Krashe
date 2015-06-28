package game.stages;

import com.badlogic.gdx.Gdx;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.resources.ResourceManager;
import game.actors.BugController;
import game.actors.FlowerController;
import game.utils.MrSpawner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Teatree on 5/25/2015.
 */
public class GameStage extends Overlap2DStage {

    public FlowerController flowerController;
    public List<BugController> bugs = new LinkedList<>();

    public GameStage getInstance() {
        return this;
    }

    private int timer;
    final MrSpawner spawner = new MrSpawner();

    public GameStage(ResourceManager resourceManager) {

        initSceneLoader(resourceManager);

        sceneLoader.loadScene("MainScene");

        addActor(sceneLoader.getRoot());

        initFlower();
    }

    public void update() {
        timer++;
        if (timer == 100) {
            bugs.add(spawner.spawn(getInstance(), sceneLoader));
            timer = 0;
        }
        if (Gdx.input.isTouched() && isGameOver()) {
            getActors().removeRange(2, getActors().size - 1);
            reloadBugs();
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
        flowerL.setY(-500);

        flowerController.saIdle = flowerL.getSpriterActorById("floweridle_ani");
        flowerController.saClose = flowerL.getSpriterActorById("flowerattack_ani");
        flowerController.itemHeadC = flowerL.getImageById("flower_head");
        flowerController.itemPeduncleImg = flowerL.getImageById("flower_peduncle");

        addActor(flowerL);
    }

    public List<BugController> getBugs() {
        return bugs;
    }

    public void setBugs(List<BugController> bugs) {
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

    public boolean isGameOver() {
        if (bugs != null && !bugs.isEmpty()) {
            for (BugController bug : bugs) {
                if (bug.isOutOfBounds()) {
                    return true;
                }
            }
        }
        return false;
    }
}
