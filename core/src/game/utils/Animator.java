package game.utils;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import game.stages.GameScreenScript;

import static game.stages.GameScreenScript.*;

/**
 * Created by MainUser on 22/08/2015.
 */
public class Animator {


    public static final String GAMEOVER_DISAPPEAR = "gameoverDisappear";
    public static final String GAMEOVER_APPEAR = "gameoverAppear";
    public static final String GAMEOVER_EXIT = "gameoverExit";
    public static final String PAUSE_EXIT = "pauseExit";
    public static final String PAUSE_DISAPPEAR = "pauseDisappear";
    public static final String PAUSE_APPEAR = "pauseAppear";
    public static final String NORMAL_BTN_STATE = "normal";
    public static final String PRESSED_BTN_STATE = "pressed";
    private int aniTimer = 0;
    private float aniAddition = 0F;
    private String aniName;
    private boolean isPlayAny = false;
    private boolean pausePressed = false;
    private CompositeItem pausePopUp;
    private CompositeItem gameoverPopUp;
    private CompositeItem backShadow;
    private int gameOverCountDown = 300;

    private GameScreenScript game;

    public void init(GameScreenScript game) {
        this.game = game;
        pausePopUp = game.getGameItem().getCompositeById("pop-up_pause");
        pausePopUp.setVisible(false);
        gameoverPopUp = game.getGameItem().getCompositeById("pop-up_gameover");
        gameoverPopUp.setVisible(false);
        backShadow = game.getGameItem().getCompositeById("backShadow");
        backShadow.setVisible(false);
        pausePopUp.setColor(1, 1, 1, 0);
        gameoverPopUp.setColor(1, 1, 1, 0);
        backShadow.setColor(1, 1, 1, 0);

        final CompositeItem gameoverPopUpCloseBtn = gameoverPopUp.getCompositeById("btn_close");
        final CompositeItem gameoverPopUpVideoBtn = gameoverPopUp.getCompositeById("btn_vid");
        final CompositeItem pausePopUpContBtn = pausePopUp.getCompositeById("btn_continue");
        final CompositeItem pausePopUpExitBtn = pausePopUp.getCompositeById("btn_exit");

        pausePopUpContBtn.addListener(new ClickListener() {
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown(InputEvent event, float x, float y, int
                    pointer, int button) {
                touchDownButton(pausePopUpContBtn);
                playUIAnimation(10, PAUSE_DISAPPEAR);

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer,
                                int button) {
                touchUpButton(pausePopUpContBtn);
                pausePressed = false;
            }
        });
        pausePopUpExitBtn.addListener(new ClickListener() {
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown(InputEvent event, float x, float y, int
                    pointer, int button) {
//                pausePopUpExitBtn.setLayerVisibilty(NORMAL_BTN_STATE, false);
//                pausePopUpExitBtn.setLayerVisibilty(PRESSED_BTN_STATE, true);
                touchDownButton(pausePopUpExitBtn);
                playUIAnimation(10, PAUSE_EXIT);

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer,
                                int button) {
//                pausePopUpExitBtn.setLayerVisibilty(NORMAL_BTN_STATE, true);
//                pausePopUpExitBtn.setLayerVisibilty(PRESSED_BTN_STATE, false);
                touchUpButton(pausePopUpExitBtn);
                pausePressed = false;
            }
        });
        gameoverPopUpVideoBtn.addListener(new ClickListener() {
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown(InputEvent event, float x, float y, int
                    pointer, int button) {
                touchDownButton(gameoverPopUpVideoBtn);
//                gameoverPopUpVideoBtn.setLayerVisibilty(NORMAL_BTN_STATE, false);
//                gameoverPopUpVideoBtn.setLayerVisibilty(PRESSED_BTN_STATE, true);
                playUIAnimation(10, GAMEOVER_DISAPPEAR);

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer,
                                int button) {
//                gameoverPopUpVideoBtn.setLayerVisibilty(NORMAL_BTN_STATE, true);
//                gameoverPopUpVideoBtn.setLayerVisibilty(PRESSED_BTN_STATE, false);
                touchUpButton(gameoverPopUpVideoBtn);
                pausePressed = false;
            }
        });
        gameoverPopUpCloseBtn.addListener(new ClickListener() {
            // Need to keep touch down in order for touch up to work normal (libGDX awkwardness)
            public boolean touchDown(InputEvent event, float x, float y, int
                    pointer, int button) {
                touchDownButton(gameoverPopUpCloseBtn);
                playUIAnimation(10, GAMEOVER_EXIT);

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer,
                                int button) {
//                gameoverPopUpCloseBtn.setLayerVisibilty(NORMAL_BTN_STATE, true);
//                gameoverPopUpCloseBtn.setLayerVisibilty(PRESSED_BTN_STATE, false);
                touchUpButton(gameoverPopUpCloseBtn);
                pausePressed = false;
            }
        });
    }

