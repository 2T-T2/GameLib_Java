package t_panda.game;

/**
 * updateの実装を保証します。
 */
public interface IUpdatable<E extends Enum<E> & IKeyCodeGetable> {
    /**
     * 毎フレーム呼び出す関数です。
     * @param keyInput キー入力情報を保持したオブジェクト
     * @param mouseInput マウス入力情報を保持したオブジェクト
     */
    void update(IKeyInput<E> keyInput, MouseInput mouseInput);
    /**
     * 自身が初期化されてから経過したフレーム数を取得します
     * @return 自身が初期化されてから経過したフレーム数
     */
    long getFrameCount();
}
