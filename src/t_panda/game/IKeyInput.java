package t_panda.game;

import java.io.Serializable;

/**
 * キー入力情報を取得するためのインターフェイス
 */
public interface IKeyInput<E extends Enum<E> & IKeyCodeGetable> extends Serializable {
    /**
     * 指定されたキーがひとつ前のフレームで入力されていたかを判定します。
     * @param key ひとつ前のフレームで入力されていたかを判定するキー
     * @return 指定されたキーがひとつ前のフレームで入力されていたか
     */
    public boolean getPreKey(E key);
    /**
     * 指定されたキーが入力されているかを判定します。
     * @param key 入力されているかを判定するキー
     * @return 指定されたキーが入力されているか
     */
    boolean getKey(E key);
    /**
     * 指定されたキーがこのフレーム入力されたかを判定します。
     * @param key このフレーム入力されたかを判定するキー
     * @return 指定されたキーがこのフレーム入力されたか
     */
    boolean getKeyDown(E key);
    /**
     * 指定されたキーがこのフレームで入力が辞めたかを判定します。
     * @param key このフレームで入力が辞めたかを判定するキー
     * @return 指定されたキーがこのフレームで入力が辞めたか
     */
    boolean getKeyUp(E key);
    /**
     * このフレームで入力されたキーのキーコード配列を返します。
     * @return このフレームで入力されたキーのキーコード配列。
     * @see java.awt.event.KeyEvent
     */
    int[] getDownKeysCode();
    /**
     * 現在入力されているキーのキーコード配列を返します。
     * @return  現在入力されているキーのキーコード配列。
     * @see java.awt.event.KeyEvent
     */
    int[] getKeysCode();
}
