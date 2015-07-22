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
    private boolean isEating = false;

    private int eatCounter;

    public SpriterActor saFlower;
    public SpriterActor saHead;

    public Rectangle headBoundsRect = new Rectangle();

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

            if (Gdx.input.justTouched() && !isMovingUp && headBoundsRect.getY() < 1000){
                isEating = false;
                saHead.setAnimation(0);
                eatCounter = 0;
            }

            if (Gdx.input.justTouched() && !isMovingUp && headBoundsRect.getY() < 1000 && !isEating) {
                if(!isEating) {
                    System.out.print("Gdx.input.isTouched() " + Gdx.input.isTouched());
                    System.out.println(" isMovingUp " + isMovingUp);
                    isMovingUp = true;
                }
            }

            if (!isMovingUp && headBoundsRect.getY() >= POINT_TRAVEL - 20 && !isEating) {
                addMovementActionDown();

                if (headBoundsRect.getY() <= POINT_TRAVEL) {
                    saFlower.setVisible(true);

                    saHead.setVisible(false);
                    itemPeduncleImg.setVisible(false);
                }
            }

            if (isMovingUp && !isEating) {
                addMovementActionUp();
                saFlower.setVisible(false);

                saHead.setVisible(true);
                itemPeduncleImg.setVisible(true);

                if (headBoundsRect.getY() > 1000) {
                    if (((GameStage) stage).cocoonPowerUp != null){
                        ((GameStage) stage).cocoonPowerUp.getCocoonController().hit();
                    }
                    isMovingUp = false;
                }
            }

            if (isEating){
                eatCounter++;
                if(eatCounter>=30){
                    isMovingUp = false;
                    eatCounter = 0;
                    saHead.setAnimation(0);
                    isEating = false;
                }
            }
        }
    }

    private void updateRect() {
        headBoundsRect.x = item.getX() + item.getSpriterActorById("flower_head2").getX();
        headBoundsRect.y = item.getY() + item.getSpriterActorById("flower_head2").getY();
        headBoundsRect.width = item.getSpriterActorById("flower_head2").getWidth();
        headBoundsRect.height = item.getSpriterActorById("flower_head2").getHeight();
    }

    @Override
    public void dispose() {
        item.dispose();
        stage.dispose();
    }

    public void eat() {
        isEating = true;
        saHead.setAnimation(1);
    }
}
