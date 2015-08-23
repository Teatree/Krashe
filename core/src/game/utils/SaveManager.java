package game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import game.actors.Flower;

/**
 * Created by MainUser on 23/08/2015.
 */
public class SaveManager {

    public static final String FILE_NAME = "state.sav";

    public static void writeFile(String fileName, String s) {
        FileHandle file = Gdx.files.local(fileName);
        file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s), false);
    }

    public static String readFile(String fileName) {
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            String s = file.readString();
            if (!s.isEmpty()) {
                return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
            }
        }
        return "";
    }

    public static void saveProperties(){
        Json json = new Json();
        JsonProperties somProperties = new SaveManager.JsonProperties();
        somProperties.bugJuiceAmount = Flower.sessionPointsAmount;
        if(Flower.sessionPointsAmount > GlobalConstants.BEST_SCORE){
            somProperties.bestScore = Flower.sessionPointsAmount;
        }else{
            somProperties.bestScore = GlobalConstants.BEST_SCORE;
        }

        writeFile(FILE_NAME, json.toJson(somProperties));
    }

    public static void loadProperties(){
        String save = readFile(FILE_NAME);
        if (!save.isEmpty()) {
            Json json = new Json();
            JsonProperties somProperties = json.fromJson(JsonProperties.class, save);
            Flower.pointsAmount = somProperties.bugJuiceAmount;
            GlobalConstants.BEST_SCORE = somProperties.bestScore;
            System.err.println("Load " + Flower.pointsAmount + " of bug juice");
            System.err.println("BEST SCORE: " + GlobalConstants.BEST_SCORE);
        }
    }

    public static class JsonProperties{
        public long bugJuiceAmount;
        public long bestScore;
    }
}
