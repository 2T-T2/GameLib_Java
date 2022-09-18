package t_panda.sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.nio.file.Path;

import t_panda.game.Game;
import t_panda.sample.Defined.Key;
import t_panda.sample.Defined.Scenes;
import t_panda.sample.Defined.Tag;

public enum SaveData {
    INSTANCE;

    private FieldData fieldData = new FieldData();
    private final Path path_最高記録データ = Application.GetAppJarDir().resolve("SaveData.cls");
    private final Path path_中断セーブデータ = Application.GetAppJarDir().resolve("中断セーブ.dat");

    public void setHiScore(int hiScore) {
        this.fieldData.setHiScore(hiScore);
    }
    public int getHiScore() {
        return this.fieldData.getHiScore();
    }
    public void loadFile() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objInStrm = new ObjectInputStream(new FileInputStream(path_最高記録データ.toFile()));) {
            this.fieldData = (SaveData.FieldData)objInStrm.readObject();
        }
    }
    public void saveFile() throws FileNotFoundException, IOException {
        try(ObjectOutputStream objOutStrm = new ObjectOutputStream(new FileOutputStream(path_最高記録データ.toFile()))) {
            objOutStrm.writeObject(this.fieldData);
            objOutStrm.flush();
        }
    }
    public void save_中断セーブデータ() throws FileNotFoundException, IOException, URISyntaxException, InterruptedException {
        GUI_Controller.INSTANCE.getGame().requestSetGameThreadPouse(true);
        SaveData.SAVE_FILE(this.path_中断セーブデータ, GUI_Controller.INSTANCE.getGame());
        GUI_Controller.INSTANCE.getGame().requestSetGameThreadPouse(false);
    }
    public Game<Scenes, Tag, Key> load_中断セーブデータ() throws ClassNotFoundException, IOException {
        return SaveData.LOAD_FILE(Application.GetAppJarDir().resolve("中断セーブ.dat"));
    }

    private static void SAVE_FILE(Path path, Serializable serializable) throws FileNotFoundException, IOException {
        try(ObjectOutputStream objOutStrm = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            objOutStrm.writeObject(serializable);
            objOutStrm.flush();
        }
    }
    @SuppressWarnings("unchecked")
    private static <T> T LOAD_FILE(Path path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objInStrm = new ObjectInputStream(new FileInputStream(path.toFile()));) {
            return (T)objInStrm.readObject();
        }
    }
    private class FieldData implements Serializable {
        private int hiScore = 0;
        public void setHiScore(int hiScore) {
            if(this.hiScore < hiScore)
                this.hiScore = hiScore;
        }
        public int getHiScore() {
            return hiScore;
        }
    }
    public boolean exists中断セーブ() {
        return this.path_中断セーブデータ.toFile().exists();
    }
}
