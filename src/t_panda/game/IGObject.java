package t_panda.game;

import java.awt.Graphics;

/**
 * ゲーム内オブジェクトの動作を実装させるインターフェース
 */
public interface IGObject<SCENE extends Enum<SCENE>, TAG extends Enum<TAG>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable> extends IDrawable, IUpdatable<VKEYPAD>, IGObjectHost<SCENE, TAG, VKEYPAD>, ISceneChanger<SCENE>, IHasRectHit {
    /**
     * 初期化関数。IGObjectHostに所属したときに呼び出されます。
     * @param arg 初期化に使用する引数。
     */
    void init(InitArg<SCENE, TAG, VKEYPAD> arg);
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
     * 所属するISceneに指定されたIGObjectを追加します
     * @param obj 所属するISceneに追加するIGObject
     */
    void addObjToScene(IGObject<SCENE, TAG, VKEYPAD> obj);
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
     * @param host 初期化するIGObjectが属するホスト
     * @param scene 初期化するIGObjectが属するシーン
     * @return IGObjectを初期化する引数オブジェクト
     */
    static <SCENE extends Enum<SCENE>, TAG extends Enum<TAG>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable>InitArg<SCENE, TAG, VKEYPAD> createInitArg(IGObjectHost<SCENE, TAG, VKEYPAD> host, IScene<SCENE, TAG, VKEYPAD> scene) {
        return new InitArg<SCENE, TAG, VKEYPAD>(host, scene);
    }
    /**
     * IGObjectの初期化に使用する引数オブジェクト
     * @see IGObject#init(InitArg)
     */
    public class InitArg<SCENE extends Enum<SCENE>, TAG_OBJ extends Enum<TAG_OBJ>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable> {
        private final IGObjectHost<SCENE, TAG_OBJ, VKEYPAD> host;
        private final IScene<SCENE, TAG_OBJ, VKEYPAD> scene;

        private InitArg(IGObjectHost<SCENE, TAG_OBJ, VKEYPAD> host, IScene<SCENE, TAG_OBJ, VKEYPAD> scene) {
            this.host = host;
            this.scene = scene;
        }

        /**
         * 初期化対象のIGObjectのホストを取得します
         * @return 初期化対象のIGObjectのホスト
         */
        public IGObjectHost<SCENE, TAG_OBJ, VKEYPAD> getHost() {
            return host;
        }
        /**
         * 初期化対象のIGObjectが属するシーンを取得します
         * @return 初期化対象のIGObjectが属するシーン
         */
        public IScene<SCENE, TAG_OBJ, VKEYPAD> getScene() {
            return scene;
        }
    }
}
