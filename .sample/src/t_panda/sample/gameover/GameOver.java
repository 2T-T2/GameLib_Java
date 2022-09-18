package t_panda.sample.gameover;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import t_panda.game.IKeyInput;
import t_panda.game.MouseInput;
import t_panda.game.Scene;
import t_panda.sample.SaveData;
import t_panda.sample.Defined.Fonts;
import t_panda.sample.Defined.Key;
import t_panda.sample.Defined.Scenes;
import t_panda.sample.Defined.Tag;

public class GameOver extends Scene<Scenes, Tag, Key> {
    private int score;

    public GameOver(Scenes scene) {
        super(scene);
    }

    @Override
    public void init(InitArg<Scenes, Tag, Key> initArg) {
        super.init(initArg);
        score = (int)initArg.getReceiveData();
        SaveData.INSTANCE.setHiScore(score);
        try {
            SaveData.INSTANCE.saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void drawToSceneImage(Graphics g) {
        g.setColor(Color.black);
        g.setFont(Fonts.S_FONT);
        g.fillRect(0, 0, getDrawWidth(), getDrawHeight());
        g.setColor(Color.white);
        g.drawString("SCORE: "+score, 120, getDrawHeight()>>1);
    }

    @Override
    public void update(IKeyInput<Key> keyInput, MouseInput mouseInput) {
        super.update(keyInput, mouseInput);
        if(keyInput.getDownKeysCode().length != 0)
            changeScene(Scenes.TITLE, this.score);
    }
}
