package game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.resources.ResourceManager;
import game.actors.*;
import game.utils.BugGenerator;
import game.utils.CollisionChecker;
import game.utils.GlobalConstants;
import game.utils.MrSpawner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Teatree on 5/25/2015.
 */
public class GameStage extends Overlap2DStage {

    public Flower flower;
    public List<Bug> bugs = new LinkedList<>();
    private int spawnInterval = 200;
    public Random random = new Random();

    public GameStage getInstance() {
        return this;
    }

    private int timer;
    final MrSpawner spawner = new MrSpawner();
    public BugGenerator bugGenerator = new BugGenerator();
    public static boolean isAngeredBeesMode = false;
    public static int angeredBeesTimer = 0;
    public DandelionPowerUp dandelionPowerup;
    public UmbrellaPowerUp umbrellaPowerUp;
    public CocoonPowerUp cocoonPowerUp;
    public ButterflyPowerUp butterflyPowerUp;
    public int dandelionSpawnCounter;
    public int cacoonSpawnCounter;

    public GameStage(ResourceManager resourceManager) {

        initSceneLoader(resourceManager);

        initGame();
    }

    public void initGame() {
        clear();
        sceneLoader.loadScene("MainScene");

        addActor(sceneLoader.getRoot());

        Flower.init(this, sceneLoader);

        dandelionSpawnCounter = random.nextInt(GlobalConstants.DANDELION_SPAWN_CHANCE_MAX - GlobalConstants.DANDELION_SPAWN_CHANCE_MIN) + GlobalConstants.DANDELION_SPAWN_CHANCE_MIN;
        cacoonSpawnCounter = random.nextInt(GlobalConstants.COCOON_SPAWN_MAX - GlobalConstants.COCOON_SPAWN_MIN) + GlobalConstants.COCOON_SPAWN_MIN;
    }

    public void initMenu(){
        sceneLoader.loadScene("MenuScene");
        MenuScreenScript menu = new MenuScreenScript(this);
        sceneLoader.sceneActor.addScript(menu);
        addActor(sceneLoader.sceneActor);
    }

    public void update() {
        timer++;
        dandelionSpawnCounter--;
        cacoonSpawnCounter--;

        if (timer >= spawnInterval) {
            bugs.add(spawner.spawn(bugGenerator.getBugSafe(getInstance(), sceneLoader), getInstance()));
            timer = 0;
        }
        if (Gdx.input.isTouched() && isGameOver()) {
            getActors().removeRange(2, getActors().size - 1);
            reloadBugs();
            isAngeredBeesMode = false;
        }
        if (isAngeredBeesMode) {
            isAngeredBeesMode = angeredBeesTimer-- >= 0;
            spawnInterval = isAngeredBeesMode ? GlobalConstants.BEE_SPAWN_INTERVAL_ANGERED : GlobalConstants.BEE_SPAWN_INTERVAL_REGULAR;
        }

        if (dandelionSpawnCounter <= 0 && umbrellaPowerUp == null){
            dandelionSpawnCounter = random.nextInt(GlobalConstants.DANDELION_SPAWN_CHANCE_MAX-GlobalConstants.DANDELION_SPAWN_CHANCE_MIN)+GlobalConstants.DANDELION_SPAWN_CHANCE_MIN;
            dandelionPowerup = new DandelionPowerUp(sceneLoader, this);
        }

//        if (cacoonSpawnCounter <= 0 && butterflyPowerUp == null) {
//            cacoonSpawnCounter = random.nextInt(GlobalConstants.COCOON_SPAWN_MAX - GlobalConstants.COCOON_SPAWN_MIN) + GlobalConstants.COCOON_SPAWN_MIN;
//            cocoonPowerUp = new CocoonPowerUp(sceneLoader, this);
//        }

        CollisionChecker.checkCollisions(this);

    }

    private void reloadBugs() {
        bugs = new ArrayList<>();
    }

//    private void init() {
////        FlowerController flowerController = new FlowerController(this);
////        CompositeItem flowerL = sceneLoader.getLibraryAsActor("flowerLib");
//
//        flower = new Flower(new FlowerController(this), sceneLoader);
////        flowerL.setX(1800);
////        flowerL.setY(-410);
//        flower.setPosition(1800, -410);
//
////        addActor(flowerL);
//        addActor(flower.getFlowerLib());
//    }


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

    public boolean isGameOver() {
//        for(Bug bug : bugs){
//            if (bug.getController().isOutOfBounds()){
//                return true;
//            }
//        }
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
