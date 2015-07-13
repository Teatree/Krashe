package game.actors.controllers;

import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

/**
 * Created by MainUser on 12/07/2015.
 */
public class UmbrellaController implements IScript{
    private CompositeItem item;
    public float velocityX;
    float velocityY;
    float gravity;

    @Override
    public void init(CompositeItem item) {
        this.item = item;

        velocityY = 45f; // has to be random
        gravity = 50f;
    }

    @Override
    public void dispose() {
        item.dispose();
    }

    @Override
    public void act(float delta) {
        velocityX += gravity * delta;
        item.setX(item.getX() + velocityX*delta);
        item.setY(item.getY() + velocityY*delta);
    }

    public void pushUmbrellaFor(float pixels){
        velocityX = pixels;
    }
}
