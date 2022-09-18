package t_panda.game;

import java.util.ArrayList;

/**
 * キー入力情報を保持しているクラス。IKeyCodeGetableとEnumを継承したクラスを用いてキーの識別を行います。
 * いくつかのキーは入力されても無効な場合があります。
 */
public class KeyInput<E extends Enum<E> & IKeyCodeGetable> implements IKeyInput<E> {
    /**  */ private final boolean[] keyTable;
    /**  */ private final boolean[] preKeyTable;

    /**  */ private final boolean[] inputKey;
    /**  */ private final boolean[] releaseKey;

    KeyInput() {
        int compatibleKeyCode = java.awt.event.KeyEvent.VK_CONTEXT_MENU + 1;
        keyTable = new boolean[compatibleKeyCode];
        preKeyTable = new boolean[compatibleKeyCode];
        inputKey = new boolean[compatibleKeyCode];
        releaseKey = new boolean[compatibleKeyCode];
    }
    @Override
    public boolean getPreKey(E key) {
        return getPreKey(key.getKeyCode());
    }
    @Override
    public boolean getKey(E key) {
        return getKey(key.getKeyCode());
    }
    @Override
    public boolean getKeyDown(E key) {
        return getKeyDown(key.getKeyCode());
    }
    @Override
    public boolean getKeyUp(E key) {
        return getKeyUp(key.getKeyCode());
    }
    @Override
    public int[] getDownKeysCode() {
        ArrayList<Integer> keysCode = new ArrayList<>();
        for (int i = 0; i < inputKey.length; i++)
            if(getKeyDown(i))
                keysCode.add(i);
        return keysCode.stream().mapToInt(Integer::intValue).toArray();
    }
    @Override
    public int[] getKeysCode() {
        ArrayList<Integer> keysCode = new ArrayList<>();
        for (int i = 0; i < inputKey.length; i++)
            if(getKey(i))
                keysCode.add(i);
        return keysCode.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * キー入力を更新します。毎フレーム呼び出されることを想定しています。
     */
    public void update() {
        System.arraycopy(keyTable, 0, preKeyTable, 0, keyTable.length);
        for (int i = 0; i < inputKey.length; i++) {
            preKeyTable[i] = keyTable[i];
            keyTable[i] |= inputKey[i];
            keyTable[i] &= !releaseKey[i];
            releaseKey[i] = false;
            inputKey[i] = false;
        }
    }
    /**
     * 指定されたキーにこのクラスが対応しているかを判定します
     * @param key 対応しているかを判定するキー
     * @return 対応しているか
     */
    public boolean isCompatibleKey(E key) {
        return isCompatibleKey(key.getKeyCode());
    }
    /**
     * 指定されたキーにこのクラスが対応しているかを判定します
     * @param keycode 対応しているかを判定するキーコード
     * @return 対応しているか
     */
    public boolean isCompatibleKey(int keycode) {
        return keycode < keyTable.length;
    }
    /**
     * キーが入力された時に呼び出します。
     * @param keycode 入力されたキーコード
     * @see java.awt.event.KeyEvent
     */
    public void pressKey(int keycode) {
        if(!isCompatibleKey(keycode)) return;
        inputKey[keycode] = true;
    }
    /**
     * キーが離された時に呼び出します。
     * @param keycode 離されたキーコード
     * @see java.awt.event.KeyEvent
     */
    public void releaseKey(int keycode) {
        if(!isCompatibleKey(keycode)) return;
        releaseKey[keycode] = true;
    }

    private boolean getPreKey(int keycode) {
        return isCompatibleKey(keycode) && preKeyTable[keycode];
    }
    private boolean getKey(int keycode) {
        return isCompatibleKey(keycode) && keyTable[keycode];
    }
    private boolean getKeyDown(int keycode) {
        return !getPreKey(keycode) && getKey(keycode);
    }
    private boolean getKeyUp(int keycode) {
        return getPreKey(keycode) && !getKey(keycode);
    }
}
