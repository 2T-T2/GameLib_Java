package t_panda.sample.util;

import java.awt.Color;
import java.awt.Graphics;

import t_panda.game.Flag32;
import t_panda.game.GObject;
import t_panda.game.IFlag;
import t_panda.game.IKeyInput;
import t_panda.game.MouseInput;
import t_panda.sample.Defined.*;

public class Holder extends GObject<Scenes, Tag, Key> {
    private Color borderCol;
    private Color bgCol;

    public Holder(int centerX, int centerY, int width, int height, IFlag<Tag> tag) {
        super(centerX, centerY, centerX, centerY, width, height, width, height, tag.set(Tag.HOLDER));
        this.borderCol = Color.white;
        this.bgCol = Colors.light_black;
    }
    public Holder(int centerX, int centerY, int width, int height) { this(centerX, centerY, width, height, new Flag32<Tag>()); }
    @Override
    public void init(InitArg<Scenes, Tag, Key> initArg) {
        super.init(initArg);
    }

    @Override
    protected void drawToObjImage(Graphics g) {
        g.setColor(this.bgCol);
        g.fillRect(0, 0, getDrawWidth(), getDrawHeight());
        g.setColor(this.borderCol);
        g.drawRect(1, 0, getDrawWidth()-2, getDrawHeight()-1);
    }

    @Override
    public void update(IKeyInput<Key> keyInput, MouseInput mouseInput) {
        super.update(keyInput, mouseInput);
    }

    public void setBorderColor(Color borderCol) {
        this.borderCol = borderCol;
    }
    public void setBgColor(Color bgCol) {
        this.bgCol = bgCol;
    }
}
