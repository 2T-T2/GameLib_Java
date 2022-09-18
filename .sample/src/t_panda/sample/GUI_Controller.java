package t_panda.sample;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

import t_panda.game.Game;
import t_panda.game.IGame;
import t_panda.sample.Defined.Key;
import t_panda.sample.Defined.Scenes;
import t_panda.sample.Defined.Tag;
import t_panda.sample.game.GameScene;
import t_panda.sample.gameover.GameOver;
import t_panda.sample.title.TitleScene;

public enum GUI_Controller {
    INSTANCE;

    private Application app;
    private int cancelFullScreenWidth;
    private int cancelFullScreenHeight;
        private GUI_Controller() {
        IGame<Scenes, Tag, Key> game = new Game.Builder<Scenes, Tag, Key>(400, 300, Scenes.TITLE)
            .addScene(new TitleScene(Scenes.TITLE))
            .addScene(new GameScene(Scenes.GAME))
            .addScene(new GameOver(Scenes.GAMEOVER))
            .initData(SaveData.INSTANCE.getHiScore())
            .fps(30)
            .build();
        this.app = new Application(game);
    }

    public void run() {
        this.app.play();
    }

    void toFullScreen() {
        cancelFullScreenWidth = this.app.getWidth();
        cancelFullScreenHeight = this.app.getHeight();
        this.app.dispose();
        this.app.setUndecorated(true);
        GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .setFullScreenWindow(this.app);
        this.app.setVisible(true);
    }
    void cancelFullScreen() {
        this.app.dispose();
        this.app.setUndecorated(false);
        GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .setFullScreenWindow(this.app);
            this.app.setSize(cancelFullScreenWidth, cancelFullScreenHeight);
        this.app.setVisible(true);
    }

    public void setGame(Game<Scenes, Tag, Key> game) throws ClassNotFoundException, IOException, URISyntaxException, InterruptedException {
        this.app.getGame().requestGameThreadStop();
        var bounds = this.app.getBounds();
        this.app.getGameThread().join();
        this.app.dispose();
        this.app = null;
        this.app = new Application(game);
        this.app.setBounds(bounds);
        this.app.play();
        game.requestSetGameThreadPouse(false);
    }
    public IGame<Scenes, Tag, Key> getGame() {
        return this.app.getGame();
    }

    public void msgbox_error(String mes) {
        JOptionPane.showMessageDialog(app, mes, "エラーメッセージ", JOptionPane.ERROR_MESSAGE);
    }
}
