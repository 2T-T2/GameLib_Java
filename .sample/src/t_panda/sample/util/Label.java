package t_panda.sample.util;

import java.awt.Color;

import t_panda.sample.Defined.Key;
import t_panda.sample.Defined.Scenes;
import t_panda.sample.Defined.Tag;

public class Label extends FilledRect {
    final String initText;
    Text text;

    public Label(String text, int centerX, int centerY, int width, int height) {
        super(centerX, centerY, width, height);
        initText = text;
        this.text = new Text(initText, getHitWidth()>>1, getHitHeight()>>1, getHitWidth(), getHitHeight());
    }
    @Override
    public void init(InitArg<Scenes, Tag, Key> initArg) {
        super.init(initArg);
        if(text == null) 
            this.text = new Text(initText, getHitWidth()>>1, getHitHeight()>>1, getHitWidth(), getHitHeight());
        addObj(this.text);
    }
    @Override
    public void removed() {
        super.removed();
        this.text = null;
    }

    public void setTextColor(Color textCol) {
        text.setColor(textCol);
    }
    public void setBgColor(Color bgCol) {
        setColor(bgCol);
    }
    public void setFont(java.awt.Font font) {
        text.setFont(font);
    }
    public void setVAlign(Text.VAlign vAlign) { text.setVAlign(vAlign); }
    public void setHAlign(Text.HAlign hAlign) { text.setHAlign(hAlign); }
    public String getText() { return text.getText(); }
}
