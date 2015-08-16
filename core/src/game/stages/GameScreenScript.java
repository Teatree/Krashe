package game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.Image9patchItem;
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

    private int aniTimer = 0;
    private String aniName;
    private boolean isPlayAny = false;
    private boolean pausePressed = false;
    private CompositeItem pausePopUp;
    private CompositeItem gameoverPopUp;
    private CompositeItem backShadow;
    private int gameOverCountDown = 300;

    public GameScreenScript(GameStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        gameItem = item;

        pausePopUp = gameItem.getCompositeById("pop-up_pause");
        pausePopUp.setVisible(false);
        gameoverPopUp = gameItem.getCompositeById("pop-up_gameover");
        gameoverPopUp.setVisible(false);
        backShadow = gameItem.getCompositeById("backShadow");
        backShadow.setVisible(false);
        pausePopUp.setColor(1,1,1,0);
        gameoverPopUp.setColor(1,1,1,0);
        backShadow.setColor(1,1,1,0);

        dandelionSpawnCounter = random.nextInt(GlobalConstants.DANDELION_SPAWN_CHANCE_MAX - GlobalConstants.DANDELION_SPAWN_CHANCE_MIN) + GlobalConstants.DANDELION_SPAWN_CHANCE_MIN;
        cacoonSpawnCounter = random.nextInt(GlobalConstants.COCOON_SPAWN_MAX - GlobalConstants.COCOON_SPAWN_MIN) + GlobalConstants.COCOON_SPAWN_MIN;

        final CompositeItem gameoverPopUpCloseBtn = gameoverPopUp.getCompositeById("btn_close");
        final CompositeItem gameoverPopUpVideoBtn = gameoverPopUp.getCompositeById("btn_vid");
        final CompositeItem pausePopUpContBtn = pausePopUp.getCompositeById("btn_continue");
        final CompositeItem pausePopUpExitBtn = pausePopUp.getCompositeById("btn_exit");
        final CompositeItem btnPause = gameItem.getCompositeById("btn_pause");

        btnPause.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                btnPause.setLayerVisibilty("normal", false);
                btnPause.setLayerVisibilty("pressed", true);
                playUIAnimation(100, "pauseAppear");

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
        pausePopUpContBtn.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                pausePopUpContBtn.setLayerVisibilty("normal", false);
                pausePopUpContBtn.setLayerVisibilty("pressed", true);
                playUIAnimation(100, "pauseDisappear");

                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                pausePopUpContBtn.setLayerVisibilty("normal", true);
                pausePopUpContBtn.setLayerVisibilty("pressed", false);

                pausePressed = false;
            }
        });
        pausePopUpExitBtn.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                pausePopUpExitBtn.setLayerVisibilty("normal", false);
                pausePopUpExitBtn.setLayerVisibilty("pressed", true);
                playUIAnimation(100, "pauseExit");

                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                pausePopUpExitBtn.setLayerVisibilty("normal", true);
                pausePopUpExitBtn.setLayerVisibilty("pressed", false);
