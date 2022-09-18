package t_panda.game;

/**
 * Enumを使用してフラグ管理を行うクラス。Enumのordinal()を使用してビットフラグでフラグ管理を行います。
 * @see java.lang.Enum#ordinal()
 */
public class Flag32<E extends Enum<E>> implements IFlag<E> {
    /**  */ private int value;

    /**
     * コンストラクタ
     */
    public Flag32() { value = IFlag.NONE; }
    private Flag32(int value) { this.value = value; }

    /**
     * 内部で使用しているビットフラグの値を取得します。
     * @return ビットフラグの値
     */
    public int getValue() {
        return value;
    }

    /**
     * ビットフラグの値から、オブジェクトを生成します。
     * @param <E> ビットフラグに対応したEnum継承クラス
     * @param value ビットフラグの値
     * @return ビットフラグの値から生成したオブジェクト。
     */
    public static <E extends Enum<E>> Flag32<E> ValueOf(int value) { return new Flag32<E>(value); }

        /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNone() {
        return value == IFlag.NONE;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasFlag(E flg) {
        return (value & (1 << flg.ordinal())) != 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IFlag<E> put(E flg) {
        value &= ~(1 << flg.ordinal()); return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IFlag<E> set(E flg) {
        value |= 1 << flg.ordinal(); return this;
    }
}
