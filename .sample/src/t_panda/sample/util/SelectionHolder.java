package t_panda.sample.util;

import java.util.ArrayList;

import t_panda.game.IFlag;
import t_panda.game.IGObject;
import t_panda.game.IKeyInput;
import t_panda.game.MouseInput;
import t_panda.sample.Defined.Key;
import t_panda.sample.Defined.Scenes;
import t_panda.sample.Defined.Tag;
import t_panda.sample.event.SelectionListener;

public class SelectionHolder extends Holder {
    private int selectingIdx = 0;
    private boolean selecting = true;
    private Key ok = Key.A;
    private Key cancel = Key.B;
    private ArrayList<SelectionListener> selectionListeners;

    public SelectionHolder(int centerX, int centerY, int width, int height) { super(centerX, centerY, width, height); }
    public SelectionHolder(int centerX, int centerY, int width, int height, IFlag<Tag> tag) { super(centerX, centerY, width, height, tag); }

    @Override
    public void init(InitArg<Scenes, Tag, Key> initArg) {
        super.init(initArg);
        selectingIdx = 0;
        selecting = true;
        selectionListeners = new ArrayList<>();
    }

    @Override
    public void updateChildObj(ArrayList<IGObject<Scenes, Tag, Key>> children, IKeyInput<Key> keyInput, MouseInput mouseInput) {
        if(getChildrenCount() != 0 && !getSelecing())
            getChildByIndex(selectingIdx).update(keyInput, mouseInput);
    }

    @Override
    public void update(IKeyInput<Key> keyInput, MouseInput mouseInput) {
        super.update(keyInput, mouseInput);
        if(getChildrenCount() != 0) {
            if(getSelecing()) {
                if(keyInput.getKeyDown(ok))
                    pressOkKey(getChildByIndex(getSelectingIdx()));
            }else if(keyInput.getKeyDown(cancel)) {
                pressCancelKey(getChildByIndex(getSelectingIdx()));
            }
        }
        addReserveObj();
    }
    @Override
    public void removed() {
        super.removed();
        selectionListeners.clear();
        selectionListeners = null;
    }

    protected void setSelectingIdx(int idx) {
        this.selectingIdx = (idx+getChildrenCount())%getChildrenCount();
    }
    protected int getSelectingIdx() {
        return selectingIdx;
    }
    protected void setSelecting(boolean b) {
        this.selecting = b;
    }
    protected boolean getSelecing() {
        return selecting;
    }

    protected void pressCancelKey(IGObject<Scenes, Tag, Key> igObject) {
        setSelecting(true);
        for (var listener : selectionListeners)
            listener.pressCancelKey(igObject);
    }
    protected void pressOkKey(IGObject<Scenes, Tag, Key> igObject) {
        setSelecting(false);
        for (var listener : selectionListeners)
            listener.pressOkKey(igObject);
    }
}
