package game.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import game.actors.controllers.BugController;
import game.actors.controllers.QueenBeeBugController;

/**
 * Created by MainUser on 04/07/2015.
 */
public class Bug {
    private BugController controller;
    private CompositeItem compositeItem;

    public Bug(BugController controller, CompositeItem compositeItem) {
        this.controller = controller;
        this.compositeItem = compositeItem;
        compositeItem.addScript((IScript) controller);
    }

    public void setPosition(Vector2 position) {
        this.compositeItem.setPosition(position.x,position.y);
        this.controller.startYPosition = position.y;
    }

    public long getPoints(){
        return this.controller.getPoints();
    }

    public void setController(BugController controller) {
        this.controller = controller;
    }

    public BugController getController() {
        return controller;
    }

    public CompositeItem getCompositeItem() {
        return compositeItem;
    }

    public void setCompositeItem(CompositeItem compositeItem) {
        this.compositeItem = compositeItem;
    }

    public boolean isQueen() {
        return controller instanceof QueenBeeBugController;
    }

    public boolean overlapsBoundingRectangle(Rectangle posXrect) {
        return controller.getBoundsRectangle().overlaps(posXrect);
    }
}
