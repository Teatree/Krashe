package game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import game.actors.*;
import game.utils.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static game.utils.Animator.*;

/**
 * Created by MainUser on 26/07/2015.
 */
public class GameScreenScript implements IScript {

    public static boolean GAME_OVER = false;
    public static boolean GAME_PAUSED = false;

    private GameStage stage;
    private CompositeItem gameItem;

    public Flower flower;
    public static List<Bug> bugs = new LinkedList<>();
    private int spawnInterval = 200;
    public Random random = new Random();

    private int timer;
    final MrSpawner spawner = new MrSpawner();
    public BugGenerator bugGenerator;
    public static boolean isAngeredBeesMode = false;
    public static int angeredBeesTimer = 0;
    public DandelionPowerUp dandelionPowerup;
    public UmbrellaPowerUp umbrellaPowerUp;
    public CocoonPowerUp cocoonPowerUp;
    public ButterflyPowerUp butterflyPowerUp;
    public int dandelionSpawnCounter;
    public int cacoonSpawnCounter;

    private Animator uiController = new Animator();

    public GameScreenScript(GameStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        gameItem = item;

        dandelionSpawnCounter = random.nextInt(GlobalConstants.DANDELION_SPAWN_CHANCE_MAX - GlobalConstants.DANDELION_SPAWN_CHANCE_MIN) + GlobalConstants.DANDELION_SPAWN_CHANCE_MIN;
        cacoonSpawnCounter = random.nextInt(GlobalConstants.COCOON_SPAWN_MAX - GlobalConstants.COCOON_SPAWN_MIN) + GlobalConstants.COCOON_SPAWN_MIN;

        initPauseButton();

        uiController.init(this);
        bugGenerator = new BugGenerator(stage.sceneLoader);
    }

    public void initPauseButton() {
        final CompositeItem btnPause = gameItem.getCompositeById("btn_pause");

        btnPause.addListener(new ClickListener() {
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown(InputEvent event, float x, float y, int
                    pointer, int button) {
                touchDownButton(btnPause);
                uiController.showPausePopup();

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer,
                                int button) {
                touchUpButton(btnPause);
                uiController.pouseGame();
            }
        });
    }

    @Override
    public void dispose() {
//        SaveManager.saveProperties();
    }

    @Override
    public void act(float delta) {
        uiController.update();

        for (Bug bug : bugs) {
            if (bug.getController().isOutOfBounds() && flower.getCurHp() <= 0) {
                GAME_PAUSED = true;
            }
        }

        if (flower.getCurHp() <= 0) {
            uiController.showGameOverPopup();
        }

        if (flower.getCurHp() <= 0) {
            uiController.playGameOverTimer();
        }


        if (isGameAlive() && GlobalConstants.CUR_SCREEN == "GAME") {
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
                Flower.pointsAmount += Flower.sessionPointsAmount;
            }
            if (isAngeredBeesMode) {
                isAngeredBeesMode = angeredBeesTimer-- >= 0;
                spawnInterval = isAngeredBeesMode ? GlobalConstants.BEE_SPAWN_INTERVAL_ANGERED : GlobalConstants.BEE_SPAWN_INTERVAL_REGULAR;
            }

            if (dandelionSpawnCounter <= 0 && umbrellaPowerUp == null) {
                dandelionSpawnCounter = random.nextInt(GlobalConstants.DANDELION_SPAWN_CHANCE_MAX - GlobalConstants.DANDELION_SPAWN_CHANCE_MIN) + GlobalConstants.DANDELION_SPAWN_CHANCE_MIN;
                dandelionPowerup = new DandelionPowerUp(stage.sceneLoader, stage);
            }

            CollisionChecker.checkCollisions(stage);
        }
    }

    private void reloadBugs() {
        bugs = new ArrayList<>();
    }

    public boolean isGameOver() {
        for (Bug bug : bugs) {
            if (bug.getController().isOutOfBounds() && flower.getCurHp() <= 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGameAlive() {
        return !GAME_PAUSED && !GAME_OVER;
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


    public CompositeItem getGameItem() {
        return gameItem;
    }

    public void setGameItem(CompositeItem gameItem) {
        this.gameItem = gameItem;
    }

    public GameStage getStage() {
        return stage;
    }

    public void setStage(GameStage stage) {
        this.stage = stage;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }


}
