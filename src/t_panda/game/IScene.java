package t_panda.game;

import t_panda.game.event.ChangeSceneListener;

/**
 * シーンの機能を実装します。
 */
public interface IScene<
    SCENE extends Enum<SCENE>,
    OBJ_TAG extends Enum<OBJ_TAG>,
    KEYPAD extends Enum<KEYPAD> & IKeyCodeGetable,
    MSG,
    CHILD extends IGObject<SCENE, OBJ_TAG, KEYPAD, MSG, ?>>
    extends IGObjectHost<SCENE, OBJ_TAG, KEYPAD, MSG, CHILD>, ISceneChanger<SCENE>, IDrawable, IUpdatable<KEYPAD>, ChangeSceneListener<SCENE> {
    /**
     * シーンの初期化を行います。シーンに切り替わった時に呼び出されます。
     * @param initArg シーンの初期化に使用する引数。
     */
    void init(InitArg<SCENE, KEYPAD> initArg);
    /**
     * このシーンを識別するEnumを取得します。
     * @return このシーンを識別するEnum
     */
    SCENE getSceneName();
    /**
     * 自身に属するIGObjectからメッセージを受信したときに呼ばれます
     * @param source メッセージ送信元IGObject
     * @param msg 受信したメッセージ
     */
    void onMessageFromObj(IGObject<SCENE, OBJ_TAG, KEYPAD, MSG, ?> source, MSG msg);

    /**
     * シーン初期化引数の生成
     * @param <SCENE> シーン識別Enum
     * @param <VKEYPAD> シーン識別Enum
     * @param host シーンが属するIGame
     * @param data シーンを初期化するためのデータ
     * @return シーン初期化を行う引数を生成します
     * @see IScene#init(InitArg)
     * @see IScene.InitArg#getReceiveData()
     * @see java.lang.Enum#ordinal()
     */
    static <SCENE extends Enum<SCENE>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable> InitArg<SCENE, VKEYPAD> createInitArg(IGame<SCENE, VKEYPAD> host, Object data) {
        return new InitArg<SCENE, VKEYPAD>(host, data);
    }

    /**
     * シーンの初期化に使用する引数クラス。
     * @see IScene#init(InitArg)
     */
    public class InitArg<SCENE extends Enum<SCENE>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable> {
        private final IGame<SCENE, VKEYPAD> host;
        private final Object data;
    
        private InitArg(IGame<SCENE, VKEYPAD> host, Object data) {
            this.host = host;
            this.data = data;
        }

        /**
         * ISceneのホストとなるIGame継承クラスを取得します。
         * @return ISceneのホストとなるIGame継承クラス
         */
        public IGame<SCENE, VKEYPAD> getHost() {
            return host;
        }
        /**
         * ISceneChangerのchangeSceneの第二引数で指定したデータを取得します。
         * @return ISceneChangerのchangeSceneの第二引数で指定したデータ
         * @see ISceneChanger#changeScene(Enum, Object)
         */
        public Object getReceiveData() {
            return data;
        }
    }
}
