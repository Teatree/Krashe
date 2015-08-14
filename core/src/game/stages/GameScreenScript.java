package game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
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
 * Created by MainUser on 26/07/2015.
 */
public class GameScreenScript implements IScript {

    private GameStage stage;
    private CompositeItem gameItem;

    public Flower flower;
    public List<Bug> bugs = new LinkedList<>();
    private int spawnInterval = 200;
    public Random random = new Random();

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

    private boolean pausePressed = false;

    public GameScreenScript(GameStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        gameItem = item;
        dandelionSpawnCounter = random.nextInt(GlobalConstants.DANDELION_SPAWN_CHANCE_MAX - GlobalConstants.DANDELION_SPAWN_CHANCE_MIN) + GlobalConstants.DANDELION_SPAWN_CHANCE_MIN;
        cacoonSpawnCounter = random.nextInt(GlobalConstants.COCOON_SPAWN_MAX - GlobalConstants.COCOON_SPAWN_MIN) + GlobalConstants.COCOON_SPAWN_MIN;
        final CompositeItem btnPause = gameItem.getCompositeById("btn_pause");

        btnPause.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                btnPause.setLayerVisibilty("normal", false);
                btnPause.setLayerVisibilty("pressed", true);

                GlobalConstants.GAME_PAUSED = true;
                pausePressed = true;

                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                btnPause.setLayerVisibilty("normal", true);
                btnPause.setLayerVisibilty("pressed", false);
                pausePressed = false;
            }
        });
    }

    @Override
    public void dispose() {

    }

    @Override
    public void act(float delta) {
        for(Bug bug : bugs){
            if (bug.getController().isOutOfBounds() && flower.getCurHp()<=0){
                GlobalConstants.GAME_OVER = true;
            }
        }
        if(GlobalConstants.GAME_PAUSED && !pausePressed && Gdx.input.justTouched()){
            GlobalConstants.GAME_PAUSED = false;
        }
        if(!GlobalConstants.GAME_PAUSED) {
            timer++;
            dandelionSpawnCounter--;
            cacoonSpawnCounter--;

            if (timer >= spawnInterval) {
                bugs.add(spawner.spawn(bugGenerator.getBugSafe(stage.getInstance(), stage.sceneLoader), stage.getInstance()));
                timer = 0;
            }
            if (Gdx.input.isTouched() && isGameOver()) {
                stage.getActors().removeRange(2, stage.getActors().size - 1);
                reloadBugs();
                isAngeredBeesMode = false;
            }
            if (isAngeredBeesMode) {
                isAngeredBeesMode = angeredBeesTimer-- >= 0;
                spawnInterval = isAngeredBeesMode ? GlobalConstants.BEE_SPAWN_INTERVAL_ANGERED : GlobalConstants.BEE_SPAWN_INTERVAL_REGULAR;
            }

            if (dandelionSpawnCounter <= 0 && umbrellaPowerUp == null) {
                dandelionSpawnCounter = random.nextInt(GlobalConstants.DANDELION_SPAWN_CHANCE_MAX - GlobalConstants.DANDELION_SPAWN_CHANCE_MIN) + GlobalConstants.DANDELION_SPAWN_CHANCE_MIN;
                dandelionPowerup = new DandelionPowerUp(stage.sceneLoader, stage);
            }

//        if (cacoonSpawnCounter <= 0 && butterflyPowerUp == null) {
//            cacoonSpawnCounter = random.nextInt(GlobalConstants.COCOON_SPAWN_MAX - GlobalConstants.COCOON_SPAWN_MIN) + GlobalConstants.COCOON_SPAWN_MIN;
//            cocoonPowerUp = new CocoonPowerUp(sceneLoader, this);
//        }

            CollisionChecker.checkCollisions(stage);
        }
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
        for(Bug bug : bugs){
            if (bug.getController().isOutOfBounds() && flower.getCurHp()<=0){
                return true;
            }
        }
        return false;
    }

}
