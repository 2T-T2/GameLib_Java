package t_panda.sample.title;

import java.awt.Color;
import java.awt.Graphics;

import t_panda.game.IGame;
import t_panda.game.IKeyInput;
import t_panda.game.MouseInput;
import t_panda.game.Scene;
import t_panda.game.animation.easing.EaseBounce;
import t_panda.sample.Defined.Fonts;
import t_panda.sample.Defined.Key;
import t_panda.sample.Defined.Scenes;
import t_panda.sample.Defined.Tag;
import t_panda.sample.util.Label;

public class TitleScene extends Scene<Scenes, Tag, Key> {
    private int max_score;

    private Label title_label;
    private final int title_label_anm_frm_num = 30;
    private final int title_label_first_y = 0;
    private int title_label_movement_y;
    private boolean title_label_anm_finished;

    public TitleScene(Scenes scene) {
        super(scene);
    }
    @Override
    public void init(InitArg<Scenes, Tag, Key> initArg) {
        super.init(initArg);
        title_label_movement_y = getDrawHeight()>>2;
        title_label_anm_finished = false;
        title_label = new Label("ブロック崩し", getDrawWidth()>>1, title_label_first_y, getGameWidth(), 100);
        title_label.setBgColor(IGame.TRANSEPARENT);
        title_label.setTextColor(Color.white);
        title_label.setFont(Fonts.L_FONT);
        addObj(title_label);
        if(initArg.getReceiveData() != null)
            max_score = Math.max(max_score, (int)initArg.getReceiveData());
        else
            max_score = 0;
    }

    @Override
    protected void drawToSceneImage(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getDrawWidth(), getDrawHeight());
        g.setColor(Color.white);
        g.setFont(Fonts.S_FONT);
        g.drawString("HI-SCORE: " + max_score, 0, Fonts.S_FONT.getSize());
    }

    @Override
    public void update(IKeyInput<Key> keyInput, MouseInput mouseInput) {
        super.update(keyInput, mouseInput);
        if(!title_label_anm_finished) {
            double d = EaseBounce.OUT.calc((double)title_label.getFrameCount()/title_label_anm_frm_num);
            title_label.setHitCenterY ( title_label_first_y + (int)(title_label_movement_y*d));
            title_label.setDrawCenterY( title_label_first_y + (int)(title_label_movement_y*d));
            if(keyInput.getDownKeysCode().length != 0 || title_label.getFrameCount() > title_label_anm_frm_num) {
                title_label_anm_finished = true;
                title_label.setHitCenterY (title_label_first_y + title_label_movement_y);
                title_label.setDrawCenterY(title_label_first_y + title_label_movement_y);            
                addObj(new Menu(getDrawWidth()>>1, (getDrawHeight()>>2)*3, 200, 100));
            }
        }
    }
}
