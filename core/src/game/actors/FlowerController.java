package game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.script.IScript;
import game.stages.GameStage;

import java.util.Iterator;

import static game.utils.GlobalConstants.POINT_TRAVEL;

/**
 * Created by MainUser on 07/06/2015.
 */
public class FlowerController implements IScript {

    public long pointsAmount = 0L;

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
//                        Actions.moveTo(1802, POINT_TRAVEL, 1f)));
    }

    public void addMovementActionDown() {
        item.addAction(
                Actions.sequence(
                        Actions.moveBy(0, -20)));
//                        Actions.moveTo(1802, -585, 1f)));
    }

    @Override
    public void act(float delta) {
        if(!((GameStage)stage).isGameOver()) {
            updateRect();
            checkForCollisions();

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
//            if (headBoundsRect.getY() < POINT_TRAVEL-20) {
                if (headBoundsRect.getY() > 1200) {
                    isMovingUp = false;
//                System.out.println("POINT_TRAVEL: " + headBoundsRect.getY());
                }
            }
        }
    }

    private void checkForCollisions() {

        Iterator<BugController> itr = ((GameStage) stage).getBugs().iterator();
        while (itr.hasNext()) {
            BugController bug = itr.next();
            Rectangle posXrect = headBoundsRect;
            Rectangle posXbug = bug.getBoundsRectangle();

//            System.out.println("posXrect: " + posXrect.getX());
//            System.out.println("posXbug: " + posXbug.getX());

            if (posXrect.overlaps(posXbug)) {
                itr.remove();
                removeActor(bug);
                pointsAmount += bug.points;
                System.out.println("I have " + pointsAmount + " points!");
            }
        }
    }

    private void removeActor(BugController bug) {
        for (Actor actor : stage.getActors()) {
            if (actor.equals(bug.getCompositeItem())) {
                actor.remove();
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
