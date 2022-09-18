package t_panda.sample.title;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URISyntaxException;

import t_panda.game.IGObject;
import t_panda.game.IKeyInput;
import t_panda.game.MouseInput;
import t_panda.sample.GUI_Controller;
import t_panda.sample.SaveData;
import t_panda.sample.Defined.Fonts;
import t_panda.sample.Defined.Key;
import t_panda.sample.Defined.Scenes;
import t_panda.sample.Defined.Tag;
import t_panda.sample.util.SelectionHolder;
import t_panda.sample.util.Text;

public class Menu extends SelectionHolder {
    public Menu(int centerX, int centerY, int width, int height) {
        super(centerX, centerY, width, height);
    }

    @Override
    public void init(InitArg<Scenes, Tag, Key> initArg) {
        super.init(initArg);
        var start = new Text("Game Start", (getDrawWidth()>>1), (Fonts.M_FONT.getSize()>>1) + 15, getDrawWidth(), Fonts.M_FONT.getSize());
        start.setFont(Fonts.M_FONT);
        start.setColor(Color.white);
        var setting = new Text("Continue", (getDrawWidth()>>1), (Fonts.M_FONT.getSize()>>1) + 25 + Fonts.M_FONT.getSize(), getDrawWidth(), Fonts.M_FONT.getSize());
        setting.setFont(Fonts.M_FONT);
        setting.setColor(Color.white);
        addObj(start);
        addObj(setting);
    }

    @Override
    protected void drawToObjImage(Graphics g) {
        super.drawToObjImage(g);
        if(getChildrenCount() != 0) {
            int idx = getSelectingIdx();
            var selectObj = getChildByIndex(idx);
            int cursorX = selectObj.getDrawLeft() + Fonts.S_FONT.getSize();
            int cursorY = selectObj.getDrawCenterY() + Fonts.S_FONT.getSize();
            g.setFont(Fonts.S_FONT);
            g.setColor(Color.white);
            g.drawString("●", cursorX, cursorY);
        }
    }
    @Override
    public void update(IKeyInput<Key> keyInput, MouseInput mouseInput) {
        super.update(keyInput, mouseInput);
        if(!getSelecing()) return;

        if(keyInput.getKeyDown(Key.UP)) {
            setSelectingIdx(getSelectingIdx()-1);

        } else if(keyInput.getKeyDown(Key.DOWN)) {
            setSelectingIdx(getSelectingIdx()+1);
        }
    }

    @Override
    protected void pressOkKey(IGObject<Scenes, Tag, Key> igObject) {
        super.pressOkKey(igObject);
        if(((Text)igObject).getText().equals("Game Start"))
            changeScene(Scenes.GAME, null);
        else if(((Text)igObject).getText().equals("Continue")) {
            if(!SaveData.INSTANCE.exists中断セーブ()) {
                GUI_Controller.INSTANCE.msgbox_error("中断セーブデータは存在しません。");
                pressCancelKey(igObject);
                return;
            }
            new Thread(new Runnable() {
                public void run() {
                    try {
                        GUI_Controller.INSTANCE.setGame(SaveData.INSTANCE.load_中断セーブデータ());
                    } catch (ClassNotFoundException | IOException | URISyntaxException | InterruptedException e) {
                        GUI_Controller.INSTANCE.msgbox_error("中断セーブデータは存在しません。");
                        pressCancelKey(igObject);
                    }
                }
            }).start();;
        }
    }
}
