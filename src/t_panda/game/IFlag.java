package t_panda.game;

import java.io.Serializable;

/**
 * フラグ管理を行うための機能を実装するインターフェイス
 */
public interface IFlag<E extends Enum<E>> extends Serializable {
    /**
     * どのフラグのたっていないときの値です。
     */
    public static final int NONE = 0;

    /**
     * どのフラグもたっていないかどうかを判定します
     * @return どのフラグもたっていないかどうか
     */
    boolean isNone();
    /**
     * 指定されたフラグがたっているかを判定します。
     * @param flg たっているかを判定するフラグ
     * @return 指定されたフラグがたっているか
     */
    boolean hasFlag(E flg);
    /**
     * 指定されたフラグをおろします
     * @param flg おろしたいフラグ
     * @return 指定されたフラグをおろした状態の自身
     */
    IFlag<E> put(E flg);
    /**
     * 指定されたフラグを立てます。
     * @param flg 立てたいフラグ
     * @return 指定されたフラグを立てた状態の自身
     */
    IFlag<E> set(E flg);
}