//
                pausePressed = false;
            }
        });
        gameoverPopUpVideoBtn.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                gameoverPopUpVideoBtn.setLayerVisibilty("normal", false);
                gameoverPopUpVideoBtn.setLayerVisibilty("pressed", true);
                playUIAnimation(100, "gameoverDisappear");

                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                gameoverPopUpVideoBtn.setLayerVisibilty("normal", true);
                gameoverPopUpVideoBtn.setLayerVisibilty("pressed", false);

                pausePressed = false;
            }
        });
        gameoverPopUpCloseBtn.addListener(new ClickListener(){
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown (InputEvent event, float x, float y, int
                    pointer, int button) {
                gameoverPopUpCloseBtn.setLayerVisibilty("normal", false);
                gameoverPopUpCloseBtn.setLayerVisibilty("pressed", true);
                playUIAnimation(100, "gameoverExit");

                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer,
                                 int button) {
                gameoverPopUpCloseBtn.setLayerVisibilty("normal", true);
                gameoverPopUpCloseBtn.setLayerVisibilty("pressed", false);
//
                pausePressed = false;
            }
        });
    }

    @Override
    public void dispose() {

    }

    @Override
    public void act(float delta) {
        if(isPlayAny){
            // Don't touch this
            aniTimer--;
            //

            // Play gameover pop-up appear animation
            if(aniName == "gameoverAppear"){
                gameoverPopUp.setVisible(true);
                backShadow.setVisible(true);

                if(gameoverPopUp.getColor().a<1){
                    gameoverPopUp.setColor(1,1,1,gameoverPopUp.getColor().a+0.1f); // Some sort of a formula would be good here
                    backShadow.setColor(1,1,1,backShadow.getColor().a+0.1f); // like a formula that replaces 0.1f with a portion of aniTimer
                }
                if(gameoverPopUp.getColor().a==1){
                    GlobalConstants.GAME_PAUSED = true;
                }
            }
            //

            // Play gameover pop-up disappear animation
            if(aniName == "gameoverDisappear"){
                if(gameoverPopUp.getColor().a>0){
                    gameoverPopUp.setColor(1,1,1,gameoverPopUp.getColor().a-0.1f);
                    backShadow.setColor(1,1,1,backShadow.getColor().a-0.1f);
                }
                if(gameoverPopUp.getColor().a==0){
                    GlobalConstants.GAME_PAUSED = false;
                    gameoverPopUp.setVisible(false);
                    backShadow.setVisible(false);

                    flower.setCurHp(3);
                    gameOverCountDown = 300;
                }
            }
            //

            // Play gameover pop-up disappear animation with Exit
            if(aniName == "gameoverExit"){
                if(gameoverPopUp.getColor().a>0){
                    gameoverPopUp.setColor(1,1,1,gameoverPopUp.getColor().a-0.1f);
                    backShadow.setColor(1,1,1,backShadow.getColor().a-0.1f);
                }
                if(gameoverPopUp.getColor().a==0){
                    GlobalConstants.GAME_PAUSED = false;
                    gameoverPopUp.setVisible(false);
                    backShadow.setVisible(false);

                    flower.setCurHp(3);
                    stage.initMenu();
                    isPlayAny = false; // this is bad, think of a formula!
                }
            }
            //

            // Play pause pop-up appear animation
            if(aniName == "pauseAppear"){
                pausePopUp.setVisible(true);
                backShadow.setVisible(true);

                if(pausePopUp.getColor().a<1){
                    pausePopUp.setColor(1,1,1,pausePopUp.getColor().a+0.1f); // Some sort of a formula would be good here
                    backShadow.setColor(1,1,1,backShadow.getColor().a+0.1f); // like a formula that replaces 0.1f with a portion of aniTimer
                }
                if(pausePopUp.getColor().a==1){
                    GlobalConstants.GAME_PAUSED = true;
                }
            }
            //

            // Play pause pop-up disappear animation
            if(aniName == "pauseDisappear"){
                if(pausePopUp.getColor().a>0){
                    pausePopUp.setColor(1,1,1,pausePopUp.getColor().a-0.1f);
                    backShadow.setColor(1,1,1,backShadow.getColor().a-0.1f);
                }
                if(pausePopUp.getColor().a==0){
                    GlobalConstants.GAME_PAUSED = false;
                    pausePopUp.setVisible(false);
                    backShadow.setVisible(false);
                }
            }
            //

            // Play pause disappear animation with Exit
            if(aniName == "pauseExit"){
                if(pausePopUp.getColor().a>0){
                    pausePopUp.setColor(1,1,1,pausePopUp.getColor().a-0.1f);
                    backShadow.setColor(1,1,1,backShadow.getColor().a-0.1f);
                }
                if(pausePopUp.getColor().a==0){
                    GlobalConstants.GAME_PAUSED = false;
                    pausePopUp.setVisible(false);
                    backShadow.setVisible(false);

                    stage.initMenu();
                    isPlayAny = false; // this is bad, think of a formula!
                }
            }
            //

            // Don't touch this
            if(aniTimer<=0){
                isPlayAny = false;
            }
            //
        }
        for(Bug bug : bugs){
            if (bug.getController().isOutOfBounds() && flower.getCurHp()<=0){
                GlobalConstants.GAME_PAUSED = true;
            }
        }
        if(flower.getCurHp()<=0 && !gameoverPopUp.isVisible()){
            playUIAnimation(100, "gameoverAppear");

        }
        if(flower.getCurHp()<=0) {
            gameOverCountDown--;
            if (gameOverCountDown > 241) {
                gameoverPopUp.getLabelById("pop-up_gameover_timer_txt").setText("5");
            } else if (gameOverCountDown > 181 && gameOverCountDown < 240) {
                gameoverPopUp.getLabelById("pop-up_gameover_timer_txt").setText("4");
            } else if (gameOverCountDown > 121 && gameOverCountDown < 180) {
                gameoverPopUp.getLabelById("pop-up_gameover_timer_txt").setText("3");
            } else if (gameOverCountDown > 61 && gameOverCountDown < 120) {
                gameoverPopUp.getLabelById("pop-up_gameover_timer_txt").setText("2");
            } else if (gameOverCountDown > 0 && gameOverCountDown < 60) {
                gameoverPopUp.getLabelById("pop-up_gameover_timer_txt").setText("1");
            } else if (gameOverCountDown <= 0) {
                gameoverPopUp.getLabelById("pop-up_gameover_timer_txt").setText("0");
                playUIAnimation(100, "gameoverExit");
            }
        }

        if(!GlobalConstants.GAME_PAUSED && !GlobalConstants.GAME_OVER && GlobalConstants.CUR_SCREEN == "GAME") {
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

    private void playUIAnimation(int timer, String name){
        isPlayAny = true;
        aniTimer = timer;
        aniName = name;
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