    public void update() {
        if (isPlayAny) {
            // Don't touch this
            aniTimer--;
            //

            // Play gameover pop-up appear animation
            if (GAMEOVER_APPEAR.equals(aniName)) {
                gameoverPopUp.setVisible(true);
                backShadow.setVisible(true);

                if (gameoverPopUp.getColor().a < 1) {
                    gameoverPopUp.setColor(1, 1, 1, gameoverPopUp.getColor().a + aniAddition);
                    backShadow.setColor(1, 1, 1, backShadow.getColor().a + aniAddition); // like a formula that replaces 0.1f with a portion of aniTimer
                }
                if (gameoverPopUp.getColor().a == 1) {
                    GAME_PAUSED = true;
                }
            }
            //

            // Play gameover pop-up disappear animation
            if (GAMEOVER_DISAPPEAR.equals(aniName)) {
                if (gameoverPopUp.getColor().a > 0) {
                    gameoverPopUp.setColor(1, 1, 1, gameoverPopUp.getColor().a - aniAddition);
                    backShadow.setColor(1, 1, 1, backShadow.getColor().a - aniAddition);
                }
                if (gameoverPopUp.getColor().a == 0) {
                    GAME_PAUSED = false;
                    gameoverPopUp.setVisible(false);
                    backShadow.setVisible(false);

                    game.getFlower().setCurHp(3);
                    gameOverCountDown = 300;
                }
            }
            //

            // Play gameover pop-up disappear animation with Exit
            if (GAMEOVER_EXIT.equals(aniName)) {
                if (gameoverPopUp.getColor().a > 0) {
                    gameoverPopUp.setColor(1, 1, 1, gameoverPopUp.getColor().a - aniAddition);
                    backShadow.setColor(1, 1, 1, backShadow.getColor().a - aniAddition);
                }
                if (gameoverPopUp.getColor().a == 0) {
                    GAME_PAUSED = false;
                    gameoverPopUp.setVisible(false);
                    backShadow.setVisible(false);

                    game.getFlower().setCurHp(3);
                    game.getStage().initMenu();
                    isPlayAny = false;
                }
            }
            //

            // Play pause pop-up appear animation
            if (PAUSE_APPEAR.equals(aniName)) {
                pausePopUp.setVisible(true);
                backShadow.setVisible(true);

                if (pausePopUp.getColor().a < 1) {
                    pausePopUp.setColor(1, 1, 1, pausePopUp.getColor().a + aniAddition);
                    backShadow.setColor(1, 1, 1, backShadow.getColor().a + aniAddition);
                }
                if (pausePopUp.getColor().a == 1) {
                    GAME_PAUSED = true;
                    isPlayAny = false;
                }
            }
            //

            // Play pause pop-up disappear animation
            if (PAUSE_DISAPPEAR.equals(aniName)) {
                if (pausePopUp.getColor().a > 0) {
                    pausePopUp.setColor(1, 1, 1, pausePopUp.getColor().a - aniAddition);
                    backShadow.setColor(1, 1, 1, backShadow.getColor().a - aniAddition);
                }
                if (pausePopUp.getColor().a == 0) {
                    GAME_PAUSED = false;
                    pausePopUp.setVisible(false);
                    backShadow.setVisible(false);
                    isPlayAny = false;
                }
            }
            //

            // Play pause disappear animation with Exit
            if (PAUSE_EXIT.equals(aniName)) {
                if (pausePopUp.getColor().a > 0) {
                    pausePopUp.setColor(1, 1, 1, pausePopUp.getColor().a - aniAddition);
                    backShadow.setColor(1, 1, 1, backShadow.getColor().a - aniAddition);
                }
                if (pausePopUp.getColor().a == 0) {
                    GAME_PAUSED = false;
                    pausePopUp.setVisible(false);
                    backShadow.setVisible(false);

                    isPlayAny = false;
                    game.getStage().initMenu();
                }
            }
            //

            // Don't touch this
            if (aniTimer <= 0) {
                isPlayAny = false;
            }
            //
        }
    }

    private void playUIAnimation(int timer, String name) {
        isPlayAny = true;
        aniTimer = timer;
        aniAddition = 1f / aniTimer;
        aniName = name;
    }

    public void showPausePopup() {
        playUIAnimation(10, PAUSE_APPEAR);
        pausePressed = true;
    }

    public void playGameOverTimer() {
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
            playUIAnimation(100, GAMEOVER_EXIT);
        }
    }

    public void showGameOverPopup() {
        if (!gameoverPopUp.isVisible()) {
            playUIAnimation(100, GAMEOVER_APPEAR);
        }
    }

    public void pouseGame() {
        pausePressed = false;
    }

    public static void touchDownButton(CompositeItem button) {
        button.setLayerVisibilty(NORMAL_BTN_STATE, false);
        button.setLayerVisibilty(PRESSED_BTN_STATE, true);
    }

    public static void touchUpButton(CompositeItem button) {
        button.setLayerVisibilty(NORMAL_BTN_STATE, true);
        button.setLayerVisibilty(PRESSED_BTN_STATE, false);
    }


}
