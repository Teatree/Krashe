package game.actors.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.script.IScript;
import game.stages.GameStage;

import static game.utils.GlobalConstants.POINT_TRAVEL;

/**
 * Created by MainUser on 07/06/2015.
 */
public class FlowerController implements IScript {

    private Overlap2DStage stage;

    private CompositeItem item;

    private boolean isMovingUp = false;

    public SpriterActor saIdle;
    public SpriterActor saClose;
    public SpriterActor saOpen;

    public Rectangle headBoundsRect = new Rectangle();

    public Image itemHeadC;
    public Image itemPeduncleImg;

    public FlowerController(Overlap2DStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        this.item = item;
        item.setX(Gdx.graphics.getWidth() - 200);
        item.setY(0);
        item.setOrigin(item.getWidth() / 2, 0);
    }

    public void addMovementActionUp() {
        item.addAction(
                Actions.sequence(
                        Actions.moveBy(0, 20)));
    }

    public void addMovementActionDown() {
        item.addAction(
                Actions.sequence(
                        Actions.moveBy(0, -20)));
    }

    @Override
    public void act(float delta) {
        if(!((GameStage)stage).isGameOver()) {
            updateRect();
//            checkForCollisions();

            if (Gdx.input.justTouched() && !isMovingUp && headBoundsRect.getY() < 1200) {
                System.out.print("Gdx.input.isTouched() "+Gdx.input.isTouched());
                System.out.println(" isMovingUp " + isMovingUp);
                isMovingUp = true;
                saClose.setAnimation(1);
            }

            if (!isMovingUp && headBoundsRect.getY() >= POINT_TRAVEL - 20) {
                addMovementActionDown();

                if (headBoundsRect.getY() <= POINT_TRAVEL) {
                    saIdle.setVisible(true);
                    saClose.setVisible(false);

                    itemHeadC.setVisible(false);
                    itemPeduncleImg.setVisible(false);
                }
            }

            if (isMovingUp) {
                addMovementActionUp();
                saIdle.setVisible(false);
                saClose.setVisible(false);

                itemHeadC.setVisible(true);
                itemPeduncleImg.setVisible(true);

                if (headBoundsRect.getY() > 1200) {
                    if (((GameStage) stage).cocoonPowerUp != null){
                        ((GameStage) stage).cocoonPowerUp.getCocoonController().hit();
                    }
                    isMovingUp = false;
                }
            }
        }
    }

    private void updateRect() {
        headBoundsRect.x = item.getX() + item.getImageById("flower_head").getX();
        headBoundsRect.y = item.getY() + item.getImageById("flower_head").getY();
        headBoundsRect.width = item.getImageById("flower_head").getImageWidth();
        headBoundsRect.height = item.getImageById("flower_head").getImageHeight();
    }

    @Override
    public void dispose() {

    }

}
