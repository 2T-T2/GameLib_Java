package t_panda.sample;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import t_panda.game.IGame;
import t_panda.game.gui.swing.GameFrame;
import t_panda.sample.Defined.Key;
import t_panda.sample.Defined.Scenes;
import t_panda.sample.Defined.Tag;

public class Application extends GameFrame<Scenes, Tag, Key> {
    public static final Random r = new Random();
    private Thread gameThread;

    public Application(IGame<Scenes, Tag, Key> game) {
        super(game);
    }
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        try {
            SaveData.INSTANCE.loadFile();
        }catch(FileNotFoundException e) {
            SaveData.INSTANCE.saveFile();
        }
        GUI_Controller.INSTANCE.run();
    }

    public void play() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }
    public Thread getGameThread() {
        return this.gameThread;
    }

    public static Path GetAppJarDir() {
        try {
            return Paths.get(Application.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_F12)
            GUI_Controller.INSTANCE.toFullScreen();
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            GUI_Controller.INSTANCE.cancelFullScreen();
        // else if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
        //     try {
        //         GUI_Controller.INSTANCE.setGame(SaveData.INSTANCE.load_中断セーブデータ());
        //     } catch (ClassNotFoundException | IOException e1) {
        //         e1.printStackTrace();
        //     }
        // }
    }
}
