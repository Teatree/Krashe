package game.utils;

import com.badlogic.gdx.math.Rectangle;
import game.actors.Bug;
import game.actors.controllers.FlowerController;
import game.actors.controllers.QueenBeeBugController;
import game.actors.controllers.UmbrellaController;
import game.stages.GameStage;

import java.util.Iterator;

/**
 * Created by MainUser on 18/07/2015.
 */
public class CollisionChecker {

    public static void checkCollisions(GameStage stage) {
        checkCollisionBugs(stage);
        checkCollisionUmbrella(stage);
    }

    private static void checkCollisionBugs(GameStage stage){
        FlowerController fc = stage.flowerController;
        Iterator<Bug> itr = ((GameStage) stage).getBugs().iterator();
        while (itr.hasNext()) {
            Bug bug = itr.next();
            Rectangle posXrect = fc.headBoundsRect;
            Rectangle posXbug = bug.getController().getBoundsRectangle();

            if (posXrect.overlaps(posXbug)) {
                itr.remove();
                stage.removeActor(bug.getCompositeItem());
                fc.pointsAmount += bug.getPoints();
                if (bug.getController() instanceof QueenBeeBugController) {
                    GameStage.angeredBeesTimer = GlobalConstants.ANGERED_BEES_MODE_DURATION;
                    GameStage.isAngeredBeesMode = true;
                    System.out.println("BEE MODE ACTIVATED");
                }
                System.out.println("I have " + fc.pointsAmount + " points!");

            }
        }
    }

    private static void checkCollisionUmbrella(GameStage stage){
        if(stage.umbrellaPowerUp != null && stage.umbrellaPowerUp.getUmbrellaController() != null) {
            FlowerController fc = stage.flowerController;
            UmbrellaController uc = stage.umbrellaPowerUp.getUmbrellaController();

            Rectangle posXrect = fc.headBoundsRect;
            Rectangle posXumbrella = uc.getBoundsRectangle();

            if (posXrect.overlaps(posXumbrella)) {
                fc.pointsAmount *= 2;
                stage.removeActor(uc.getCompositeItem());
                stage.umbrellaPowerUp = null;
                System.out.println("Doubling points!");
                System.out.println("I now have " + fc.pointsAmount + " points!");
            }

            if (isOutOfBounds(stage, posXumbrella)){
                uc.pushUmbrella(350, 400, 45, 55);
            }
        }

    }

    public static boolean isOutOfBounds(GameStage stage, Rectangle boundsRect){
        FlowerController flowerController = stage.flowerController;
        if (boundsRect.getX() >= flowerController.headBoundsRect.getX()+flowerController.headBoundsRect.getWidth()+100){
            return true;
        }
        return false;
    }
}
