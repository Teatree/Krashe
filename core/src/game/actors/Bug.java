package game.actors;

import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import game.actors.controllers.BugController;

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

    public BugController getController() {
        return controller;
    }

    public void setPosition(Vector2 position) {
        this.compositeItem.setPosition(position.x,position.y);
        this.controller.startYPosition = position.y;
    }

    public int getPoints(){
        return controller.points;
    }

    public void setController(BugController controller) {
        this.controller = controller;
    }

    public CompositeItem getCompositeItem() {
        return compositeItem;
    }

    public void setCompositeItem(CompositeItem compositeItem) {
        this.compositeItem = compositeItem;
    }
}
