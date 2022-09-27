package t_panda.game;

import java.awt.Graphics;

/**
 * ゲーム内オブジェクトの動作を実装させるインターフェース
 */
public interface IGObject<SCENE extends Enum<SCENE>, TAG extends Enum<TAG>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable, MSG, CHILD extends IGObject<SCENE, TAG, VKEYPAD, MSG, ?>> extends IDrawable, IUpdatable<VKEYPAD>, IGObjectHost<SCENE, TAG, VKEYPAD, MSG, CHILD>, ISceneChanger<SCENE>, IHasRectHit {
    /**
     * 初期化関数。IGObjectHostに所属したときに呼び出されます。
     * @param initArg 初期化に使用する引数。
     */
    void init(InitArg<SCENE, TAG, VKEYPAD, MSG> initArg);
    /**
     * IGObjectHostに属しているかを判定します。
     * @return IGObjectHostに属しているか
     */
    boolean isDestroyed();
    /**
     * IGObjectHostから削除されたときに呼び出されます。
     */
    void removed();
    /**
     * 所属するIGObjectHostから削除されるようにします。
     */
    void destroy();
    /**
     * 自身がもつタグ情報を取得します。
     * @return 自身がもつタグ情報
     */
    IFlag<TAG> getTags();

    /**
     * 指定されたタグがこのオブジェクトに当てはまるかを判定します、
     * @param tag このオブジェクトに当てはまるかを判定するタグ
     * @return 指定されたタグがこのオブジェクトに当てはまるか
     */
    default boolean hasTag(TAG tag) {
        return getTags().hasFlag(tag);
    }

    /**
     * 指定されたGraphicsを使用して、自身の描画矩形サイズ分をclearRectします。
     * @param g 自身の描画矩形サイズ分をclearRectするGraphics
     * @see java.awt.Graphics#clearRect(int, int, int, int)
     */
    default void clear(Graphics g) {
        g.clearRect(0, 0, getDrawWidth(), getDrawWidth());
    }

    /**
     * 自身を指定されたX,Y座標分移動させます。{@link IDrawable#setDrawCenterX(int)},{@link IDrawable#setDrawCenterY(int)},{@link IHasRectHit#getHitCenterX()},{@link IHasRectHit#getHitCenterY()}を呼び出します。
     * @param dx 移動させるX座標
     * @param dy 移動させるY座標
     */
    default void move(int dx, int dy) {
        setHitCenterX(getHitCenterX()+dx);
        setHitCenterY(getHitCenterY()+dy);
        setDrawCenterX(getDrawCenterX()+dx);
        setDrawCenterY(getDrawCenterY()+dy);
    }
    /**
     * IGObjectを初期化する引数オブジェクトを生成します。
     * @param <SCENE> シーン識別Enum
     * @param <TAG> オブジェクト識別タグEnum
     * @param <VKEYPAD> キー識別タグEnum
     * @param <MSG> メッセージクラス
     * @param host 初期化するIGObjectが属するホスト
     * @param scene 初期化するIGObjectが属するシーン
     * @return IGObjectを初期化する引数オブジェクト
     */
    static <SCENE extends Enum<SCENE>, TAG extends Enum<TAG>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable, MSG>InitArg<SCENE, TAG, VKEYPAD, MSG> createInitArg(IGObjectHost<SCENE, TAG, VKEYPAD, MSG, IGObject<SCENE, TAG, VKEYPAD, MSG, ?>> host, IScene<SCENE, TAG, VKEYPAD, MSG, ?> scene) {
        return new InitArg<>(host, scene);
    }
    /**
     * IGObjectの初期化に使用する引数オブジェクト
     * @see IGObject#init(InitArg)
     */
    public class InitArg<SCENE extends Enum<SCENE>, TAG extends Enum<TAG>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable, MSG> {
        private final IGObjectHost<SCENE, TAG, VKEYPAD, MSG, IGObject<SCENE, TAG, VKEYPAD, MSG, ?>> host;
        private final IScene<SCENE, TAG, VKEYPAD, MSG, ?> scene;

        private InitArg(IGObjectHost<SCENE, TAG, VKEYPAD, MSG, IGObject<SCENE, TAG, VKEYPAD, MSG, ?>> host, IScene<SCENE, TAG, VKEYPAD, MSG, ?> scene) {
            this.host = host;
            this.scene = scene;
        }

        /**
         * 初期化対象のIGObjectのホストを取得します
         * @return 初期化対象のIGObjectのホスト
         */
        public IGObjectHost<SCENE, TAG, VKEYPAD, MSG, IGObject<SCENE, TAG, VKEYPAD, MSG, ?>> getHost() {
            return host;
        }
        /**
         * 初期化対象のIGObjectが属するシーンを取得します
         * @return 初期化対象のIGObjectが属するシーン
         */
        public IScene<SCENE, TAG, VKEYPAD, MSG, ?> getScene() {
            return scene;
        }
    }
}
